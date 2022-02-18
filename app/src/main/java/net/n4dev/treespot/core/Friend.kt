package net.n4dev.treespot.core

import androidx.room.Entity
import androidx.room.PrimaryKey
import net.n4dev.treespot.core.api.IFriend
import net.n4dev.treespot.core.api.IUser
import java.util.*

@Entity
class Friend(private var friendID: UUID, private var friendsSince: Long) : IFriend {

    @PrimaryKey(autoGenerate = true)
    private val localUID: Long? = null


    override fun getFriendID(): UUID {
        return friendID
    }

    override fun getFriendsSince(): Long {
       return friendsSince
    }

    override fun setFriendsSince(date: Long) {
      this.friendsSince = date
    }

    override fun getLocalID(): Long {
        return localUID!!
    }

    override fun becomeFriendsWith(user: IUser, friend: IUser) {
        TODO("Not yet implemented")
    }

    override fun getType(): String {
        return TypeConst.FRIEND
    }

    override fun getEntityID(): String {
        TODO("Not yet implemented")
    }

    override fun isUser(): Boolean {
        return false
    }

    override fun isTreeSpot(): Boolean {
        return false
    }
}