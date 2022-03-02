package net.n4dev.treespot.ui.addspot

import androidx.recyclerview.widget.RecyclerView
import net.n4dev.treespot.databinding.AdapteritemPhotoAddBinding

class AddSpotPhotosViewHolder(private val binding : AdapteritemPhotoAddBinding) : RecyclerView.ViewHolder(binding.root) {

    fun getXMLBinding() : AdapteritemPhotoAddBinding {
        return binding
    }
}