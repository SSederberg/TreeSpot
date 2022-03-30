package net.n4dev.treespot.db.query

import io.objectbox.query.Query
import net.n4dev.treespot.db.TreeSpotObjectBox
import net.n4dev.treespot.db.entity.User
import net.n4dev.treespot.db.entity.User_

class GetSingleUserQuery {

    companion object {
        private val queryBox = TreeSpotObjectBox.getBoxStore().boxFor(User::class.java)

        fun get(userID : String) : Query<User> {
            return queryBox.query(User_.userID.equal(userID)).build()
        }
    }
}