package net.n4dev.treespot.db.query

import io.objectbox.query.Query
import net.n4dev.treespot.core.api.IQuery
import net.n4dev.treespot.core.entity.TreeSpot
import net.n4dev.treespot.core.entity.TreeSpot_
import net.n4dev.treespot.db.TreeSpotObjectBox

class GetUserTreeSpotsQuery : IQuery {

   companion object {
       private val queryBox = TreeSpotObjectBox.getBoxStore().boxFor(TreeSpot::class.java)
       fun get(ownerID : String) : Query<TreeSpot>? {
           return queryBox.query(TreeSpot_.spotOwnerID.equal(ownerID)).build()
       }
   }

}