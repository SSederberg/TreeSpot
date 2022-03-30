package net.n4dev.treespot.db.query

import io.objectbox.query.Query
import net.n4dev.treespot.db.TreeSpotObjectBox
import net.n4dev.treespot.db.entity.Friend
import net.n4dev.treespot.db.entity.Friend_

class GetSingleFriendQuery {

    companion object {
        private val queryBox = TreeSpotObjectBox.getBoxStore().boxFor(Friend::class.java)
        fun get(userID : String) : Query<Friend> {
            return queryBox.query(Friend_.friendID.equal(userID)).build()
        }
    }
}