package net.n4dev.treespot.ui.addspot

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import net.n4dev.treespot.databinding.ActivityAddSpotBinding
import net.n4dev.treespot.util.ActivityUtil

class AddSpotActivity : AppCompatActivity() {

    companion object {
        const val ARG_IMAGES_ARRAY = "ARG_IMAGES_ARRAY"
    }

    private lateinit var binding : ActivityAddSpotBinding
    private val photosUriArray = ArrayList<String>()
    private val photosBitmapArray = ArrayList<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSpotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addSpotNameSwitch.setOnClickListener {
            ActivityUtil.toast(this, "Switched!", false)
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

    }

    private fun setupFromArgs(extras: Bundle) {
        val temp = extras.getStringArrayList(ARG_IMAGES_ARRAY)
        photosUriArray.addAll(temp!!)

        setupBitmapsFromUri(photosUriArray)
    }

    fun setupBitmapsFromUri(array : ArrayList<String>) {

        array.forEach {
            val source = ImageDecoder.createSource(contentResolver, Uri.parse(it))
            val bitmap = ImageDecoder.decodeBitmap(source)
            photosBitmapArray.add(bitmap)
        }
    }
}