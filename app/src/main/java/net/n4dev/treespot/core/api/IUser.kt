package net.n4dev.treespot.core.api

import java.util.UUID
import java.util.Date

interface IUser : IEntity {

    fun getusername() : String
    fun setusername(username : String)

    fun getEmailAddress() : String
    fun setEmailAddress(email : String)

    fun getUserID() : UUID
    fun setUserID(userID : UUID)

    fun getLocalID() : Int
    fun setLocalID(int: Int)

    fun getAccountCreationDate() : Long
    fun setAccountCreationDate(date : Long)

    fun getUserSpots() : List<ITreeSpot>
    fun assignSpot(treespot : ITreeSpot)
    fun removeSpot(treespot: ITreeSpot)

    fun getUserFriends() : List<IUser>
    fun addFriend(friendUUID: UUID)
    fun removeFriend(friendUUID: UUID)


}