package net.n4dev.treespot.core.api

import java.util.UUID
import java.util.Date

interface IUser : IEntity {

    fun getUsername() : String
    fun setUsername(username : String)

    fun getEmailAddress() : String
    fun setEmailAddress(email : String)

    fun getUserID() : String
    fun setUserID(userID : String)

    fun getLocalUID() : Long
    fun setLocalUID(int: Long)

    fun getAccountCreationDate() : Long
    fun setAccountCreationDate(date : Long)

    fun getUserSpots() : List<ITreeSpot>
    fun assignSpot(treespot : ITreeSpot)
    fun removeSpot(treespot: ITreeSpot)

    fun getUserFriends() : List<IUser>
    fun addFriend(friendUUID: UUID)
    fun removeFriend(friendUUID: UUID)


}