package net.n4dev.treespot.core

import androidx.room.Entity
import androidx.room.PrimaryKey
import net.n4dev.treespot.core.api.ITreeSpot
import net.n4dev.treespot.core.api.IUser
import java.util.*

@Entity
class User : IUser {

    constructor(username: String, emailAddress: String) {
        this.username = username
        this.emailAddress = emailAddress
    }


    private lateinit var username: String
    private lateinit var emailAddress : String
    private lateinit var userID : UUID
    private lateinit var accountCreationDate : Date
    private lateinit var userSpots : List<ITreeSpot>
    private lateinit var userFriends : List<IUser>


    override fun getUserName(): String {
        return username
    }

    override fun setUserName(username: String) {
        this.username = username
    }

    override fun getEmailAddress(): String {
        return emailAddress
    }

    override fun setEmailAddress(email: String) {
        this.emailAddress = email
    }

    override fun getUserID(): UUID {
        return userID
    }

    override fun setUserID(userID: UUID) {
        this.userID = userID
    }

    override fun getLocalID(): Int {
        return localUID
    }

    override fun setLocalID(int: Int) {
        this.localUID = int;
    }

    override fun getAccountCreationDate(): Date {
        return accountCreationDate
    }

    override fun setAccountCreationDate(date: Date) {
        this.accountCreationDate = date
    }

    override fun getUserSpots(): List<ITreeSpot> {
        return userSpots
    }

    override fun assignSpot(treespot: ITreeSpot) {
        TODO("Not yet implemented")
    }

    override fun removeSpot(treespot: ITreeSpot) {
        TODO("Not yet implemented")
    }

    override fun getUserFriends(): List<IUser> {
        return this.userFriends
    }

    override fun addFriend(friendUUID: UUID) {
        TODO("Not yet implemented")
    }

    override fun removeFriend(friendUUID: UUID) {
        TODO("Not yet implemented")
    }

    override fun getType(): String {
        return TypeConst.USER
    }

    override fun getEntityID(): String {
        TODO("Not yet implemented")
    }

    override fun isUser(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isTreeSpot(): Boolean {
        TODO("Not yet implemented")
    }
}