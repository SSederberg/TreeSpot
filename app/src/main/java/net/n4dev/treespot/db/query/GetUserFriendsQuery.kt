package net.n4dev.treespot.db.query

import io.objectbox.Property
import io.objectbox.query.QueryCondition
import net.n4dev.treespot.core.AbstractQuery
import net.n4dev.treespot.core.entity.Friend
import net.n4dev.treespot.db.constants.TreeSpotFriendsConstants

class GetUserFriendsQuery(val userID : String) : AbstractQuery<Friend>(Friend::class.java) {

    //    companion object {
//        private val queryBox = TreeSpotObjectBox.getBoxStore().boxFor(Friend::class.java)
//        fun get(userID : String) : Query<Friend> {
//            return queryBox.query(Friend_.userID.equal(userID)).build()
//        }
//    }
    override fun buildConditions(fields: Array<out Property<Friend>>): QueryCondition<Friend> {
        val field = Friend.fieldConverter.get(TreeSpotFriendsConstants.USER_ID) as Int
        return fields[field].equal(userID)
    }
}
