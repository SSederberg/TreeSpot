package net.n4dev.treespot.ui.friends.detail

import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import net.n4dev.treespot.core.AbstractViewHolder
import net.n4dev.treespot.core.entity.Friend
import net.n4dev.treespot.databinding.ActivityFriendDetailBinding
import net.n4dev.treespot.databinding.AdapteritemTreespotLocationBinding
import net.n4dev.treespot.db.query.GetSingleFriendQuery
import net.n4dev.treespot.db.query.GetUserTreeSpotsQuery
import net.n4dev.treespot.ui.TreeSpotActivity
import net.n4dev.treespot.util.ActivityUtil
import net.n4dev.treespot.util.DateConverter
import net.n4dev.treespot.viewmodel.FriendDetailViewModel

class FriendDetailActivity : TreeSpotActivity() {

    private lateinit var binding : ActivityFriendDetailBinding
    private var theFriend : Friend? = null
    private lateinit var friendID : String
    private lateinit var userID : String
    private lateinit var viewModel : FriendDetailViewModel

    companion object {
        const val ARG_FRIEND_ID = "ARG_FRIEND_ID"
        const val ARG_USER_ID = "ARG_USER_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendDetailBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(FriendDetailViewModel::class.java)
        viewModel.init(this)

        if(intent.extras != null) {
            buildFromBundle(intent.extras!!)
        } else if(savedInstanceState != null) {
            buildFromBundle(savedInstanceState)
        }

        theFriend = getFriend()
        binding.friendDetail = theFriend

        val friendSinceString = DateConverter.toPrettyString(theFriend!!.getFriendsSince())
        val accountCreatedString = DateConverter.toPrettyString(theFriend!!.getAccountCreationDate())

        binding.friendDetailFrendSinceText.setText(friendSinceString)
        binding.friendDetailJoinedText.setText(accountCreatedString)

//        binding.mainIncludeTopbar.mainAppbarBar.menu.getItem(1).setVisible(false)
        loadAvatar(friendID)

        val adapterItemBinding  = AdapteritemTreespotLocationBinding.inflate(layoutInflater)
        val viewHolder = FriendDetailSpotsViewHolder(adapterItemBinding)
        val query = GetUserTreeSpotsQuery(friendID)

        val layoutManager = LinearLayoutManager(this)
        val adapter = FriendDetailSpotsAdapter(viewHolder, query!!, userID)

        binding.friendDetailSpotsList.layoutManager = layoutManager
        binding.friendDetailSpotsList.adapter = adapter

        AbstractViewHolder.generateItemDecoration(binding.friendDetailSpotsList, layoutManager)

        setContentView(binding.root)
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

    private fun getFriend() : Friend {
        if(friendID.equals("NULL")) {
            return Friend()
        } else {
            val singleQuery = GetSingleFriendQuery.get(friendID)
            val returnedFriend = singleQuery.find()

            if(returnedFriend.size > 0) {
                return returnedFriend[0]
            }
        }

        return Friend()
    }
}