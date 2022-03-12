package net.n4dev.treespot.db.query

import io.objectbox.query.Query
import net.n4dev.treespot.db.TreeSpotObjectBox
import net.n4dev.treespot.db.entity.TreeSpot
import net.n4dev.treespot.db.entity.TreeSpot_

class GetUserTreeSpotsQuery {

    private val queryBox = TreeSpotObjectBox.getBoxStore().boxFor(TreeSpot::class.java)
    fun get(ownerID : String) : Query<TreeSpot> {
        return queryBox.query(TreeSpot_.spotOwnerID.equal(ownerID)).build()
    }

}