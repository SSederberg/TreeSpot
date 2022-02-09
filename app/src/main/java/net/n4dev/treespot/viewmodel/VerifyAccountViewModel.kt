package net.n4dev.treespot.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import io.appwrite.Client
import io.appwrite.services.Account
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.api.IViewModel

class VerifyAccountViewModel : ViewModel(), IViewModel {

    private lateinit var client: Client
    private lateinit var account: Account

    override fun init(context: Context) {
       client = TreeSpotApplication.getClient(context)
        account = Account(client)
    }


    fun verifyEmailAddress() {

    }
}