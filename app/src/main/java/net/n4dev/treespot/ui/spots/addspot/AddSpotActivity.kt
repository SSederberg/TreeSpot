package net.n4dev.treespot.ui.spots.addspot

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.LocationRequest
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.CancellationTokenSource
import com.orhanobut.logger.Logger
import io.appwrite.extensions.toJson
import net.n4dev.treespot.databinding.ActivityAddSpotBinding
import net.n4dev.treespot.db.entity.TreeSpot
import net.n4dev.treespot.util.GPSUtils
import net.n4dev.treespot.viewmodel.AddSpotViewModel
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.*


class AddSpotActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val ARG_IMAGES_ARRAY = "ARG_IMAGES_ARRAY"
    }

    private lateinit var binding : ActivityAddSpotBinding
    private val photosUriArray = ArrayList<String>()
    private val photosBitmapArray = ArrayList<Bitmap>()
    private var hasPrivateName : Boolean = false
    private lateinit var viewmodel : AddSpotViewModel
    private val treeSpot = TreeSpot()
    private val cancelToken = CancellationTokenSource()
    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSpotBinding.inflate(layoutInflater)
        viewmodel = ViewModelProvider(this).get(AddSpotViewModel::class.java)
        viewmodel.init(this)
        val mapsFragment = binding.spotDetailMapsFragment.getFragment() as SupportMapFragment
        mapsFragment.getMapAsync(this)

        binding.addSpotNameSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            hasPrivateName = isChecked
            if(isChecked) {
                binding.spotPrivateName.visibility = View.VISIBLE
            }else{
                binding.spotPrivateName.visibility = View.GONE
            }
        }
        
        binding.addSpotAdd.setOnClickListener {
            treeSpot.setCreationDate(System.currentTimeMillis())
            treeSpot.setSpotID(UUID.randomUUID().toString())
            treeSpot.setDescription(binding.spotPublicText.text.toString())

            viewmodel.addSpot(treeSpot)
        }

        if(intent.extras != null) {
            setupFromArgs(intent.extras!!)
        } else if(savedInstanceState != null) {
            setupFromArgs(savedInstanceState)
        }

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        val layoutManager = LinearLayoutManager(this)
        val adapter = AddSpotPhotosAdapter(photosBitmapArray)

        binding.photosList.layoutManager = layoutManager
        binding.photosList.adapter = adapter

        setContentView(binding.root)
    }

    private fun setupFromArgs(extras: Bundle) {
        val temp = extras.getStringArrayList(ARG_IMAGES_ARRAY)
        photosUriArray.addAll(temp!!)

        setupBitmapsFromUri(photosUriArray)
    }

    private fun setupBitmapsFromUri(array : ArrayList<String>) {

        array.forEach {
            val uri = Uri.parse(it)
            val bitmap : Bitmap? = decodeUriToBitmap(uri)
            photosBitmapArray.add(bitmap!!)
        }
    }

    private fun decodeUriToBitmap(sendUri: Uri): Bitmap? {
        var getBitmap: Bitmap? = null
        try {
            val image_stream: InputStream
            try {
                image_stream = contentResolver.openInputStream(sendUri)!!
                getBitmap = BitmapFactory.decodeStream(image_stream)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return getBitmap
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(gmap: GoogleMap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            GPSUtils.getLocationClient(this).getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY, cancelToken.token)
                .addOnSuccessListener {
                    Logger.i(it.toJson())
                }
        } else {
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    for (location in locationResult.locations){

                        val coord = LatLng(location.latitude, location.longitude)
                        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(coord, 16F)
                        val marker = MarkerOptions()
                            .position(coord)
                            .title("New Tree Spot!")
                        gmap.addMarker(marker)
                        gmap.moveCamera(CameraUpdateFactory.newLatLng(coord))
                        gmap.animateCamera(cameraUpdate)
                    }
                }

            }
            GPSUtils.getLocationClient(this).requestLocationUpdates(GPSUtils.highAccuracyRequest, locationCallback, Looper.getMainLooper())
        }
    }
}