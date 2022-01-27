package net.n4dev.treespot.ui.fragments.friends

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.zeko.db.sql.Query
import net.n4dev.treespot.core.api.IUser

class MyFriendsAdapter : RecyclerView.Adapter<MyFriendsViewHolder>() {

    private var userFriends = ArrayList<IUser>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFriendsViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyFriendsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
       return userFriends.size
    }
}