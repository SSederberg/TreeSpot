package net.n4dev.treespot.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.appwrite.Client
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account
import kotlinx.coroutines.launch
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.api.IViewModel

class UserLoginViewModel : ViewModel(), IViewModel {

    private lateinit var client: Client
    private lateinit var account: Account

   override fun init(context: Context) {
        client = TreeSpotApplication.getClient(context)
        account = Account(client)
    }

    fun attemptLogin(emailAddress : String, password : String) {
        viewModelScope.launch {
            try {
                if (!sessionExists()) {

                    val response = account.createSession(emailAddress, password)
                    var append = ""
                    response.toMap().forEach {
                        append += ("[" + it.key + "," + it.value + "]")
                    }
                }
            } catch (e: AppwriteException) {
                e.printStackTrace()
            }
        }
    }

    fun sessionExists() : Boolean {
        return false
    }



}