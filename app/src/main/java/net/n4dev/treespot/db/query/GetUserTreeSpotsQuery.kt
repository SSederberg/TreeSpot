package net.n4dev.treespot.db.query

import io.objectbox.Box
import io.objectbox.query.Query
import net.n4dev.treespot.core.AbstractQuery
import net.n4dev.treespot.db.entity.TreeSpot
import net.n4dev.treespot.db.entity.TreeSpot_

class GetUserTreeSpotsQuery(private val box: Box<TreeSpot>,
                            entityName: String,
                            private val ownerID : String) : AbstractQuery<TreeSpot>(box, System.currentTimeMillis(), entityName) {

    override fun buildQuery(): Query<TreeSpot> {
        return box.query().equal(TreeSpot_.spotOwnerID, ownerID, StringOrder.CASE_INSENSITIVE).build()
    }
}