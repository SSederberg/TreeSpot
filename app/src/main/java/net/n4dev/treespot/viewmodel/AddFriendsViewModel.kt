 package net.n4dev.treespot.viewmodel

import android.content.Context
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
import net.n4dev.treespot.core.AbstractViewModel
import net.n4dev.treespot.db.constants.TreeSpotFriendRequestConstants
import net.n4dev.treespot.db.constants.TreeSpotFriendsConstants
import net.n4dev.treespot.db.constants.TreeSpotUserConstants
import net.n4dev.treespot.db.query.GetUserFriendsQuery
import net.n4dev.treespot.ui.friends.add.AddFriendsAdapter
import java.util.*

 class AddFriendsViewModel : AbstractViewModel() {

    private lateinit var awDatabase: Database
    private lateinit var client : Client
    private lateinit var avatars: Avatars

    private val friendRequestCollectionID = TreeSpotFriendRequestConstants.name
    private val usersCollectionID = TreeSpotUserConstants.name
    private val friendsCollectionID = TreeSpotFriendsConstants.name
    private val fieldUserName = TreeSpotUserConstants.USERNAME
     private lateinit var userID : String

    override fun init(context: Context) {
        client = TreeSpotApplication.getClient(context)
        awDatabase = Database(client)
        avatars = Avatars(client)
    }

     fun setID(userID: String) {
         this.userID = userID
     }


    fun createFriendship(user : String, friend : String) {
        viewModelScope.launch {
            val data = mapOf(
                TreeSpotFriendRequestConstants.CALLED_BY_ID to user,
                             TreeSpotFriendRequestConstants.FRIEND_ID to friend,
                             TreeSpotFriendRequestConstants.REQUEST_DATE to System.currentTimeMillis(),
                             TreeSpotFriendRequestConstants.WAS_ACCEPTED to false)

            try {
                val createFriendsResponse = awDatabase.createDocument(friendRequestCollectionID, "unique()", data, arrayListOf("role:member"))
                insertRequestIntoDB()
            }catch (ex : AppwriteException) {
                ex.printStackTrace()
                Logger.e("An error occurred while trying to create Friendship! :(", ex)}

        }
    }

     fun createFriendshipTemp(user: String, friend : String) {
         viewModelScope.launch {
             val data = mapOf(
                 TreeSpotFriendsConstants.FRIEND_ID to friend,
                 TreeSpotFriendsConstants.USER_ID to user,
                 TreeSpotFriendsConstants.FRIEND_PAIR_ID to UUID.randomUUID().toString(),
                 TreeSpotFriendsConstants.FRIENDS_SINCE to System.currentTimeMillis()
             )

             val createFriends = awDatabase.createDocument(friendsCollectionID, "unique()", data, arrayListOf("role:member"))
         }
     }

     private fun insertRequestIntoDB() {
     }


     fun searchByUsername(usernameInput : String, adapter : AddFriendsAdapter, calledByUsername : String){

         viewModelScope.launch {
            try {
                val queryResponse : DocumentList
                val listOfQueries : List<String> = getFriendsOfUser(usernameInput)

                if(usernameInput.isEmpty()) {
                    queryResponse = awDatabase.listDocuments(usersCollectionID, listOfQueries)
                } else {
                   queryResponse = awDatabase.listDocuments(usersCollectionID,
                        listOf(Query.search(fieldUserName, usernameInput))
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

     private fun getFriendsOfUser(usernameInput: String): List<String> {
         val queryArray = ArrayList<String>()
         queryArray.add(Query.notEqual(fieldUserName, usernameInput))

         val friendsQuery = GetUserFriendsQuery.get(userID).find()

         for(friend in friendsQuery) {
             val id = friend.getFriendID().toString()

             queryArray.add(Query.notEqual(userID, id))
         }
         return queryArray
     }

     fun searchByAddress(addressInput : String) : List<User> {
         val returnedUsers = ArrayList<User>()
         viewModelScope.launch {  }
         return returnedUsers
     }
}