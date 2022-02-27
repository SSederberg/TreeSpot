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
import net.n4dev.treespot.db.TreeSpotDatabases
import net.n4dev.treespot.db.TreeSpotFriendRequestsDatabase
import net.n4dev.treespot.db.TreeSpotFriendsDatabase
import net.n4dev.treespot.db.TreeSpotUserDB
import net.n4dev.treespot.db.query.InsertFriendRequestQuery
import net.n4dev.treespot.ui.friends.add.AddFriendsAdapter

 class AddFriendsViewModel : ViewModel(), IViewModel {

    private lateinit var awDatabase: Database
    private lateinit var client : Client
    private lateinit var avatars: Avatars
    private lateinit var couchbase : TreeSpotDatabases

    private val friendRequestCollectionID = "Tree-Spot-Friend-Requests"
    private val usersCollectionID = TreeSpotUserDB.name
    private val friendsCollectionID = TreeSpotFriendsDatabase.name
    private val fieldUserID = "user_id"
    private val fieldFriendID = "friend_id"
    private val fieldFriendsSince = "friends_since"
    private val fieldUserName = "user_name"

    override fun init(context: Context) {
        client = TreeSpotApplication.getClient(context)
        awDatabase = Database(client)
        avatars = Avatars(client)
        couchbase = TreeSpotDatabases()
    }


    fun createFriendship(user : String, friend : String) {
        viewModelScope.launch {
            val data = mapOf(TreeSpotFriendRequestsDatabase.CALLED_BY_ID to user,
                             TreeSpotFriendRequestsDatabase.FRIEND_ID to friend,
                             TreeSpotFriendRequestsDatabase.REQUEST_DATE to System.currentTimeMillis(),
                             TreeSpotFriendRequestsDatabase.WAS_ACCEPTED to false)

            try {
                val createFriendsResponse = awDatabase.createDocument(friendRequestCollectionID, "unique()", data, arrayListOf("role:member"))
                insertRequestIntoDB(data)
            }catch (ex : AppwriteException) {
                ex.printStackTrace()
                Logger.e("An error occurred while trying to create Friendship! :(", ex)}

        }
    }

     private fun insertRequestIntoDB(data: Map<String, Any>) {
         InsertFriendRequestQuery.insert(data, couchbase.friendRequestsDB)
     }


     fun searchByUsername(usernameInput : String, adapter : AddFriendsAdapter, calledByUsername : String){

         viewModelScope.launch {
            try {
                var queryResponse : DocumentList

                if(usernameInput.isEmpty()) {
                    queryResponse = awDatabase.listDocuments(usersCollectionID,
                        listOf(Query.notEqual(fieldUserName, calledByUsername))
                    )
                } else {
                   queryResponse = awDatabase.listDocuments(usersCollectionID,
                        listOf(Query.search(fieldUserID, usernameInput))
                    )
                }

//                val alreadyFriendsResponse = awDatabase.listDocuments(friendRequestCollectionID,
//                listOf(Query.equal(TreeSpotFriendRequestsDatabase.CALLED_BY_ID, calledByUsername)))

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
                ex.printStackTrace()
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