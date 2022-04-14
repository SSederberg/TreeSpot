package net.n4dev.treespot.db.query

import io.objectbox.query.Query
import net.n4dev.treespot.core.entity.Friend
import net.n4dev.treespot.core.entity.Friend_
import net.n4dev.treespot.db.TreeSpotObjectBox

class GetUserFriendsQuery {

    companion object {
        private val queryBox = TreeSpotObjectBox.getBoxStore().boxFor(Friend::class.java)
        fun get(userID : String) : Query<Friend> {
            return queryBox.query(Friend_.userID.equal(userID)).build()
        }
    }
}
