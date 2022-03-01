package net.n4dev.treespot.ui.addspot

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.n4dev.treespot.databinding.AdapteritemPhotoAddBinding

class AddSpotPhotosAdapter(private val photos : ArrayList<Bitmap>) : RecyclerView.Adapter<AddSpotPhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddSpotPhotosViewHolder {
        val binding : AdapteritemPhotoAddBinding = AdapteritemPhotoAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddSpotPhotosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddSpotPhotosViewHolder, position: Int) {
        val photo = photos.get(position)

      holder.getXMLBinding().photoToAdd.setImageBitmap(photo)
    }

    override fun getItemCount(): Int {
       return photos.size
    }
}