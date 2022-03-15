package net.n4dev.treespot.db

import android.content.Context
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import net.n4dev.treespot.db.entity.Friend
import net.n4dev.treespot.db.entity.MyObjectBox
import net.n4dev.treespot.db.entity.TreeSpot

object TreeSpotObjectBox {
    private lateinit var store: BoxStore

    fun init(context : Context) {

        store = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()

        var started = AndroidObjectBrowser(store).start(context)

    }

    fun getBoxStore() : BoxStore {
        return store
    }

    fun purgeStores() {
        store.boxFor(Friend::class.java).removeAll();
        store.boxFor(TreeSpot::class.java).removeAll()
    }
}