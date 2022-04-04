package net.n4dev.treespot.ui.spots.share

import android.content.Context
import io.objectbox.query.Query
import net.n4dev.treespot.BR
import net.n4dev.treespot.core.AbstractEntityAdapter
import net.n4dev.treespot.db.entity.Friend

class ShareSpotFriendsAdapter(holder: ShareSpotFriendsViewHolder, query : Query<Friend>)
    : AbstractEntityAdapter<Friend, ShareSpotFriendsViewHolder>(holder, query, BR.adapterFriend, false, Friend::class.java) {

    override fun onItemSelected(
        holder: ShareSpotFriendsViewHolder?,
        entity: Friend,
        context: Context,
        position: Int
    ) {

    }

    override fun onBindItem(holder: ShareSpotFriendsViewHolder?, entity: Friend?, position: Int) {

    }

    override fun onNoItemsAvailable(holder: ShareSpotFriendsViewHolder?) {

    }

    private fun swapIcons() {

    }
}