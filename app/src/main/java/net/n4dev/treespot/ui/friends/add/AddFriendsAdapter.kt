package net.n4dev.treespot.ui.friends.add

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import io.appwrite.models.User
import net.n4dev.treespot.R
import net.n4dev.treespot.databinding.AdapteritemFriendAddBinding

class AddFriendsAdapter : RecyclerView.Adapter<AddFriendsViewHolder>() {

    private var avatars = ArrayList<Bitmap>()
    private var users = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddFriendsViewHolder {
        val binding : AdapteritemFriendAddBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.adapteritem_friend_add, parent, false)
        return AddFriendsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddFriendsViewHolder, position: Int) {
        val user = users.get(position)
        val avatar = avatars.get(position)

        holder.bind(user)
        holder.getBinding().imageView.setImageBitmap(avatar)

    }

    override fun getItemCount(): Int {
        if(users.size == 0) {
            Logger.w("Size was 0!")
            return 0
        } else return users.size
    }


    fun setUsers(users : ArrayList<User>) {
        this.users = users
    }

    fun setAvatars(avatars : ArrayList<Bitmap>) {
        this.avatars = avatars
    }
}