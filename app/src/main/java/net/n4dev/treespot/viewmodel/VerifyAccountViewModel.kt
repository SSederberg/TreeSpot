package net.n4dev.treespot.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import io.appwrite.Client
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account
import kotlinx.coroutines.launch
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.api.IViewModel

class VerifyAccountViewModel : ViewModel(), IViewModel {

    private lateinit var client: Client
    private lateinit var account: Account

    override fun init(context: Context) {
       client = TreeSpotApplication.getClient(context)
        account = Account(client)
    }


    fun createVerification() {
        viewModelScope.launch {
           try {
               val verifyResponse = account.createVerification("verified.n4dev.net")
               Logger.json(verifyResponse.toString())
           }catch (ex : AppwriteException) {
               ex.printStackTrace()
           }
        }
    }

    fun verifyAccount(userID : String, token : String) {
        viewModelScope.launch {
            try {
                val verifyResponse = account.updateVerification(userID, token)
                Logger.json(verifyResponse.toString())
            }catch (ex: AppwriteException) {

            }
        }
    }
}