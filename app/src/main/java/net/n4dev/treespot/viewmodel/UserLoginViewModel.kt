package net.n4dev.treespot.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import io.appwrite.Client
import io.appwrite.Query
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.User
import io.appwrite.services.Account
import io.appwrite.services.Avatars
import io.appwrite.services.Database
import io.objectbox.Box
import kotlinx.coroutines.launch
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.api.IViewModel
import net.n4dev.treespot.db.TreeSpotObjectBox
import net.n4dev.treespot.db.constants.TreeSpotFriendsConstants
import net.n4dev.treespot.db.constants.TreeSpotUserConstants
import net.n4dev.treespot.db.entity.Friend
import net.n4dev.treespot.db.entity.TreeSpot
import net.n4dev.treespot.ui.TreeSpotActivity
import net.n4dev.treespot.util.ActivityUtil
import java.io.FileOutputStream
import java.util.*

class UserLoginViewModel : ViewModel(), IViewModel {

    private lateinit var client: Client
    private lateinit var account: Account
    private lateinit var avatars: Avatars
    private lateinit var awDatabase: Database
    private lateinit var userBox: Box<net.n4dev.treespot.db.entity.User>
    private lateinit var friendBox : Box<Friend>
    private lateinit var spotBox : Box<TreeSpot>
    private lateinit var loggedInUserID : String

   override fun init(context: Context) {
        client = TreeSpotApplication.getClient(context)
        account = Account(client)
        awDatabase = Database(client)
        avatars = Avatars(client)
        userBox = TreeSpotObjectBox.getBoxStore().boxFor(net.n4dev.treespot.db.entity.User::class.java)
        friendBox = TreeSpotObjectBox.getBoxStore().boxFor(Friend::class.java)
        spotBox = TreeSpotObjectBox.getBoxStore().boxFor(TreeSpot::class.java)
    }

    fun attemptLogin(emailAddress : String, password : String, sharedPreferences: SharedPreferences, context: Context) {
        viewModelScope.launch {
            try {
                if (!sessionExists()) {

                    val response = account.createSession(emailAddress, password)
                    sharedPreferences.edit().putString(TreeSpotActivity.PREF_ACTIVE_SESSION_ID, response.id).apply()
                    sharedPreferences.edit().putString(TreeSpotActivity.PREF_ACTIVE_USERNAME_ID, response.userId).apply()

                    loggedInUserID = account.get().id
                    createUserInDB(account.get())

                    pullUserData(account.get(), context)
                }
            } catch (e: AppwriteException) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun pullUserData(get: User, context: Context) {
        pullFriendsData(get, context)
        pullSpotData(get)
        pullSettingsData(get)
    }

    private suspend fun pullSettingsData(get: User) {

    }

    private suspend fun pullFriendsData(get: User, context: Context) {
        val friendQuery = listOf(Query.equal(TreeSpotFriendsConstants.USER_ID, get.id))
        val friendDocuments = awDatabase.listDocuments(TreeSpotFriendsConstants.name, friendQuery, 100)

        for (document in friendDocuments.documents) {
            val data = document.data
            val userQuery = listOf(Query.equal(TreeSpotUserConstants.USER_ID, data.get(TreeSpotFriendsConstants.FRIEND_ID)!!))
            val userDocumentResponse = awDatabase.listDocuments(TreeSpotUserConstants.name, userQuery, 5)
            val tempFriend = Friend()
            val friendData = document.data

            for(userDocument in userDocumentResponse.documents) {
                val userData = userDocument.data

                val email = userData.get(TreeSpotUserConstants.EMAIL_ADDRESS)
                val username = userData.get(TreeSpotUserConstants.USERNAME)
                val creationDate = userData.get(TreeSpotUserConstants.USER_CREATION_DATE)
                val userID = userData.get(TreeSpotFriendsConstants.USER_ID)

                val friendID = friendData.get(TreeSpotFriendsConstants.FRIEND_ID)
                val since = friendData.get(TreeSpotFriendsConstants.FRIENDS_SINCE)
                val pairID = friendData.get(TreeSpotFriendsConstants.FRIEND_PAIR_ID)

                tempFriend.setEmailAddress(email.toString())
                tempFriend.setUsername(username.toString())
                tempFriend.setUserID(UUID.fromString(get.id))
                tempFriend.setAccountCreationDate(creationDate as Long)
                tempFriend.setFriendID(UUID.fromString(friendID.toString()))
                tempFriend.setFriendsSince(since as Long)
                tempFriend.setFriendPairID(UUID.fromString(pairID.toString()))
                friendBox.put(tempFriend)

                pullFriendAvatar(username.toString(), friendID.toString(), context)
            }

        }
    }

    private fun pullSpotData(get : User) {

    }


    private fun createUserInDB(get: User) {
        val convert = net.n4dev.treespot.db.entity.User.convertFromAWUser(get)
        userBox.removeAll()
        userBox.put(convert)
    }

    private suspend fun pullFriendAvatar(username: String, friendID : String, context: Context) {
        val avatarResponse = avatars.getInitials(username)
        val path = ActivityUtil.getAppFriendImagesDirectory(context) + "avatar_" + friendID + ".png"
        val outPutStream = FileOutputStream(path)
        outPutStream.write(avatarResponse)
        outPutStream.flush()
        outPutStream.close()

        Logger.i(path)
    }

    fun sessionExists() : Boolean {
        return false
    }

    fun getLoggedInUserID() : String {
        return loggedInUserID
    }



}