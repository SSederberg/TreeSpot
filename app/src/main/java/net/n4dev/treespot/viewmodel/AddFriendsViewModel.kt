 package net.n4dev.treespot.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import io.appwrite.Client
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Database
import kotlinx.coroutines.launch
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.api.IUser
import net.n4dev.treespot.core.api.IViewModel
import net.n4dev.treespot.db.TreeSpotDatabase

 class AddFriendsViewModel : ViewModel(), IViewModel {

    private lateinit var awDatabase: Database
    private lateinit var localDatabase : TreeSpotDatabase
    private lateinit var client : Client
    private val friendsCollectionID = "treespot-friends"
    private val fieldUserID = "userID"
    private val fieldFriendID = "userIDOfFriend"
    private val fieldFriendsSince = "friendsSince"

    override fun init(context: Context) {
        client = TreeSpotApplication.getClient(context)
        awDatabase = Database(client)
        localDatabase = TreeSpotDatabase.getDatabase(context)
    }


    fun createFriendship(user : IUser, friend : IUser) {
        viewModelScope.launch {
            val data = mapOf(fieldUserID to user.getUserID(),
                             fieldFriendID to friend.getUserID(),
                             fieldFriendsSince to System.currentTimeMillis())

            try {
                val createFriendsResponse = awDatabase.createDocument(friendsCollectionID, "unique()", data)
            }catch (ex : AppwriteException) {
                Logger.e("An error occurred while trying to create Friendship! :(", ex)}

        }
    }


}