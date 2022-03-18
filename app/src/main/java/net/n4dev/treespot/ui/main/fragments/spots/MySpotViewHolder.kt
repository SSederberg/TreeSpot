package net.n4dev.treespot.ui.main.fragments.spots

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import net.n4dev.treespot.core.AbstractViewHolder
import net.n4dev.treespot.databinding.AdapteritemTreespotLocationBinding

class MySpotViewHolder(private val binding : AdapteritemTreespotLocationBinding) : AbstractViewHolder<AdapteritemTreespotLocationBinding>(binding) {

    override fun getDataBinding(
        context: Context?,
        parent: ViewGroup?,
        attach: Boolean
    ): AdapteritemTreespotLocationBinding {
      return AdapteritemTreespotLocationBinding.inflate(LayoutInflater.from(context), parent, attach)
    }

    override fun newInstance(binding: AdapteritemTreespotLocationBinding): MySpotViewHolder {
       return MySpotViewHolder(binding);
    }

    override fun getXMLBinding(): AdapteritemTreespotLocationBinding {
        return binding;
    }

}
