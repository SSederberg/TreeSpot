package net.n4dev.treespot.ui.spots.share

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import net.n4dev.treespot.core.AbstractViewHolder
import net.n4dev.treespot.databinding.AdapteritemFriendsSelectBinding

class ShareSpotFriendsViewHolder(private val binding : AdapteritemFriendsSelectBinding) : AbstractViewHolder<AdapteritemFriendsSelectBinding>(binding) {


    override fun getDataBinding(
        context: Context,
        parent: ViewGroup,
        attach: Boolean
    ): AdapteritemFriendsSelectBinding {
        return AdapteritemFriendsSelectBinding.inflate(LayoutInflater.from(context), parent, attach)
    }

    override fun newInstance(binding: AdapteritemFriendsSelectBinding): ShareSpotFriendsViewHolder {
        return ShareSpotFriendsViewHolder(binding)
    }

    override fun getXMLBinding(): AdapteritemFriendsSelectBinding {
        return binding
    }


}