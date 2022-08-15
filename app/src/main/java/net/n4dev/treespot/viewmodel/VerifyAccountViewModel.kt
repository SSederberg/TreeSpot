package net.n4dev.treespot.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import io.appwrite.Client
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account
import io.appwrite.services.Databases
import kotlinx.coroutines.launch
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.AppwriteViewModel

class VerifyAccountViewModel : AppwriteViewModel() {

    private lateinit var client: Client
    private lateinit var account: Account
    private lateinit var awDatabase : Databases

    private val usersCollectionID = "treespot-users"

    override fun init(context: Context) {
       client = TreeSpotApplication.getClient(context)
        account = Account(client)
        awDatabase = super.getAppWriteDatabase(context, usersCollectionID)
    }


    fun createVerification() {
        viewModelScope.launch {

           try {
               val verifyResponse = account.createVerification("http://192.168.1.221")
//               val sessionResponse = account.createSession(emailAddress, password)
//               val dbResponse = awDatabase.createDocument(usersCollectionID, account.get().id, awData, arrayListOf("role:member"))
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