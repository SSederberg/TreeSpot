package net.n4dev.treespot.ui.friends.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.appwrite.models.Document
import net.n4dev.treespot.R
import net.n4dev.treespot.databinding.AdapteritemFriendAddBinding

class AddFriendsAdapter : RecyclerView.Adapter<AddFriendsViewHolder>() {

    private var avatars = ArrayList<ByteArray>()
    private var users = ArrayList<Document>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddFriendsViewHolder {
        val binding : AdapteritemFriendAddBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.adapteritem_friend_add, parent, false)
        return AddFriendsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddFriendsViewHolder, position: Int) {
        val user = users.get(position)
        val avatar = avatars.get(position)

        holder.bind(user.data.get("user_name") as String)

        Glide.with(holder.itemView.context)
            .asBitmap()
            .load(avatar)
            .into(holder.getBinding().imageView)

    }

    override fun getItemCount(): Int {
        if(users.size == 0) {
            return 0
        } else return users.size
    }


    fun setUsers(users : ArrayList<Document>) {
        this.users.clear()
        this.users.addAll(users)
    }

    fun setAvatars(avatars : ArrayList<ByteArray>) {
        this.avatars = avatars
    }
}