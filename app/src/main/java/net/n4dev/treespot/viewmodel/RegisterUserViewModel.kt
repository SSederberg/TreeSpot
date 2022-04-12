package net.n4dev.treespot.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import io.appwrite.Client
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.User
import io.appwrite.services.Account
import io.appwrite.services.Database
import kotlinx.coroutines.launch
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.AbstractViewModel
import net.n4dev.treespot.db.constants.TreeSpotUserConstants
import net.n4dev.treespot.ui.TreeSpotActivity
import java.util.*

class RegisterUserViewModel : AbstractViewModel() {

    private lateinit var client: Client
    private lateinit var account: Account
    private lateinit var awDatabase: Database

    private val usersCollectionID = TreeSpotUserConstants.name
    private val userAttEmail = TreeSpotUserConstants.EMAIL_ADDRESS
    private val userAttCount = TreeSpotUserConstants.FRIEND_COUNT
    private val userAttName = TreeSpotUserConstants.USERNAME
    private val userAttID = TreeSpotUserConstants.USER_ID
    private val userAttDate = TreeSpotUserConstants.USER_CREATION_DATE
    private val userLateOnline = TreeSpotUserConstants.LAST_ONLINE

    override fun init(context: Context) {
        client = TreeSpotApplication.getClient(context)
        account = Account(client)
        awDatabase = Database(client)
    }

    fun registerAccount(emailAddress : String, password : String, username : String, userID: UUID) {
        viewModelScope.launch {
          try {
              val userResponse = account.create(userID.toString(), emailAddress, password, username)
              insertUserIntoDB(userResponse)
          }catch (e : AppwriteException) {
              e.printStackTrace()
              Logger.e(e, "Failure to create new account!")
          }
        }
    }

    fun putToSharedPreferences(preferences: SharedPreferences, userID: String) {
        preferences.edit().putString(TreeSpotActivity.PREF_ACTIVE_USERNAME_ID, userID).apply()
    }

    private suspend fun insertUserIntoDB(awUser: User) {

        val time = System.currentTimeMillis()
        //Local database
        val user = net.n4dev.treespot.db.entity.User(
            awUser.name,
            awUser.email,
            UUID.fromString(awUser.id)
        )
        user.setAccountCreationDate(time)
        user.setLastOnline(time)

        val data = mapOf(userAttID to user.getUserID(),
            userAttCount to 0,
            userAttEmail to user.getEmailAddress(),
            userAttName to user.getUsername(),
            userAttDate to time,
            userLateOnline to time)

        //Appwrite Database
        awDatabase.createDocument(usersCollectionID, awUser.id, data, listOf("role:member"))
    }
}