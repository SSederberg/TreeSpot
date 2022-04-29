package net.n4dev.treespot.db

import android.content.Context
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import net.n4dev.treespot.core.api.IEntity
import net.n4dev.treespot.core.entity.MyObjectBox

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
        store.removeAllObjects()
    }

    fun <T : IEntity> getBox(klass : Class<T>): Box<T> {
        return store.boxFor(klass)
    }
}