package net.n4dev.treespot.ui.friends

import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import net.n4dev.treespot.databinding.ActivityFriendDetailBinding
import net.n4dev.treespot.db.entity.Friend
import net.n4dev.treespot.db.query.GetSingleFriendQuery
import net.n4dev.treespot.ui.TreeSpotActivity
import net.n4dev.treespot.util.ActivityUtil
import net.n4dev.treespot.util.DateConverter
import net.n4dev.treespot.viewmodel.FriendDetailViewModel

class FriendDetailActivity : TreeSpotActivity() {

    private lateinit var binding : ActivityFriendDetailBinding
    private var theFriend : Friend? = null
    private lateinit var friendID : String
    private lateinit var viewModel : FriendDetailViewModel

    companion object {
        const val ARG_FRIEND_ID = "ARG_FRIEND_ID"
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

        binding.mainIncludeTopbar.mainAppbarBar.menu.getItem(1).setVisible(false)

        loadAvatar(friendID)

        setContentView(binding.root)
    }

    private fun loadAvatar(friendID: String) {
        val fileName = "avatar_$friendID.png"
        val path = ActivityUtil.getAppFriendImagesDirectory(this) + fileName
        val uri = Uri.parse(path).path

        Glide.with(this)
            .asBitmap()
            .load(uri)
            .into(binding.imageView)

    }

    override fun buildFromBundle(bundle: Bundle) {
        friendID = bundle.getString(ARG_FRIEND_ID, "NULL")
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