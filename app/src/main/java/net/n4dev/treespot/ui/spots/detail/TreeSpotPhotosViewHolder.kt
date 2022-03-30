package net.n4dev.treespot.ui.spots.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import net.n4dev.treespot.core.AbstractViewHolder
import net.n4dev.treespot.databinding.AdapteritemSpotMediaBinding

class TreeSpotPhotosViewHolder(private val binding : AdapteritemSpotMediaBinding): AbstractViewHolder<AdapteritemSpotMediaBinding>(binding) {

    override fun getDataBinding(
        context: Context?,
        parent: ViewGroup?,
        attach: Boolean
    ): AdapteritemSpotMediaBinding {
        return AdapteritemSpotMediaBinding.inflate(LayoutInflater.from(context), parent, attach)
    }

    override fun newInstance(binding: AdapteritemSpotMediaBinding): TreeSpotPhotosViewHolder {
        return TreeSpotPhotosViewHolder(binding)
    }

    override fun getXMLBinding(): AdapteritemSpotMediaBinding {
        return binding
    }


}