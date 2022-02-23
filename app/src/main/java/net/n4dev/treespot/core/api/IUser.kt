package net.n4dev.treespot.core.api

import java.util.*

interface IUser : IEntity {

    fun getUsername() : String
    fun setUsername(username : String)

    fun getEmailAddress() : String
    fun setEmailAddress(email : String)

    fun getUserID() : String
    fun setUserID(userID : String)

    fun getAccountCreationDate() : Long
    fun setAccountCreationDate(date : Long)

    fun getCurrentSessionID() : String
    fun setCurrentSessionID(string: String)

    fun getUserSpots() : List<ITreeSpot>
    fun assignSpot(treespot : ITreeSpot)
    fun removeSpot(treespot: ITreeSpot)

    fun getUserFriends() : List<IFriend>
    fun addFriend(friendUUID: UUID)
    fun removeFriend(friendUUID: UUID)


}