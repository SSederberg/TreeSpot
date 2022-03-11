package net.n4dev.treespot.core.api

import java.util.*

interface IFriend : IEntity {

    fun getUserID() : UUID
    fun setUserID(uuid: UUID)

    fun getFriendID() : UUID
    fun setFriendID(uuid: UUID)

    fun getFriendPairID() : UUID
    fun setFriendPairID(uuid: UUID)

    fun getFriendsSince() : Long
    fun setFriendsSince(date: Long)

    fun getLocalID() : Long

}