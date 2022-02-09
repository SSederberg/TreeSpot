package net.n4dev.treespot.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.appwrite.Client
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account
import kotlinx.coroutines.launch
import net.n4dev.treespot.TreeSpotApplication

class UserLoginViewModel : ViewModel() {

    private lateinit var client: Client
    private lateinit var account: Account

    fun init(context: Context) {
        client = TreeSpotApplication.getClient(context)
        account = Account(client)
    }

    fun attemptLogin(emailAddress : String, password : String) {
        try {
            if(!sessionExists()) {
                viewModelScope.launch {
                    val response = account.createSession(emailAddress, password)
                }
            }
        }catch (e : AppwriteException) {
            e.printStackTrace()
        }
    }

    fun sessionExists() : Boolean {
        return false
    }


}