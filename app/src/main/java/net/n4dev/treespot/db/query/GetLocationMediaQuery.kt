package net.n4dev.treespot.db.query

import io.objectbox.Property
import io.objectbox.query.QueryCondition
import net.n4dev.treespot.core.AbstractQuery
import net.n4dev.treespot.core.entity.TreeSpotMedia
import net.n4dev.treespot.db.constants.TreeSpotMediaConstants

class GetLocationMediaQuery(private val spotID : String) : AbstractQuery<TreeSpotMedia>(TreeSpotMedia::class.java) {

    //    companion object {
//        private val queryBox = TreeSpotObjectBox.getBoxStore().boxFor(TreeSpotMedia::class.java)
//
//        fun get(spotID : String) : Query<TreeSpotMedia> {
//            return queryBox.query(TreeSpotMedia_.spotID.equal(spotID)).build()
//        }
//    }
    override fun buildConditions(fields: Array<out Property<TreeSpotMedia>>): QueryCondition<TreeSpotMedia> {
        val field = TreeSpotMedia.fieldConverter[TreeSpotMediaConstants.SPOT_ID] as Int
        return fields[field].equal(spotID)
    }
}