package net.n4dev.treespot.ui.main.fragments.friends

import android.content.Context
import android.os.Bundle
import io.objectbox.query.Query
import net.n4dev.treespot.BR
import net.n4dev.treespot.core.AbstractEntityAdapter
import net.n4dev.treespot.db.entity.Friend
import net.n4dev.treespot.ui.friends.FriendDetailActivity
import net.n4dev.treespot.util.ActivityUtil

class MyFriendsAdapter(viewHolder: MyFriendsViewHolder, query : Query<Friend>) :
    AbstractEntityAdapter<Friend, MyFriendsViewHolder>(viewHolder, query, BR.adapterFriend, false, Friend::class.java) {


    override fun onItemSelected(
        holder: MyFriendsViewHolder,
        entity: Friend,
        context: Context,
        position: Int
    ) {
        val bundle = Bundle()

        ActivityUtil.startActivity(bundle, FriendDetailActivity::class.java, holder.itemView.context)
    }

    override fun onBindItem(holder: MyFriendsViewHolder, entity: Friend?, position: Int) {
    }

    override fun onNoItemsAvailable(holder: MyFriendsViewHolder?) {
    }

}