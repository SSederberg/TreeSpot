package net.n4dev.treespot.core.api

import java.util.UUID
import java.util.Date

interface IUser : IEntity {

    fun getUserName() : String
    fun setUserName(username : String)

    fun getEmailAddress() : String
    fun setEmailAddress(email : String)

    fun getUserID() : UUID
    fun setUserID(userID : UUID)

    fun getAccountCreationDate() : Date
    fun setAccountCreationDate(date: Date)

    fun getUserSpots() : List<ITreeSpot>
    fun assignSpot(treespot : ITreeSpot)
    fun removeSpot(treespot: ITreeSpot)

    fun getUserFriends() : List<IUser>
    fun addFriend(friendUUID: UUID)
    fun removeFriend(friendUUID: UUID)

}