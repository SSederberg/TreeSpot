package net.n4dev.treespot.ui.spots.addspot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.orhanobut.logger.Logger
import net.n4dev.treespot.core.api.ITreeSpotMedia
import net.n4dev.treespot.databinding.AdapteritemPhotoAddBinding
import java.io.File

class AddSpotMediaAdapter(private val medias : ArrayList<ITreeSpotMedia>) : RecyclerView.Adapter<AddSpotPhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddSpotPhotosViewHolder {
        val binding : AdapteritemPhotoAddBinding = AdapteritemPhotoAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddSpotPhotosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddSpotPhotosViewHolder, position: Int) {
        val media = medias[position]
        Logger.i("POS: $position ${media.getMediaPath()}")

      Glide
          .with(holder.itemView.context)
          .load(File(media.getMediaPath()))
          .into(holder.getXMLBinding().photoToAdd)
    }

    override fun getItemCount(): Int {
       return medias.size
    }

    fun addItem(media : ITreeSpotMedia) {
        medias.add(media)
        notifyItemInserted(medias.size)
    }
}