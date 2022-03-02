package net.n4dev.treespot.ui.addspot

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import net.n4dev.treespot.core.TreeSpot
import net.n4dev.treespot.databinding.ActivityAddSpotBinding
import net.n4dev.treespot.viewmodel.AddSpotViewModel
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.*


class AddSpotActivity : AppCompatActivity() {

    companion object {
        const val ARG_IMAGES_ARRAY = "ARG_IMAGES_ARRAY"
    }

    private lateinit var binding : ActivityAddSpotBinding
    private val photosUriArray = ArrayList<String>()
    private val photosBitmapArray = ArrayList<Bitmap>()
    private var hasPrivateName : Boolean = false
    private lateinit var viewmodel : AddSpotViewModel
    private val treeSpot = TreeSpot()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddSpotBinding.inflate(layoutInflater)
        viewmodel = ViewModelProvider(this).get(AddSpotViewModel::class.java)
        viewmodel.init(this)

        setContentView(binding.root)

        binding.addSpotNameSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            hasPrivateName = isChecked
            if(isChecked) {
                binding.spotPrivateName.visibility = View.VISIBLE
            }else{
                binding.spotPrivateName.visibility = View.INVISIBLE
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

//        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val layoutManager = LinearLayoutManager(this)
        val adapter = AddSpotPhotosAdapter(photosBitmapArray)

        binding.photosList.layoutManager = layoutManager
        binding.photosList.adapter = adapter

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
}