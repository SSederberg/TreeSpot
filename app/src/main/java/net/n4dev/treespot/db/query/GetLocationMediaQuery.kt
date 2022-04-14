package net.n4dev.treespot.db.query

import io.objectbox.query.Query
import net.n4dev.treespot.core.entity.TreeSpotMedia
import net.n4dev.treespot.core.entity.TreeSpotMedia_
import net.n4dev.treespot.db.TreeSpotObjectBox

class GetLocationMediaQuery {

    companion object {
        private val queryBox = TreeSpotObjectBox.getBoxStore().boxFor(TreeSpotMedia::class.java)

        fun get(spotID : String) : Query<TreeSpotMedia> {
            return queryBox.query(TreeSpotMedia_.spotID.equal(spotID)).build()
        }
    }
}