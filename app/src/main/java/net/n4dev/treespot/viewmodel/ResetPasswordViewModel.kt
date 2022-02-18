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

class ResetPasswordViewModel : ViewModel(), IViewModel {

    private lateinit var client: Client
    private lateinit var account: Account

    override fun init(context: Context) {
        client = TreeSpotApplication.getClient(context)
        account = Account(client)
    }

    fun updatePassword(oldPassword : String, newPassword : String) {
        viewModelScope.launch {
           try {
               val passwordResponse = account.updatePassword(newPassword, oldPassword)
           }catch (ex : AppwriteException) {
               ex.printStackTrace()
           }
        }
    }
}