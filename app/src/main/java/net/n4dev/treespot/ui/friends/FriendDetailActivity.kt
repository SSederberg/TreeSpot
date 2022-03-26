package net.n4dev.treespot.ui.friends

import android.os.Bundle
import net.n4dev.treespot.databinding.ActivityFriendDetailBinding
import net.n4dev.treespot.db.entity.Friend
import net.n4dev.treespot.db.query.GetSingleFriendQuery
import net.n4dev.treespot.ui.TreeSpotActivity
import net.n4dev.treespot.util.DateConverter

class FriendDetailActivity : TreeSpotActivity() {

    private lateinit var binding : ActivityFriendDetailBinding
    private var theFriend : Friend? = null
    private lateinit var friendID : String

    companion object {
        const val ARG_FRIEND_ID = "ARG_FRIEND_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendDetailBinding.inflate(layoutInflater)

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
        setContentView(binding.root)
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