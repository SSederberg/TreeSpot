package net.n4dev.treespot.ui.spots.all

import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import net.n4dev.treespot.core.api.IUser
import net.n4dev.treespot.core.entity.User
import net.n4dev.treespot.databinding.ActivityAllUserSpotsBinding
import net.n4dev.treespot.db.query.GetSingleUserQuery
import net.n4dev.treespot.ui.TreeSpotActivity



class AllUserSpotsActivity : TreeSpotActivity(), OnMapReadyCallback {


    companion object {
        const val ARG_USER_ID = "ARG_USER_ID"
    }

    private lateinit var binding: ActivityAllUserSpotsBinding
    private lateinit var theUser : IUser
    private lateinit var gmap : GoogleMap
    private lateinit var mapsFragment : SupportMapFragment
    private lateinit var markers : List<MarkerOptions>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllUserSpotsBinding.inflate(layoutInflater)


        if(savedInstanceState != null) {
            buildFromBundle(savedInstanceState)
        } else if(intent.extras != null) {
            buildFromBundle(intent.extras!!)
        }

        markers = generateAllMarkers()

        val mapsFragment = binding.fragmentContainerView.getFragment() as SupportMapFragment
        mapsFragment.getMapAsync(this)



        setContentView(binding.root)
    }

    override fun buildFromBundle(bundle: Bundle) {
       val userID = bundle.getString(ARG_USER_ID)
        val singleUserQuery = GetSingleUserQuery(userID!!)
        val userBox = super.getBox(User::class.java)

        val result = userBox.query(singleUserQuery.buildQuery()).build().find()

        if(result.size > 0 ) {
            theUser = result[0]
        }
    }

    private fun generateAllMarkers() : List<MarkerOptions> {
        val markers = ArrayList<MarkerOptions>()

        val spots = theUser.getUserSpots()

        for(spot in spots) {
            val latNorth = spot.getLatNorth().toDouble()
            val longWest = spot.getLongWest().toDouble()
            val tempLatLng = LatLng(latNorth, longWest)
            val tempMarker = MarkerOptions()
                .position(tempLatLng)
                .title(spot.getDescription())

            markers.add(tempMarker)

        }
        return markers;
    }

    override fun onMapReady(gmap: GoogleMap) {

        for(marker in markers) {
            gmap.addMarker(marker)
        }

    }
}