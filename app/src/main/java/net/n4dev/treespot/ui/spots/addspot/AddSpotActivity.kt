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
import net.n4dev.treespot.core.api.ITreeSpot
import net.n4dev.treespot.core.api.ITreeSpotMedia
import net.n4dev.treespot.core.entity.TreeSpot
import net.n4dev.treespot.core.entity.TreeSpotMedia
import net.n4dev.treespot.core.entity.TypeConst
import net.n4dev.treespot.databinding.ActivityAddSpotBinding
import net.n4dev.treespot.ui.main.MainActivity
import net.n4dev.treespot.util.ActivityUtil
import net.n4dev.treespot.util.GPSUtils
import net.n4dev.treespot.viewmodel.AddSpotViewModel
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.*


class AddSpotActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val ARG_IMAGES_ARRAY = "ARG_IMAGES_ARRAY"
        const val ARG_USER_ID = "ARG_USER_ID";
    }

    private lateinit var binding : ActivityAddSpotBinding
    private val photosUriArray = ArrayList<String>()
    private val photosBitmapArray = ArrayList<Bitmap>()
    private var hasPrivateName : Boolean = false
    private lateinit var viewmodel : AddSpotViewModel
    private val cancelToken = CancellationTokenSource()
    private lateinit var locationCallback: LocationCallback

    private var lat : Double = 0.0
    private var long : Double = 0.0
    private lateinit var userID : String
    private val creationDate = System.currentTimeMillis()
    private val spotID = UUID.randomUUID().toString()
    private lateinit var treeMedia : List<ITreeSpotMedia>

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
            val treeSpot = buildTreeSpot(binding.addSpotNameSwitch.isChecked)

            viewmodel.addSpot(treeSpot, treeMedia as List<TreeSpotMedia>, this)

            val bundle = Bundle()
            bundle.putString(MainActivity.ARG_USER_ID, userID)
            ActivityUtil.startActivity(bundle, MainActivity::class.java, this, false)
            ActivityUtil.toast(this, "Uploading spot in the background!", false)
            finish()
        }

        if(intent.extras != null) {
            setupFromArgs(intent.extras!!)
        } else if(savedInstanceState != null) {
            setupFromArgs(savedInstanceState)
        }

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = AddSpotPhotosAdapter(photosBitmapArray)

        binding.photosList.layoutManager = layoutManager
        binding.photosList.adapter = adapter

        setContentView(binding.root)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(gmap: GoogleMap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            GPSUtils.getLocationClient(this).getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY, cancelToken.token)
                .addOnSuccessListener {
                    this.lat = it.latitude
                    this.long = it.longitude
                    val coord = LatLng(it.latitude, it.longitude)
                    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(coord, 12F)
                    val marker = MarkerOptions()
                        .position(coord)
                        .title("New Tree Spot!")
                    gmap.addMarker(marker)
                    gmap.moveCamera(CameraUpdateFactory.newLatLng(coord))
                    gmap.animateCamera(cameraUpdate)
                }
        } else {
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    for (location in locationResult.locations){
                        this@AddSpotActivity.lat = location.latitude
                        this@AddSpotActivity.long = location.longitude

                        val coord = LatLng(location.latitude, location.longitude)
                        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(coord, 12F)
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

    private fun setupFromArgs(extras: Bundle) {
        val temp = extras.getStringArrayList(ARG_IMAGES_ARRAY)
        photosUriArray.addAll(temp!!)
        setupBitmapsFromUri(photosUriArray)

        this.userID = extras.getString(ARG_USER_ID)!!
        this.treeMedia = buildTreeSpotMedia()
    }

    private fun setupBitmapsFromUri(array : ArrayList<String>) {

        array.forEach {
            val uri = Uri.parse(it)
            val bitmap : Bitmap? = decodeUriToBitmap(uri)
            photosBitmapArray.add(bitmap!!)
        }
    }

    private fun buildTreeSpot(usePrivateDescription : Boolean) : ITreeSpot {
        val newSpot = TreeSpot()
        val description = binding.spotPublicText.text.toString()

        newSpot.setLatNorth(lat.toString())
        newSpot.setLongWest(long.toString())
        newSpot.setCreationDate(creationDate)
        newSpot.setSpotID(spotID)
        newSpot.setSpotOwnerID(userID)
        newSpot.setDescription(description)
        newSpot.setIsFavorite(false)

        if(usePrivateDescription) {
            val privateDescription = binding.spotPrivateText.text.toString()
            newSpot.setPrivateDescription(privateDescription)
        }

        return newSpot
    }

    private fun buildTreeSpotMedia() : List<ITreeSpotMedia> {
        val newMedias = ArrayList<ITreeSpotMedia>()
        for (uri in this.photosUriArray) {
            val filename = uri.substring(uri.lastIndexOf("/") + 1)
            val media = TreeSpotMedia(
                userID,
                spotID,
                TypeConst.MEDIA,
                uri,
                filename
            )

            newMedias.add(media)
        }

        return newMedias
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
}