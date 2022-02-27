package net.n4dev.treespot.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import io.appwrite.Client
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.User
import io.appwrite.services.Account
import io.appwrite.services.Database
import kotlinx.coroutines.launch
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.api.IViewModel
import net.n4dev.treespot.db.query.InsertUserQuery
import net.n4dev.treespot.ui.TreeSpotActivity
import java.util.*

class RegisterUserViewModel : ViewModel(), IViewModel {

    private lateinit var client: Client
    private lateinit var account: Account
    private lateinit var awDatabase: Database

    private val usersCollectionID = "treespot-users"
    private val userAttEmail = "user_email"
    private val userAttCount = 0
    private val userAttName = "user_name"
    private val userAttID = "user_id"

    override fun init(context: Context) {
        client = TreeSpotApplication.getClient(context)
        account = Account(client)
        awDatabase = Database(client)
    }

    fun registerAccount(emailAddress : String, password : String, username : String, userID: UUID, database: com.couchbase.lite.Database) {
        viewModelScope.launch {
          try {
              val userResponse = account.create(userID.toString(), emailAddress, password, username)
              insertUserIntoDB(userResponse, database)
          }catch (e : AppwriteException) {
              e.printStackTrace()
              Logger.e(e, "Failure to create new account!")
          }
        }
    }

    fun putToSharedPreferences(preferences: SharedPreferences, userID: String) {
        preferences.edit().putString(TreeSpotActivity.PREF_ACTIVE_USERNAME_ID, userID).apply()
    }

    private suspend fun insertUserIntoDB(awUser: User, database: com.couchbase.lite.Database) {

        //Local database
        val user = net.n4dev.treespot.core.User(awUser.name, awUser.email, UUID.fromString(awUser.id))
        user.setAccountCreationDate(System.currentTimeMillis())
        InsertUserQuery.insert(user, database)

        val data = mapOf(userAttID to user.getUserID(),
            userAttCount to 0,
            userAttEmail to user.getEmailAddress(),
            userAttName to user.getUsername())

        //Appwrite Database
        awDatabase.createDocument(usersCollectionID, awUser.id, data, listOf("role:member"))
    }
}