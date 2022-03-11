package net.n4dev.treespot.core.api

import androidx.annotation.Nullable
import net.n4dev.treespot.db.entity.Friend
import net.n4dev.treespot.db.entity.TreeSpot

interface IUser : IEntity {

    fun getUsername() : String
    fun setUsername(username : String)

    fun getEmailAddress() : String
    fun setEmailAddress(email : String)

    fun getUserID() : String
    fun setUserID(userID : String)

    fun getAccountCreationDate() : Long
    fun setAccountCreationDate(date : Long)

    @Nullable
    fun getCurrentSessionID() : String
    fun setCurrentSessionID(string: String)

    fun getUserSpots() : List<TreeSpot>

    fun getUserFriends() : List<Friend>


}