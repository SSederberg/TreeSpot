package net.n4dev.treespot.ui.spots.share

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.orhanobut.logger.Logger
import net.n4dev.treespot.databinding.ActivityShareSpotBinding
import net.n4dev.treespot.databinding.AdapteritemFriendsSelectBinding
import net.n4dev.treespot.db.entity.TreeSpot
import net.n4dev.treespot.db.entity.User
import net.n4dev.treespot.db.query.GetSingleLocationQuery
import net.n4dev.treespot.db.query.GetSingleUserQuery
import net.n4dev.treespot.db.query.GetUserFriendsQuery
import net.n4dev.treespot.ui.TreeSpotActivity

class ShareSpotActivity : TreeSpotActivity() {

    private lateinit var binding : ActivityShareSpotBinding

    companion object {
        const val ARG_LOCATION_ID = "ARG_LOCATION_ID"
        const val ARG_USER_ID = "ARG_USER_ID"
    }

    private lateinit var currentUser : User
    private lateinit var locationToShare : TreeSpot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareSpotBinding.inflate(layoutInflater)
        val adapterItemBinding = AdapteritemFriendsSelectBinding.inflate(layoutInflater)

        if(intent.extras != null) {
            buildFromBundle(intent.extras!!)
        }else if(savedInstanceState != null) {
            buildFromBundle(savedInstanceState)
        }

        val viewholder = ShareSpotFriendsViewHolder(adapterItemBinding)
        val userID = currentUser.getUserID().toString()
        val friendsQuery = GetUserFriendsQuery.get(userID)
        val adapter = ShareSpotFriendsAdapter(viewholder, friendsQuery, binding.shareSpotShare)
        val layoutManager = LinearLayoutManager(this)

        binding.shareSpotFriendsList.layoutManager = layoutManager
        binding.shareSpotFriendsList.adapter = adapter

        setContentView(binding.root)
    }

    override fun buildFromBundle(bundle: Bundle) {
        val locationID = bundle.getString(ARG_LOCATION_ID)!!
        val userID = bundle.getString(ARG_USER_ID)!!

        val locationQuery = GetSingleLocationQuery.get(locationID).find()
        val userQuery = GetSingleUserQuery.getFromUser(userID).find()

        if(locationQuery.size == 1) {
            locationToShare = locationQuery[0]
        } else {
            Logger.e("Failed to find location to be able to share it!")
        }

        if(userQuery.size == 1) {
            currentUser = userQuery[0]
        } else {
            Logger.e("Failed to find user to associate with sharing!")
        }
    }
}