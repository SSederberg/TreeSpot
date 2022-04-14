package net.n4dev.treespot.ui.spots.share

import android.content.Context
import android.net.Uri
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.orhanobut.logger.Logger
import io.objectbox.query.Query
import net.n4dev.treespot.BR
import net.n4dev.treespot.R
import net.n4dev.treespot.core.AbstractEntityAdapter
import net.n4dev.treespot.core.entity.Friend
import net.n4dev.treespot.util.ActivityUtil

class ShareSpotFriendsAdapter(holder: ShareSpotFriendsViewHolder, query : Query<Friend>, private val shareButton : MaterialButton)
    : AbstractEntityAdapter<Friend, ShareSpotFriendsViewHolder>(holder, query, BR.adapterFriend, false, Friend::class.java) {

    private val selectedFriends = ArrayList<Friend>()

    override fun onItemSelected(
        holder: ShareSpotFriendsViewHolder,
        entity: Friend,
        context: Context,
        position: Int
    ) {

    }

    override fun onBindItem(holder: ShareSpotFriendsViewHolder, entity: Friend, position: Int) {

        try {
            val fileName = "avatar_${entity.getFriendID()}.png"
            val path = ActivityUtil.getAppFriendImagesDirectory(holder.itemView.context) + fileName
            val uri = Uri.parse(path).path

            setAvatar(holder.itemView.context,
            holder.xmlBinding.aiFriendProfilePictureSelect,
            uri!!)

        }catch (e : Exception) {
            Logger.e(e, "")
        }

        holder.xmlBinding.aiFriendProfilePictureSelect.setOnClickListener {
            val context = holder.itemView.context
            val buttonTag = holder.xmlBinding.aiFriendProfilePictureSelect.tag
            if(buttonTag == 0 || buttonTag == null) {
                swapIcons(context,true, holder.xmlBinding.aiFriendProfilePictureSelect, entity)
            } else {
                swapIcons(context, false, holder.xmlBinding.aiFriendProfilePictureSelect, entity)
            }
        }

    }

    override fun onNoItemsAvailable(holder: ShareSpotFriendsViewHolder) {

    }

    private fun setAvatar(context: Context, imageButton: ImageButton, uriPath : String) {
        Glide.with(context)
            .asBitmap()
            .load(uriPath)
            .into(imageButton)
    }

    private fun swapIcons(context: Context, toCheckMark : Boolean, button : ImageButton, friend: Friend) {

        if(toCheckMark) {
            val checkDrawable = ContextCompat.getDrawable(context, R.drawable.ic_check_circle_outline)
            val background = context.resources.getColor(R.color.primaryDarkColor, context.theme)
            button.setImageDrawable(null)
            button.setImageDrawable(checkDrawable)
            button.setBackgroundColor(background)
            selectedFriends.add(friend)
            button.tag = 1
            shareButton.isEnabled = true
        } else {
            val fileName = "avatar_${friend.getFriendID()}.png"
            val path = ActivityUtil.getAppFriendImagesDirectory(context) + fileName
            val uri = Uri.parse(path).path

            button.setBackgroundColor(-1)
            setAvatar(context, button, uri!!)

            selectedFriends.remove(friend)
            button.tag = 0

            shareButton.isEnabled = selectedFriends.size > 0
        }
    }
}