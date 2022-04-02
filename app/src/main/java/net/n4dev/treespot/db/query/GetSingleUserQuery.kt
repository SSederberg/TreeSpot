package net.n4dev.treespot.db.query

import io.objectbox.query.Query
import net.n4dev.treespot.db.TreeSpotObjectBox
import net.n4dev.treespot.db.entity.Friend
import net.n4dev.treespot.db.entity.Friend_
import net.n4dev.treespot.db.entity.User
import net.n4dev.treespot.db.entity.User_

class GetSingleUserQuery {

    companion object {
        private val userBox= TreeSpotObjectBox.getBoxStore().boxFor(User::class.java)
        private val friendBox = TreeSpotObjectBox.getBoxStore().boxFor(Friend::class.java)

        fun getFromUser(userID : String) : Query<User> {
            return userBox.query(User_.userID.equal(userID)).build()
        }

        fun getFromFriend(userID: String) : Query<Friend> {
            return friendBox.query(Friend_.friendID.equal(userID)).build()
        }
    }
}