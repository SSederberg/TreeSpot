package net.n4dev.treespot.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import io.appwrite.Client
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account
import io.appwrite.services.Database
import kotlinx.coroutines.launch
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.User
import net.n4dev.treespot.core.api.IViewModel
import net.n4dev.treespot.db.TreeSpotDatabase
import net.n4dev.treespot.ui.TreeSpotActivity
import java.util.*

class RegisterUserViewModel : ViewModel(), IViewModel {

    private lateinit var client: Client
    private lateinit var account: Account
    private lateinit var localDatabase : TreeSpotDatabase
    private lateinit var awDatabase: Database

    private val awUserID = "userID"
    private val awUsername = "username"
    private val awEmailAddress = "emailAddress"
    private val awFriendCount = "friendCount"
    private val usersCollectionID = "61e61ac924a1ae90810c"


    override fun init(context: Context) {
        client = TreeSpotApplication.getClient(context)
        account = Account(client)
        awDatabase = Database(client)
        localDatabase = TreeSpotDatabase.getDatabase(context)
    }

    fun registerAccount(emailAddress : String, password : String, username : String, userID: UUID) {
        val awData = mapOf(awUsername to username,
                            awEmailAddress to emailAddress,
                            awFriendCount to 0,
                            awUserID to userID.toString())

        viewModelScope.launch {
          try {
              val userResponse = account.create(userID.toString(), emailAddress, password, username)
              val sessionResponse = account.createSession(emailAddress, password)
              val dbResponse = awDatabase.createDocument(usersCollectionID, userID.toString(), awData)

              val objectUser = generateUserObject(emailAddress, username, userID)
//              localDatabase.userDAO.insert(objectUser)
              Logger.json(userResponse.toString())
          }catch (e : AppwriteException) {
              Logger.e(e, "Failure to create new account!")

          }
        }
    }

    fun putToSharedPreferences(preferences: SharedPreferences, userID: String) {
        preferences.edit().putString(TreeSpotActivity.PREF_ACTIVE_USERNAME_ID, userID).apply()
    }

    private fun generateUserObject(emailAddress: String, username: String, userID: UUID) : User {
        return User(username, emailAddress, userID)
    }
}