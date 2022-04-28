package net.n4dev.treespot.db.query

import io.objectbox.Property
import io.objectbox.query.QueryCondition
import net.n4dev.treespot.core.AbstractQuery
import net.n4dev.treespot.core.entity.TreeSpotMedia

class GetMediaByIDQuery(val mediaID : String) : AbstractQuery<TreeSpotMedia>(TreeSpotMedia::class.java) {
    override fun buildConditions(fields: Array<Property<TreeSpotMedia>>): QueryCondition<TreeSpotMedia> {
//        val field = TreeSpotMedia.fieldConverter.get("mediaID") as Int

        return fields[7].equal(mediaID)
    }
}