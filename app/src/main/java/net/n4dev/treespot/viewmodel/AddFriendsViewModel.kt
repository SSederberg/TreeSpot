 package net.n4dev.treespot.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import io.appwrite.Client
import io.appwrite.Query
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.User
import io.appwrite.services.Avatars
import io.appwrite.services.Database
import kotlinx.coroutines.launch
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.api.IViewModel
import net.n4dev.treespot.db.TreeSpotDatabase

 class AddFriendsViewModel : ViewModel(), IViewModel {

    private lateinit var awDatabase: Database
    private lateinit var localDatabase : TreeSpotDatabase
    private lateinit var client : Client
    private lateinit var avatars: Avatars
    private val friendsCollectionID = "treespot-friends"
    private val fieldUserID = "userID"
    private val fieldFriendID = "userIDOfFriend"
    private val fieldFriendsSince = "friendsSince"

    override fun init(context: Context) {
        client = TreeSpotApplication.getClient(context)
        awDatabase = Database(client)
        localDatabase = TreeSpotDatabase.getDatabase(context)
        avatars = Avatars(client)
    }


    fun createFriendship(user : String, friend : String) {
        viewModelScope.launch {
            val data = mapOf(fieldUserID to user,
                             fieldFriendID to friend,
                             fieldFriendsSince to System.currentTimeMillis())

            try {
                val createFriendsResponse = awDatabase.createDocument(friendsCollectionID, "unique()", data, arrayListOf("role:member"), arrayListOf("role:member"))
            }catch (ex : AppwriteException) {
                Logger.e("An error occurred while trying to create Friendship! :(", ex)}

        }
    }

     fun searchByUsername(usernameInput : String, toReturnAvatars : ArrayList<Bitmap>) : ArrayList<User> {
         val returnedUsers = ArrayList<User>()
         viewModelScope.launch {
            try {
                val queryResponse = awDatabase.listDocuments("61e61ac924a1ae90810c",
                    queries = listOf(Query.equal("username", usernameInput))
                )

                for (i in 0 until queryResponse.documents.size) {
                    val document = queryResponse.documents.get(i)
                    val data = document.data

                    val avatarResponse = avatars.getInitials(data.get("username") as String)
                    val bmp = BitmapFactory.decodeByteArray(avatarResponse, 0, avatarResponse.size)
                    toReturnAvatars.add(bmp)
                }

                Logger.d(queryResponse.documents)
            }catch (ex: AppwriteException) {
                ex.printStackTrace()
            }
         }

         return returnedUsers
     }

     fun searchByAddress(userID: String, addressInput : String) : List<User> {
         val returnedUsers = ArrayList<User>()
         viewModelScope.launch {  }
         return returnedUsers
     }


}