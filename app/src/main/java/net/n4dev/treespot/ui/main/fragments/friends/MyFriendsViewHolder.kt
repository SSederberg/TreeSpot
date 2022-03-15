package net.n4dev.treespot.ui.main.fragments.friends

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import net.n4dev.treespot.core.AbstractViewHolder
import net.n4dev.treespot.databinding.AdapteritemFriendsBinding

class MyFriendsViewHolder(private val binding : AdapteritemFriendsBinding) : AbstractViewHolder<AdapteritemFriendsBinding>(binding) {

    override fun getDataBinding(
        context: Context?,
        parent: ViewGroup?,
        attach: Boolean
    ): AdapteritemFriendsBinding {
        return AdapteritemFriendsBinding.inflate(LayoutInflater.from(context), parent, attach)
    }

    override fun newInstance(binding: AdapteritemFriendsBinding): AbstractViewHolder<AdapteritemFriendsBinding> {
       return MyFriendsViewHolder(binding)
    }

    override fun getXMLBinding(): AdapteritemFriendsBinding {
       return binding
    }


}