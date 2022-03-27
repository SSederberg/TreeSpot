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

class UserAuthorizedViewModel : AbstractViewModel() {

    private lateinit var client: Client
    private lateinit var account: Account

    override fun init(context: Context) {
        client = TreeSpotApplication.getClient(context)
        account = Account(client)
    }

    fun isAuthorized(sessionID : String) : Boolean {
        var isAuthorized = false
        viewModelScope.launch {
            try {
                var sessionResponse = account.getSession(sessionID)

                //Check to make sure stored session has not already expired.
                if(sessionResponse.expire >= System.currentTimeMillis()) {
                    Logger.i("Greater or Equal To");
                } else {
                    Logger.i("Else!")
                    sessionResponse = account.getSession("current")

                    if(sessionResponse.expire >= System.currentTimeMillis()) {
                        Logger.i("Found current session!")
                        isAuthorized = true
                    } else {
                        isAuthorized = false
                    }
                }

            }catch (ex : AppwriteException) {
                ex.printStackTrace()
                isAuthorized = false
            }
        }

        return isAuthorized
    }
}