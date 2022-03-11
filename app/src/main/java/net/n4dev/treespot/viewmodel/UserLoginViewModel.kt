package net.n4dev.treespot.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.appwrite.Client
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.User
import io.appwrite.services.Account
import kotlinx.coroutines.launch
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.api.IViewModel
import net.n4dev.treespot.ui.TreeSpotActivity

class UserLoginViewModel : ViewModel(), IViewModel {

    private lateinit var client: Client
    private lateinit var account: Account

   override fun init(context: Context) {
        client = TreeSpotApplication.getClient(context)
        account = Account(client)
    }

    fun attemptLogin(emailAddress : String, password : String, sharedPreferences: SharedPreferences) {
        viewModelScope.launch {
            try {
                if (!sessionExists()) {

                    val response = account.createSession(emailAddress, password)
                    sharedPreferences.edit().putString(TreeSpotActivity.PREF_ACTIVE_SESSION_ID, response.id).apply()
                    sharedPreferences.edit().putString(TreeSpotActivity.PREF_ACTIVE_USERNAME_ID, response.userId).apply()

                    createUserInDB(account.get())
                }
            } catch (e: AppwriteException) {
                e.printStackTrace()
            }
        }
    }

    private fun createUserInDB(get: User) {
        val convert = net.n4dev.treespot.db.entity.User.convertFromAWUser(get)
    }

    fun sessionExists() : Boolean {
        return false
    }



}