package net.n4dev.treespot.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import io.appwrite.Client
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account
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


    override fun init(context: Context) {
        client = TreeSpotApplication.getClient(context)
        account = Account(client)
        localDatabase = TreeSpotDatabase.getDatabase(context)
    }

    fun registerAccount(emailAddress : String, password : String, username : String, userID: UUID) {
        viewModelScope.launch {
          try {
              val userResponse = account.create(userID.toString(), emailAddress, password, username)
              val objectUser = generateUserObject(emailAddress, username, userID)
              localDatabase.userDAO.insert(objectUser)
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