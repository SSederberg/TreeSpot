package net.n4dev.treespot.viewmodel.entity

import androidx.lifecycle.MutableLiveData
import net.n4dev.treespot.core.EntityViewModel
import net.n4dev.treespot.core.TreeSpotException
import net.n4dev.treespot.core.api.IFriend
import net.n4dev.treespot.core.entity.Friend
import net.n4dev.treespot.core.entity.TreeSpot
import net.n4dev.treespot.core.entity.TypeConst
import java.util.*

class FriendViewModel() : EntityViewModel<Friend>(), IFriend {

    private var friendID = MutableLiveData<UUID>()
    private var friendsSince = MutableLiveData<Long>()
    private var userID = MutableLiveData<UUID>()
    private var friendPairID = MutableLiveData<UUID>()
    private var username = MutableLiveData<String>()
    private var emailAddress = MutableLiveData<String>()
    private var accountCreationDate = MutableLiveData<Long>()
    private var lastOnline = MutableLiveData<Long>()


    constructor(friend: Friend) : this() {
        friendID.value = friend.getFriendID()
        friendID.value = friend.getFriendID()
        friendsSince.value = friend.getFriendsSince()
        userID.value = friend.getUserID()
        friendPairID.value = friend.getFriendPairID()
        username.value = friend.getUsername()
        emailAddress.value = friend.getEmailAddress()
        accountCreationDate.value = friend.getAccountCreationDate()
        lastOnline.value = friend.getLastOnline()
    }

    override fun getFriendID(): UUID {
        return friendID.value!!
    }

    override fun setFriendID(uuid: UUID) {
        friendID.postValue(uuid)
    }

    override fun getFriendPairID(): UUID {
        return friendPairID.value!!
    }

    override fun setFriendPairID(uuid: UUID) {
        friendPairID.postValue(uuid)
    }

    override fun getFriendsSince(): Long {
        return friendsSince.value!!
    }

    override fun setFriendsSince(date: Long) {
        friendsSince.postValue(date)
    }

    override fun getLocalID(): Long {
        throw TreeSpotException("Interface method in this class is not supported!")
    }

    override fun getUsername(): String {
        return username.value!!
    }

    override fun setUsername(username: String) {
        this.username.postValue(username)
    }

    override fun getEmailAddress(): String {
        return emailAddress.value!!
    }

    override fun setEmailAddress(email: String) {
        emailAddress.postValue(email)
    }

    override fun getUserID(): UUID {
        return userID.value!!
    }

    override fun setUserID(userID: UUID) {
        this.userID.postValue(userID)
    }

    override fun getAccountCreationDate(): Long {
        return accountCreationDate.value!!
    }

    override fun setAccountCreationDate(date: Long) {
        accountCreationDate.postValue(date)
    }

    override fun getCurrentSessionID(): String {
        throw TreeSpotException("Interface method in this class is not supported!")
    }

    override fun setCurrentSessionID(string: String) {
        throw TreeSpotException("Interface method in this class is not supported!")
    }

    override fun getUserSpots(): List<TreeSpot> {
        TODO("Not yet implemented")
    }

    override fun getUserFriends(): List<Friend> {
        TODO("Not yet implemented")
    }

    override fun getLastOnline(): Long {
        return lastOnline.value!!
    }

    override fun setLastOnline(date: Long) {
      lastOnline.postValue(date)
    }

    override fun getType(): String {
        return TypeConst.FRIEND
    }

    override fun getEntityID(): String {
        return friendID.toString()
    }

    override fun isUser(): Boolean {
        return true
    }

    override fun isTreeSpot(): Boolean {
        return false
    }

    override fun copyFrom(entity: Friend) {

    }

    override fun copyTo(entity: Friend) {

    }
}