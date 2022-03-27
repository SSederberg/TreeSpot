package net.n4dev.treespot.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import io.appwrite.Client
import io.appwrite.services.Avatars
import kotlinx.coroutines.launch
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.AbstractViewModel
import net.n4dev.treespot.databinding.ActivityFriendDetailBinding

class FriendDetailViewModel : AbstractViewModel() {

    private lateinit var client : Client
    private lateinit var avatars: Avatars

    override fun init(context: Context) {
        client = TreeSpotApplication.getClient(context)
        avatars = Avatars(client)
    }


    fun setProfilePic(username : String, binding : ActivityFriendDetailBinding) {
        viewModelScope.launch {

            val avatarResponse = avatars.getInitials(username)

            Glide.with(binding.root.context)
                .asBitmap()
                .load(avatarResponse)
                .into(binding.imageView)
        }
    }
}