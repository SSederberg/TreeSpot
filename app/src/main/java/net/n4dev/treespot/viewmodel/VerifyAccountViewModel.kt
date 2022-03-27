package net.n4dev.treespot.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import io.appwrite.Client
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account
import io.appwrite.services.Database
import kotlinx.coroutines.launch
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.AbstractViewModel

class VerifyAccountViewModel : AbstractViewModel() {

    private lateinit var client: Client
    private lateinit var account: Account
    private lateinit var awDatabase : Database

    private val usersCollectionID = "treespot-users"

    override fun init(context: Context) {
       client = TreeSpotApplication.getClient(context)
        account = Account(client)
        awDatabase = Database(client)
    }


    fun createVerification() {
        viewModelScope.launch {

           try {
               val verifyResponse = account.createVerification("verified.n4dev.net")
//               val sessionResponse = account.createSession(emailAddress, password)
//               val dbResponse = awDatabase.createDocument(usersCollectionID, account.get().id, awData, arrayListOf("role:member"))
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