package net.n4dev.treespot.core.api

import java.util.*

interface IFriend : IEntity {

    fun getUserID() : UUID

    fun getFriendsSince() : Date
    fun setFriendsSince(date: Date)

    fun getLocalID() : Int
    fun setLocalID(int: Int)

    fun becomeFriendsWith(user : IUser, friend : IUser)

}