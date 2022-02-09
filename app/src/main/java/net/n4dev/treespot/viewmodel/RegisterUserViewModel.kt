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
import java.util.*

class RegisterUserViewModel : ViewModel(), IViewModel {

    private lateinit var client: Client
    private lateinit var account: Account


    override fun init(context: Context) {
        client = TreeSpotApplication.getClient(context)
        account = Account(client)
    }

    fun registerAccount(emailAddress : String, password : String, username : String, userID: UUID) {
        viewModelScope.launch {
          try {
              val user = account.create(userID.toString(), emailAddress, password, username)
          }catch (e : AppwriteException) {
              Logger.e(e, "")
          }
        }
    }
}