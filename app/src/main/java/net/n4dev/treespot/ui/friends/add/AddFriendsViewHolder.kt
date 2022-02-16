package net.n4dev.treespot.ui.friends.add

import androidx.recyclerview.widget.RecyclerView
import io.appwrite.models.User
import net.n4dev.treespot.BR
import net.n4dev.treespot.databinding.AdapteritemFriendAddBinding

open class AddFriendsViewHolder(private val binding: AdapteritemFriendAddBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        binding.setVariable(BR.addableFriend, user)
    }

    fun getBinding() : AdapteritemFriendAddBinding {
        return binding
    }
}