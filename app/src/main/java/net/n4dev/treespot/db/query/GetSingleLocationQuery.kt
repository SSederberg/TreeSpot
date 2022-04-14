package net.n4dev.treespot.db.query

import io.objectbox.query.Query
import net.n4dev.treespot.core.entity.TreeSpot
import net.n4dev.treespot.core.entity.TreeSpot_
import net.n4dev.treespot.db.TreeSpotObjectBox

class GetSingleLocationQuery {

    companion object {
        private val queryBox = TreeSpotObjectBox.getBoxStore().boxFor(TreeSpot::class.java)
        fun get(spotID : String) : Query<TreeSpot> {
            return queryBox.query(TreeSpot_.spotID.equal(spotID)).build()
        }
    }
}