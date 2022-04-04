package net.n4dev.treespot.ui.spots.detail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.orhanobut.logger.Logger
import net.n4dev.treespot.R
import net.n4dev.treespot.core.AbstractViewHolder
import net.n4dev.treespot.core.api.IUser
import net.n4dev.treespot.databinding.ActivityTreeSpotDetailBinding
import net.n4dev.treespot.databinding.AdapteritemSpotMediaBinding
import net.n4dev.treespot.db.entity.TreeSpot
import net.n4dev.treespot.db.query.GetLocationMediaQuery
import net.n4dev.treespot.db.query.GetSingleLocationQuery
import net.n4dev.treespot.db.query.GetSingleUserQuery
import net.n4dev.treespot.ui.TreeSpotActivity
import net.n4dev.treespot.ui.spots.share.ShareSpotActivity
import net.n4dev.treespot.util.ActivityUtil
import net.n4dev.treespot.util.DateConverter

class TreeSpotDetailActivity : TreeSpotActivity(), OnMapReadyCallback,
    PopupMenu.OnMenuItemClickListener {

    companion object {
        const val ARG_LOCATION_ID = "ARG_LOCATION_ID"
        const val ARG_USER_TYPE = "ARG_USER_TYPE"
        const val ARG_USER = "ARG_USER"
        const val ARG_FRIEND = "ARG_FRIEND"
    }

    private lateinit var binding: ActivityTreeSpotDetailBinding
    private lateinit var mapsFragment: SupportMapFragment
    private lateinit var theSpot: TreeSpot
    private lateinit var theUser: IUser
    private lateinit var popupMenu: PopupMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTreeSpotDetailBinding.inflate(layoutInflater)

        if (intent.extras != null) {
            this.buildFromBundle(intent.extras!!)
        } else if (savedInstanceState != null) {
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

        popupMenu = PopupMenu(this, binding.spotDetailShare)
        popupMenu.menuInflater.inflate(R.menu.spot_detail_share, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(this)

        AbstractViewHolder.generateItemDecoration(binding.spotPhotoList, layoutManager)

        binding.spotDetailGmapsButton.setOnClickListener {
            ActivityUtil.forwardToGMaps(theSpot, this)
        }

        binding.spotDetailShare.setOnClickListener {
            popupMenu.show()
        }

        binding.spotPhotoList.layoutManager = layoutManager
        binding.spotPhotoList.adapter = adapter
        setContentView(binding.root)

    }

    override fun buildFromBundle(bundle: Bundle) {
        val spotID = bundle.getString(ARG_LOCATION_ID)
        val userType = bundle.getString(ARG_USER_TYPE)

        val spotQuery = GetSingleLocationQuery.get(spotID!!).find()
        theSpot = spotQuery[0]
        val ownerID = theSpot.getSpotOwnerID();

        if (userType.equals(ARG_USER)) {
            val userQuery = GetSingleUserQuery.getFromUser(ownerID).find()
            theUser = userQuery[0]
        } else if (userType.equals(ARG_FRIEND)) {
            val friendQuery = GetSingleUserQuery.getFromFriend(ownerID).find()
            theUser = friendQuery[0]
        } else {
            Logger.e("Failed to determine user type for Tree Spot Detail!")
        }
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

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.share_via_treespot -> {
                val bundle = Bundle()

                bundle.putString(ShareSpotActivity.ARG_LOCATION_ID, theSpot.getSpotID())
                bundle.putString(ShareSpotActivity.ARG_USER_ID, theUser.getUserID().toString())

                ActivityUtil.startActivity(bundle, ShareSpotActivity::class.java, this)
                true

            }
            R.id.share_via_sharesheet -> {

                val share = generateShareSheet()

                startActivity(share)
                true
            }
            else -> false
        }
    }

    private fun generateShareSheet() : Intent {
        val shareStringTitle = resources.getString(R.string.share_sheet_title)
        val locationTitle = theSpot.getDescription()

        return Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/*"

            putExtra(Intent.EXTRA_TITLE, "Share $locationTitle")
        }, shareStringTitle + " ( $locationTitle )")
    }
}