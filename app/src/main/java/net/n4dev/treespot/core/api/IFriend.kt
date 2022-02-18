package net.n4dev.treespot.core.api

import java.util.*

interface IFriend : IEntity {

    // This should be a standard User UUID
    fun getFriendID() : UUID

    fun getFriendsSince() : Long
    fun setFriendsSince(date: Long)

    fun getLocalID() : Long

    fun becomeFriendsWith(user : IUser, friend : IUser)

}