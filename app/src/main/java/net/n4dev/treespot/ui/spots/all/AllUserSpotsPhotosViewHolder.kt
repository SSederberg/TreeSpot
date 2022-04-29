package net.n4dev.treespot.ui.spots.all

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import net.n4dev.treespot.core.AbstractViewHolder
import net.n4dev.treespot.databinding.AdapteritemSpotMediaBinding

class AllUserSpotsPhotosViewHolder(private val binding : AdapteritemSpotMediaBinding) : AbstractViewHolder<AdapteritemSpotMediaBinding>(binding) {

    override fun getDataBinding(
        context: Context,
        parent: ViewGroup,
        attach: Boolean
    ): AdapteritemSpotMediaBinding {
       return AdapteritemSpotMediaBinding.inflate(LayoutInflater.from(context), parent, attach)
    }

    override fun newInstance(binding: AdapteritemSpotMediaBinding): AllUserSpotsPhotosViewHolder {
        return AllUserSpotsPhotosViewHolder(binding)
    }

    override fun getXMLBinding(): AdapteritemSpotMediaBinding {
       return binding
    }
}