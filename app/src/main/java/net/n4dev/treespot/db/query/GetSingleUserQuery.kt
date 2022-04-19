package net.n4dev.treespot.db.query

import io.objectbox.Property
import io.objectbox.query.QueryCondition
import net.n4dev.treespot.core.AbstractQuery
import net.n4dev.treespot.core.entity.User
import net.n4dev.treespot.db.constants.TreeSpotUserConstants

class GetSingleUserQuery(private val userID : String) :AbstractQuery<User>(User::class.java) {

    //    companion object {
//        private val userBox= TreeSpotObjectBox.getBoxStore().boxFor(User::class.java)
//        private val friendBox = TreeSpotObjectBox.getBoxStore().boxFor(Friend::class.java)
//
//        fun getFromUser(userID : String) : Query<User> {
//            return userBox.query(User_.userID.equal(userID)).build()
//        }
//
//        fun getFromFriend(userID: String) : Query<Friend> {
//            return friendBox.query(Friend_.friendID.equal(userID)).build()
//        }
//    }
    override fun buildConditions(fields: Array<out Property<User>>): QueryCondition<User> {
        val field = User.fieldConverter.get(TreeSpotUserConstants.USER_ID) as Int
        return fields[field].equal(userID)
    }
}