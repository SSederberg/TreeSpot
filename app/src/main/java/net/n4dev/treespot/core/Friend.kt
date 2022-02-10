package net.n4dev.treespot.core

import androidx.room.Entity
import net.n4dev.treespot.core.api.IFriend
import net.n4dev.treespot.core.api.IUser
import java.util.*

@Entity
class Friend : IFriend {

    override fun getUserID(): UUID {
        TODO("Not yet implemented")
    }

    override fun getFriendsSince(): Date {
        TODO("Not yet implemented")
    }

    override fun setFriendsSince(date: Date) {
        TODO("Not yet implemented")
    }

    override fun getLocalID(): Int {
        TODO("Not yet implemented")
    }

    override fun setLocalID(int: Int) {
        TODO("Not yet implemented")
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