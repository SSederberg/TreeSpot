package net.n4dev.treespot.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import io.appwrite.Client
import io.appwrite.Query
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.User
import io.appwrite.services.Account
import io.appwrite.services.Avatars
import io.appwrite.services.Databases
import io.appwrite.services.Storage
import io.objectbox.Box
import kotlinx.coroutines.launch
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.AppwriteViewModel
import net.n4dev.treespot.db.TreeSpotObjectBox
import net.n4dev.treespot.db.constants.TreeSpotFriendsConstants
import net.n4dev.treespot.db.constants.TreeSpotMediaConstants
import net.n4dev.treespot.db.constants.TreeSpotUserConstants
import net.n4dev.treespot.db.constants.TreeSpotsConstants
import net.n4dev.treespot.ui.TreeSpotActivity
import net.n4dev.treespot.ui.main.MainActivity
import net.n4dev.treespot.util.ActivityUtil
import java.io.FileOutputStream
import java.util.*

class UserLoginViewModel : AppwriteViewModel() {

    private lateinit var client: Client
    private lateinit var account: Account
    private lateinit var avatars: Avatars
    private lateinit var awDatabase: Databases
    private lateinit var awFiles : Storage

    private lateinit var userBox: Box<net.n4dev.treespot.core.entity.User>
    private lateinit var friendBox : Box<net.n4dev.treespot.core.entity.Friend>
    private lateinit var spotBox : Box<net.n4dev.treespot.core.entity.TreeSpot>
    private lateinit var loggedInUserID : String

   override fun init(context: Context) {
        client = TreeSpotApplication.getClient(context)
//        awDatabase = super.getAppWriteDatabase(context)
        avatars = super.getAppWriteAvatars(context)
        account = Account(client)
        awFiles = Storage(client)

        userBox = TreeSpotObjectBox.getBoxStore().boxFor(net.n4dev.treespot.core.entity.User::class.java)
        friendBox = TreeSpotObjectBox.getBoxStore().boxFor(net.n4dev.treespot.core.entity.Friend::class.java)
        spotBox = TreeSpotObjectBox.getBoxStore().boxFor(net.n4dev.treespot.core.entity.TreeSpot::class.java)
    }

    fun attemptLogin(emailAddress : String, password : String, sharedPreferences: SharedPreferences, context: Context) {
//        val context = activity.applicationContext
        viewModelScope.launch {
            try {
                if (!sessionExists()) {

                    val response = account.createEmailSession(emailAddress, password)
                    sharedPreferences.edit().putString(TreeSpotActivity.PREF_ACTIVE_SESSION_ID, response.id).apply()
                    sharedPreferences.edit().putString(TreeSpotActivity.PREF_ACTIVE_USERNAME_ID, response.userId).apply()

                    loggedInUserID = account.get().id
                    createUserInDB(account.get(), response.id)

                    pullUserData(account.get(), context)

                    val bundle = Bundle()
                    bundle.putString(MainActivity.ARG_USER_ID, loggedInUserID)
                    ActivityUtil.startActivity(bundle, MainActivity::class.java, context, true)
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
            val tempFriend = net.n4dev.treespot.core.entity.Friend()
            val friendData = document.data

            for(userDocument in userDocumentResponse.documents) {
                val userData = userDocument.data

                val email = userData[TreeSpotUserConstants.EMAIL_ADDRESS]
                val username = userData[TreeSpotUserConstants.USERNAME]
                val creationDate = userData[TreeSpotUserConstants.USER_CREATION_DATE]
                val lastOnline = userData[TreeSpotUserConstants.LAST_ONLINE]
                val friendID = friendData[TreeSpotFriendsConstants.FRIEND_ID]
                val since = friendData[TreeSpotFriendsConstants.FRIENDS_SINCE]
                val pairID = friendData[TreeSpotFriendsConstants.FRIEND_PAIR_ID]

                tempFriend.setEmailAddress(email.toString())
                tempFriend.setUsername(username.toString())
                tempFriend.setUserID(UUID.fromString(get.id))
                tempFriend.setAccountCreationDate(creationDate as Long)
                tempFriend.setFriendID(UUID.fromString(friendID.toString()))
                tempFriend.setFriendsSince(since as Long)
                tempFriend.setFriendPairID(UUID.fromString(pairID.toString()))
                tempFriend.setLastOnline(lastOnline as Long)
                friendBox.put(tempFriend)

                pullFriendAvatar(username.toString(), friendID.toString(), context)
                pullFriendSpots(friendID.toString())
            }

        }
    }


    private suspend fun pullSpotData(get : User) {
        val spotQuery = listOf(Query.equal(TreeSpotsConstants.SPOT_OWNER_ID, get.id))

        val spotDocumentResponse = awDatabase.listDocuments(
            TreeSpotsConstants.name,
            spotQuery)

        for(locationDoc in spotDocumentResponse.documents) {
            val spotData = locationDoc.data

            val ownerID = spotData[TreeSpotsConstants.SPOT_OWNER_ID]
            val latNorth = spotData[TreeSpotsConstants.SPOT_LAT_NORTH]
            val longWest = spotData[TreeSpotsConstants.SPOT_LONG_WEST]
            val creationDate = spotData[TreeSpotsConstants.SPOT_CREATION_DATE]
            val spotID = spotData[TreeSpotsConstants.SPOT_UUID]
            val description = spotData[TreeSpotsConstants.SPOT_DESCRIPTION]
            val privateDescription = spotData[TreeSpotsConstants.SPOT_PRIVATE_DESCRIPTION]

            if(privateDescription == null) {
                val tempSpot = net.n4dev.treespot.core.entity.TreeSpot(
                    latNorth as String,
                    longWest as String,
                    creationDate as Long,
                    spotID as String,
                    description as String,
                    ownerID as String,
                )

                spotBox.put(tempSpot)

            } else {
                val tempSpot = net.n4dev.treespot.core.entity.TreeSpot(
                    latNorth as String,
                    longWest as String,
                    creationDate as Long,
                    spotID as String,
                    description as String,
                    privateDescription as String,
                    ownerID as String,
                )

                spotBox.put(tempSpot)
            }
            pullSpotMediaData(spotID)
        }
    }

    private suspend fun pullSpotMediaData(spotID: String) {
        val mediaQuery = listOf(Query.equal(TreeSpotMediaConstants.SPOT_ID, spotID))

        val mediaFileResponse = awFiles.listFiles("ts_bucket_pictures", spotID)
    }


    private fun createUserInDB(get: User, sessionID : String) {
        val convert = net.n4dev.treespot.core.entity.User.convertFromAWUser(get, sessionID)
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

    private suspend fun pullFriendSpots(friendID : String) {
        val friendSpotQuery = listOf(Query.equal(TreeSpotsConstants.SPOT_OWNER_ID, friendID))
        val spotDocumentResponse = awDatabase.listDocuments(
            TreeSpotsConstants.name,
            friendSpotQuery)

        for(locationDoc in spotDocumentResponse.documents) {
            val spotData = locationDoc.data

            val ownerID = spotData[TreeSpotsConstants.SPOT_OWNER_ID]
            val latNorth = spotData[TreeSpotsConstants.SPOT_LAT_NORTH]
            val longWest = spotData[TreeSpotsConstants.SPOT_LONG_WEST]
            val creationDate = spotData[TreeSpotsConstants.SPOT_CREATION_DATE]
            val spotID = spotData[TreeSpotsConstants.SPOT_UUID]
            val description = spotData[TreeSpotsConstants.SPOT_DESCRIPTION]
            val privateDescription = spotData[TreeSpotsConstants.SPOT_PRIVATE_DESCRIPTION]
            val isFavorite = spotData[TreeSpotsConstants.SPOT_FAVORITE]

            if(privateDescription == null) {
                val tempSpot = net.n4dev.treespot.core.entity.TreeSpot(
                    latNorth as String,
                    longWest as String,
                    creationDate as Long,
                    spotID as String,
                    description as String,
                    ownerID as String,
                )

                spotBox.put(tempSpot)
            } else {
                val tempSpot = net.n4dev.treespot.core.entity.TreeSpot(
                    latNorth as String,
                    longWest as String,
                    creationDate as Long,
                    spotID as String,
                    description as String,
                    privateDescription as String,
                    ownerID as String,
                )

                spotBox.put(tempSpot)
            }
        }
    }

    fun sessionExists() : Boolean {
//        if(userBox.all.size > 0) {
//            val user = userBox.all[0]
//
//            return user.getCurrentSessionID() != null
//        }
        return false
    }

    fun getLoggedInUserID() : String {
        return loggedInUserID
    }



}