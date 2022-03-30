package net.n4dev.treespot.ui.spots.detail

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import net.n4dev.treespot.core.AbstractViewHolder
import net.n4dev.treespot.databinding.ActivityTreeSpotDetailBinding
import net.n4dev.treespot.databinding.AdapteritemSpotMediaBinding
import net.n4dev.treespot.db.entity.TreeSpot
import net.n4dev.treespot.db.entity.User
import net.n4dev.treespot.db.query.GetLocationMediaQuery
import net.n4dev.treespot.db.query.GetSingleLocationQuery
import net.n4dev.treespot.db.query.GetSingleUserQuery
import net.n4dev.treespot.ui.TreeSpotActivity
import net.n4dev.treespot.util.DateConverter

class TreeSpotDetailActivity : TreeSpotActivity(), OnMapReadyCallback {

    companion object {
        const val ARG_LOCATION_ID = "ARG_LOCATION_ID"
    }

    private lateinit var binding : ActivityTreeSpotDetailBinding
    private lateinit var mapsFragment : SupportMapFragment
    private lateinit var theSpot : TreeSpot
    private lateinit var theUser : User

    private var spotBox = super.getBox(TreeSpot::class.java)
    private var userBox = super.getBox(User::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTreeSpotDetailBinding.inflate(layoutInflater)

        if(intent.extras != null) {
            this.buildFromBundle(intent.extras!!)
        } else if(savedInstanceState != null) {
            this.buildFromBundle(savedInstanceState)
        }

        mapsFragment = binding.spotDetailMapsFragment.getFragment() as SupportMapFragment
        mapsFragment.getMapAsync(this)

        val prettyString = DateConverter.toPrettyString(theSpot.getCreationDate())
        binding.spotDetailDateCreatedText.setText(prettyString)

        binding.spotDetailUserText.setText(theUser.getUsername())
        binding.mainIncludeTopbar.mainAppbarBar.title = theSpot.getDescription()

        val adapterItemBinding = AdapteritemSpotMediaBinding.inflate(layoutInflater)
        val viewHolder = TreeSpotPhotosViewHolder(adapterItemBinding)

        val layoutManager = LinearLayoutManager(this)
        val query = GetLocationMediaQuery.get(theSpot.getSpotID())
        val adapter = TreeSpotPhotosAdapter(viewHolder, query)

        AbstractViewHolder.generateItemDecoration(binding.spotPhotoList, layoutManager)

        binding.spotPhotoList.layoutManager = layoutManager
        binding.spotPhotoList.adapter = adapter
        setContentView(binding.root)

    }

    override fun buildFromBundle(bundle: Bundle) {
        val spotID = bundle.getString(ARG_LOCATION_ID)

        val spotQuery = GetSingleLocationQuery.get(spotID!!).find()
        theSpot = spotQuery[0]

        val userQuery = GetSingleUserQuery.get(theSpot.getSpotOwnerID()).find()
        theUser = userQuery[0]
    }

    override fun onMapReady(gmap: GoogleMap) {
        val coord = LatLng(theSpot.getLatNorth().toDouble(), theSpot.getLongWest().toDouble())
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(coord, 12F)
        val marker = MarkerOptions()
            .position(coord)
            .title(theSpot.getDescription())
        gmap.addMarker(marker)
        gmap.moveCamera(CameraUpdateFactory.newLatLng(coord))
        gmap.animateCamera(cameraUpdate)

    }
}