 package net.n4dev.treespot.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import io.appwrite.Client
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account
import kotlinx.coroutines.launch
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.AbstractViewModel

class ResetPasswordViewModel : AbstractViewModel() {

    private lateinit var client: Client
    private lateinit var account: Account

    override fun init(context: Context) {
        client = TreeSpotApplication.getClient(context)
        account = Account(client)
    }

    fun startPasswordRecovery(userEmail : String) {
        viewModelScope.launch {
           try {
               val passwordResponse = account.createRecovery(userEmail, "http://redirect.n4dev.net")
           }catch (ex : AppwriteException) {
               ex.printStackTrace()
           }
        }
    }

    fun confirmPasswordRecovery(userID : String, recoverySecret : String, password : String, confirmPassword : String) {

        if(recoverySecret.isNotEmpty()) {
            viewModelScope.launch {
                val confirmResponse = account.updateRecovery(userID,
                    recoverySecret,
                password,
                confirmPassword)
            }
        } else {
            Logger.e("Failed to send password recovery request! The provided secret was empty!")
        }
    }

}