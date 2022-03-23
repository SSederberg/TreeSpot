package net.n4dev.treespot.ui.spots.addspot

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.orhanobut.logger.Logger
import net.n4dev.treespot.databinding.AdapteritemPhotoAddBinding

class AddSpotPhotosAdapter(private val photos : ArrayList<Bitmap>) : RecyclerView.Adapter<AddSpotPhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddSpotPhotosViewHolder {
        val binding : AdapteritemPhotoAddBinding = AdapteritemPhotoAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddSpotPhotosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddSpotPhotosViewHolder, position: Int) {
        Logger.i("POS: $position")
        val photo = photos.get(position)

        val drawable = BitmapDrawable(holder.itemView.resources, photo)

      Glide
          .with(holder.itemView.context)
          .asDrawable()
          .load(drawable)
          .into(holder.getXMLBinding().photoToAdd)
    }

    override fun getItemCount(): Int {
       return photos.size
    }
}