package net.n4dev.treespot.ui.main.fragments.friends

import android.content.Context
import android.net.Uri
import android.os.Bundle
import com.bumptech.glide.Glide
import io.appwrite.Client
import io.appwrite.services.Avatars
import io.objectbox.query.Query
import net.n4dev.treespot.BR
import net.n4dev.treespot.core.AbstractEntityAdapter
import net.n4dev.treespot.db.entity.Friend
import net.n4dev.treespot.ui.friends.FriendDetailActivity
import net.n4dev.treespot.util.ActivityUtil

class MyFriendsAdapter(viewHolder: MyFriendsViewHolder, query : Query<Friend>) :
    AbstractEntityAdapter<Friend, MyFriendsViewHolder>(viewHolder, query, BR.adapterFriend, false, Friend::class.java) {

    private lateinit var client : Client
    private lateinit var avatars: Avatars

    override fun onItemSelected(
        holder: MyFriendsViewHolder,
        entity: Friend,
        context: Context,
        position: Int
    ) {
        val bundle = Bundle()
        bundle.putString(FriendDetailActivity.ARG_FRIEND_ID, entity.getFriendID().toString())
        ActivityUtil.startActivity(bundle, FriendDetailActivity::class.java, holder.itemView.context)
    }

    override fun onBindItem(holder: MyFriendsViewHolder, entity: Friend, position: Int) {

        val fileName = "avatar_${entity.getFriendID()}.png"
        val path = ActivityUtil.getAppFriendImagesDirectory(holder.itemView.context) + fileName
        val uri = Uri.parse(path).path

        Glide.with(holder.itemView.context)
            .asBitmap()
            .load(uri)
            .into(holder.xmlBinding.aiFriendProfilePicture)
    }

    override fun onNoItemsAvailable(holder: MyFriendsViewHolder?) {
    }

}