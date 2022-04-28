package net.n4dev.treespot.ui.friends.detail

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import net.n4dev.treespot.R
import net.n4dev.treespot.core.AbstractViewHolder
import net.n4dev.treespot.core.entity.Friend
import net.n4dev.treespot.databinding.ActivityFriendDetailBinding
import net.n4dev.treespot.databinding.AdapteritemTreespotLocationBinding
import net.n4dev.treespot.db.query.GetSingleFriendQuery
import net.n4dev.treespot.db.query.GetUserTreeSpotsQuery
import net.n4dev.treespot.ui.TreeSpotActivity
import net.n4dev.treespot.ui.spots.all.AllUserSpotsActivity
import net.n4dev.treespot.util.ActivityUtil
import net.n4dev.treespot.viewmodel.FriendViewModel

class FriendDetailActivity : TreeSpotActivity(), Toolbar.OnMenuItemClickListener {

    private lateinit var binding : ActivityFriendDetailBinding
    private lateinit var friendID : String
    private lateinit var userID : String
    private lateinit var viewModel : FriendViewModel

    companion object {
        const val ARG_FRIEND_ID = "ARG_FRIEND_ID"
        const val ARG_USER_ID = "ARG_USER_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendDetailBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(FriendViewModel::class.java)

        if(intent.extras != null) {
            buildFromBundle(intent.extras!!)
        } else if(savedInstanceState != null) {
            buildFromBundle(savedInstanceState)
        }

        viewModel = getFriend()
        binding.friendDetail = viewModel


        binding.mainIncludeTopbar.mainAppbarBar.menu.getItem(1).setVisible(false)
        binding.mainIncludeTopbar.mainAppbarBar.setOnMenuItemClickListener(this)
        loadAvatar(friendID)

       generateSpotsList()

        setContentView(binding.root)
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        val itemID = item.itemId
        if(itemID == R.id.manu_main_spots_all) {
            val bundle = Bundle()
            bundle.putString(AllUserSpotsActivity.ARG_USER_ID, viewModel.getUserID().toString())

            ActivityUtil.startActivity(bundle, AllUserSpotsActivity::class.java, this, false)
        }
        return true
    }

    private fun generateSpotsList() {
        val adapterItemBinding  = AdapteritemTreespotLocationBinding.inflate(layoutInflater)
        val viewHolder = FriendDetailSpotsViewHolder(adapterItemBinding)
        val query = GetUserTreeSpotsQuery(friendID)

        val layoutManager = LinearLayoutManager(this)
        val adapter = FriendDetailSpotsAdapter(viewHolder, query!!, userID)

        binding.friendDetailSpotsList.layoutManager = layoutManager
        binding.friendDetailSpotsList.adapter = adapter

        AbstractViewHolder.generateItemDecoration(binding.friendDetailSpotsList, layoutManager)
    }

    private fun loadAvatar(friendID: String) {
        val fileName = "avatar_$friendID.png"
        val path = ActivityUtil.getAppFriendImagesDirectory(this) + fileName
        val uri = Uri.parse(path).path

        Glide.with(this)
            .asBitmap()
            .load(uri)
            .into(binding.friendDetailAvatar)

    }

    override fun buildFromBundle(bundle: Bundle) {
        friendID = bundle.getString(ARG_FRIEND_ID, "NULL")
        userID = bundle.getString(ARG_USER_ID)!!
    }

    private fun getFriend() : FriendViewModel {
        if(friendID.equals("NULL")) {
            return FriendViewModel()
        } else {
            val friendBox = super.getBox(Friend::class.java)
            val singleQuery = GetSingleFriendQuery(friendID)
            val returnedFriend = friendBox.query(singleQuery.buildQuery()).build().find()

            if(returnedFriend.size > 0) {
                val model = FriendViewModel(returnedFriend[0])
                return model
            }
        }

        return FriendViewModel()
    }
}