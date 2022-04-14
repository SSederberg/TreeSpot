package net.n4dev.treespot.core.api

import androidx.annotation.Nullable
import net.n4dev.treespot.core.entity.Friend
import net.n4dev.treespot.core.entity.TreeSpot
import java.util.*

interface IUser : IEntity {

    /**
     * The publicly visible nick name of the user
     */
    fun getUsername() : String
    fun setUsername(username : String)

    /**
     * The email address associated with this acoount
     */
    fun getEmailAddress() : String
    fun setEmailAddress(email : String)

    /**
     * The user UUID of this account
     */
    fun getUserID() : UUID
    fun setUserID(userID : UUID)

    /**
     * The unix timestamp of when this account was registered.
     */
    fun getAccountCreationDate() : Long
    fun setAccountCreationDate(date : Long)

    /**
     * Current and valid session id provided by AppWrite.
     */
    @Nullable
    fun getCurrentSessionID() : String
    fun setCurrentSessionID(string: String)

    /**
     * Gets all captured spots associated with a user
     */
    fun getUserSpots() : List<TreeSpot>

    /**
     * Gets all friends associated with a user
     */
    fun getUserFriends() : List<Friend>

    /**
     * Gets Unix timestamp of the last time this user logged in.
     */
    fun getLastOnline() : Long
    fun setLastOnline(date : Long)

}