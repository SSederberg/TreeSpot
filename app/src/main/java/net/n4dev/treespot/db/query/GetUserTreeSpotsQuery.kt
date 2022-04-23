package net.n4dev.treespot.db.query

import io.objectbox.Property
import io.objectbox.query.QueryCondition
import net.n4dev.treespot.core.AbstractQuery
import net.n4dev.treespot.core.entity.TreeSpot
import net.n4dev.treespot.db.constants.TreeSpotsConstants

class GetUserTreeSpotsQuery(private val ownerID : String) : AbstractQuery<TreeSpot>(TreeSpot::class.java) {

    override fun buildConditions(fields: Array<Property<TreeSpot>>): QueryCondition<TreeSpot> {
        val field = TreeSpot.fieldConverter[TreeSpotsConstants.SPOT_OWNER_ID] as Int
        return fields[field].equal(ownerID)
    }

}