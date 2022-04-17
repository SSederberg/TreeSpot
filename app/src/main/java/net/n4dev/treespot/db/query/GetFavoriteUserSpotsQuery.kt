package net.n4dev.treespot.db.query

import io.objectbox.Property
import io.objectbox.query.QueryCondition
import net.n4dev.treespot.core.AbstractQuery
import net.n4dev.treespot.core.entity.FavoriteSpot
import net.n4dev.treespot.db.constants.TreeSpotFavoriteConstants

class GetFavoriteUserSpotsQuery(private val userID : String) : AbstractQuery<FavoriteSpot>(FavoriteSpot::class.java) {

    //    companion object {
//        private val queyrBox = TreeSpotObjectBox.getBox(FavoriteSpot::class.java)
//
//        fun get(userID : String) : Query<FavoriteSpot> {
//            return queyrBox.query(FavoriteSpot_.favoriteUserID.equal(userID)).build()
//        }
//    }
    override fun buildConditions(fields: Array<out Property<FavoriteSpot>>): QueryCondition<FavoriteSpot> {
        val field = FavoriteSpot.fieldConverter.get(TreeSpotFavoriteConstants.SPOT_FAV_USER_ID) as Int
        return fields[field].equal(userID)
    }
}