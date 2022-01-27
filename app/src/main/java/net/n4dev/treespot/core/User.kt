package net.n4dev.treespot.core

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import net.n4dev.treespot.core.User.Companion.name
import net.n4dev.treespot.core.api.ITreeSpot
import net.n4dev.treespot.core.api.IUser
import java.util.UUID
import java.util.Date

@Entity(tableName = name)
data class User(@ColumnInfo(name = USER_USERNAME) private var username: String,
           @ColumnInfo(name = USER_EMAIL_ADDRESS) private var emailAddress: String) : IUser {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = USER_LOCAL_UID) var localUID = 0

   @ColumnInfo(name = USER_ID) private val userID : UUID = UUID.randomUUID()
    @ColumnInfo(name = USER_CREATION_DATE) private var accountCreationDate: Long = -1
    @Ignore private lateinit var userSpots : List<ITreeSpot>
   @Ignore private lateinit var userFriends : List<IUser>

    companion object {
        const val USER_LOCAL_UID = "USER_LOCAL_UID"
        const val USER_USERNAME = "USER_USERNAME"
        const val USER_EMAIL_ADDRESS = "USER_EMAIL_ADDRESS"
        const val USER_ID = "USER_ID"
        const val USER_CREATION_DATE = "USER_CREATION_DATE"
        const val name = "TREESPOT_USER"
    }

    override fun getusername(): String {
        return username
    }

    override fun setusername(username: String) {
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

    override fun setUserID(userID: UUID) {}

    override fun getLocalID(): Int {
        return localUID
    }

    override fun setLocalID(int: Int) {
        this.localUID = int;
    }

    override fun getAccountCreationDate(): Long {
        return accountCreationDate
    }

    override fun setAccountCreationDate(date: Long) {
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
       return true
    }

    override fun isTreeSpot(): Boolean {
        return false
    }
}