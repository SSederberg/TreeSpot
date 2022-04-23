package net.n4dev.treespot.db.query

import io.objectbox.Property
import io.objectbox.query.QueryCondition
import net.n4dev.treespot.core.AbstractQuery
import net.n4dev.treespot.core.entity.Friend
import net.n4dev.treespot.db.constants.TreeSpotFriendsConstants

class GetSingleFriendQuery(private val friendID : String) : AbstractQuery<Friend>(Friend::class.java) {

    override fun buildConditions(fields: Array<Property<Friend>>): QueryCondition<Friend> {
        val field = Friend.fieldConverter.get(TreeSpotFriendsConstants.FRIEND_ID) as Int
        return fields[field].equal(friendID)
    }
}