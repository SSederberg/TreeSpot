package net.n4dev.treespot.ui.spots.all

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.orhanobut.logger.Logger
import net.n4dev.treespot.core.TreeSpotException
import net.n4dev.treespot.core.api.IUser
import net.n4dev.treespot.core.entity.Friend
import net.n4dev.treespot.core.entity.TreeSpot
import net.n4dev.treespot.core.entity.User
import net.n4dev.treespot.databinding.ActivityAllUserSpotsBinding
import net.n4dev.treespot.databinding.AdapteritemSpotMediaBinding
import net.n4dev.treespot.db.query.GetLocationMediaQuery
import net.n4dev.treespot.db.query.GetSingleFriendQuery
import net.n4dev.treespot.db.query.GetSingleUserQuery
import net.n4dev.treespot.ui.TreeSpotActivity



class AllUserSpotsActivity : TreeSpotActivity(), OnMapReadyCallback {


    companion object {
        const val ARG_USER_ID = "ARG_USER_ID"
    }

    private lateinit var binding: ActivityAllUserSpotsBinding
    private lateinit var theUser : IUser
    private lateinit var mapsFragment : SupportMapFragment
    private lateinit var markers : List<Marker>

    private lateinit var adapter: AllUserSpotsPhotosAdapter
    private lateinit var viewHolder: AllUserSpotsPhotosViewHolder

    private lateinit var markerMap : HashMap<Marker, TreeSpot>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllUserSpotsBinding.inflate(layoutInflater)


        if(savedInstanceState != null) {
            buildFromBundle(savedInstanceState)
        } else if(intent.extras != null) {
            buildFromBundle(intent.extras!!)
        }



        mapsFragment = binding.fragmentContainerView.getFragment() as SupportMapFragment
        mapsFragment.getMapAsync(this)


        val adapterBinding = AdapteritemSpotMediaBinding.inflate(layoutInflater)
        val photosQuery = GetLocationMediaQuery("")
        viewHolder = AllUserSpotsPhotosViewHolder(adapterBinding)
        adapter = AllUserSpotsPhotosAdapter(viewHolder, photosQuery)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.userSpotsPhotosList.layoutManager = layoutManager
        binding.userSpotsPhotosList.adapter = adapter

        setContentView(binding.root)
    }

    override fun buildFromBundle(bundle: Bundle) {
       val userID = bundle.getString(ARG_USER_ID)
        val singleUserQuery = GetSingleUserQuery(userID!!)
        val singleFriendQuery = GetSingleFriendQuery(userID)
        val userBox = super.getBox(User::class.java)
        val friendBox = super.getBox(Friend::class.java)
        val userResult = userBox.query(singleUserQuery.buildQuery()).build().find()

        markerMap = HashMap()

        if(userResult.size > 0 ) {
            theUser = userResult[0]
        } else {
            userBox.closeThreadResources()
            val friendResult = friendBox.query(singleFriendQuery.buildQuery()).build().find()

            if(friendResult.size > 0) {
                theUser = friendResult[0]
            } else {
                throw TreeSpotException("Failed to find a user or friend entity based on id $userID !")
            }
        }
    }

    private fun generateAllMarkers(gmap : GoogleMap) : List<Marker> {
        val markers = ArrayList<Marker>()

        val spots = theUser.getUserSpots()

        for(spot in spots) {
            val latNorth = spot.getLatNorth().toDouble()
            val longWest = spot.getLongWest().toDouble()
            val tempLatLng = LatLng(latNorth, longWest)
            val tempOptions = MarkerOptions()
                .position(tempLatLng)
                .title(spot.getDescription())

            val tempMarker = gmap.addMarker(tempOptions)!!

            markers.add(tempMarker)
            markerMap[tempMarker] = spot

        }
        return markers;
    }

    override fun onMapReady(gmap: GoogleMap) {
        markers = generateAllMarkers(gmap)

        gmap.setOnMarkerClickListener {
            val spot = markerMap[it] as TreeSpot
            val media = spot.getSpotPhotos()

            Logger.i(media.toString())
            true
        }

    }
}