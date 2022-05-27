package net.n4dev.treespot.viewmodel

import android.content.Context
import io.appwrite.Client
import io.appwrite.services.Avatars
import net.n4dev.treespot.core.AppwriteViewModel

class FriendDetailViewModel : AppwriteViewModel() {

    private lateinit var client : Client
    private lateinit var avatars: Avatars

    override fun init(context: Context) {
        avatars = super.getAppWriteAvatars(context)
    }
}