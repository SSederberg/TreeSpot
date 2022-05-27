package net.n4dev.treespot.viewmodel.entity

import androidx.lifecycle.MutableLiveData
import net.n4dev.treespot.core.EntityViewModel
import net.n4dev.treespot.core.api.IUser
import net.n4dev.treespot.core.entity.Friend
import net.n4dev.treespot.core.entity.TreeSpot
import net.n4dev.treespot.core.entity.TypeConst
import net.n4dev.treespot.core.entity.User
import java.util.*

class UserViewModel() : EntityViewModel<User>(), IUser {

    private var userID = MutableLiveData<UUID>()
    private var username = MutableLiveData<String>()
    private var emailAddress = MutableLiveData<String>()
    private var accountCreationDate = MutableLiveData<Long>()
    private var currentSessionID = MutableLiveData<String>()
    private var lastOnline = MutableLiveData<Long>()

    constructor(user: User) : this() {
        copyFrom(user)
    }

    override fun copyFrom(entity: User) {

    }

    override fun copyTo(entity: User) {

    }

    override fun getUsername(): String {
       return username.value.toString()
    }

    override fun setUsername(username: String) {
        this.username.value = username
    }

    override fun getEmailAddress(): String {
        return emailAddress.value.toString()
    }

    override fun setEmailAddress(email: String) {
        this.emailAddress.value = email
    }

    override fun getUserID(): UUID {
        return UUID.fromString(userID.value.toString())
    }

    override fun setUserID(userID: UUID) {
        this.userID.value = userID
    }

    override fun getAccountCreationDate(): Long {
        return accountCreationDate.value.toString().toLong()
    }

    override fun setAccountCreationDate(date: Long) {
        accountCreationDate.value = date
    }

    override fun getCurrentSessionID(): String {
        return currentSessionID.value.toString()
    }

    override fun setCurrentSessionID(string: String) {
        this.currentSessionID.value = string
    }

    override fun getUserSpots(): List<TreeSpot> {
        TODO("Not yet implemented")
    }

    override fun getUserFriends(): List<Friend> {
        TODO("Not yet implemented")
    }

    override fun getLastOnline(): Long {
        return lastOnline.value.toString().toLong()
    }

    override fun setLastOnline(date: Long) {
        this.lastOnline.value = date
    }

    override fun getType(): String {
        return TypeConst.USER
    }

    override fun getEntityID(): String {
        return userID.value.toString()
    }

    override fun isUser(): Boolean {
        return true
    }

    override fun isTreeSpot(): Boolean {
        return false
    }

}