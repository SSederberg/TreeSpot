package net.n4dev.treespot.ui.friends.add

import androidx.recyclerview.widget.RecyclerView
import net.n4dev.treespot.BR
import net.n4dev.treespot.databinding.AdapteritemFriendAddBinding

open class AddFriendsViewHolder(private val binding: AdapteritemFriendAddBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(username: String) {
        binding.setVariable(BR.addableFriendName, username)
    }

    fun getBinding() : AdapteritemFriendAddBinding {
        return binding
    }
}