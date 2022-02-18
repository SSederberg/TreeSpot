 package net.n4dev.treespot.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import io.appwrite.Client
import io.appwrite.Query
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.Document
import io.appwrite.models.DocumentList
import io.appwrite.models.User
import io.appwrite.services.Avatars
import io.appwrite.services.Database
import kotlinx.coroutines.launch
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.api.IViewModel
import net.n4dev.treespot.ui.friends.add.AddFriendsAdapter

 class AddFriendsViewModel : ViewModel(), IViewModel {

    private lateinit var awDatabase: Database
//    private lateinit var localDatabase : TreeSpotDatabase
    private lateinit var client : Client
    private lateinit var avatars: Avatars

    private val friendsCollectionID = "treespot-friends"
     private val usersCollectionID = "treespot-users"
    private val fieldUserID = "user_id"
    private val fieldFriendID = "friend_id"
    private val fieldFriendsSince = "friends_since"
    private val fieldUserName = "user_name"

    override fun init(context: Context) {
        client = TreeSpotApplication.getClient(context)
        awDatabase = Database(client)
//        localDatabase = TreeSpotDatabase.getDatabase(context)
        avatars = Avatars(client)
    }


    fun createFriendship(user : String, friend : String) {
        viewModelScope.launch {
            val data = mapOf(fieldUserID to user,
                             fieldFriendID to friend,
                             fieldFriendsSince to System.currentTimeMillis())

            try {
                val createFriendsResponse = awDatabase.createDocument(friendsCollectionID, "unique()", data, arrayListOf("role:member"))
            }catch (ex : AppwriteException) {
                Logger.e("An error occurred while trying to create Friendship! :(", ex)}

        }
    }

     fun searchByUsername(usernameInput : String, adapter : AddFriendsAdapter){

         viewModelScope.launch {
            try {
                var queryResponse : DocumentList

                if(usernameInput.isEmpty()) {
                    queryResponse = awDatabase.listDocuments(usersCollectionID)
                } else {
                   queryResponse = awDatabase.listDocuments(usersCollectionID,
                        listOf(Query.search(fieldUserName, usernameInput))
                    )
                }

                val returnedAvatars = ArrayList<ByteArray>()

                for (i in 0 until queryResponse.documents.size) {
                    val document = queryResponse.documents[i]
                    val data = document.data

                    val avatarResponse = avatars.getInitials(data.get(fieldUserName) as String)
                    returnedAvatars.add(avatarResponse)
                }

                adapter.setUsers(queryResponse.documents as ArrayList<Document>)
                adapter.setAvatars(returnedAvatars)
                adapter.notifyItemRangeChanged(0, queryResponse.documents.size)
            }catch (ex: AppwriteException) {
                Logger.e(ex, "")
            }
         }

     }

     fun searchByAddress(addressInput : String) : List<User> {
         val returnedUsers = ArrayList<User>()
         viewModelScope.launch {  }
         return returnedUsers
     }
}