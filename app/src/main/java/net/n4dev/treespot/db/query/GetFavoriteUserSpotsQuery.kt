package net.n4dev.treespot.db.query

import io.objectbox.Property
import io.objectbox.query.QueryCondition
import net.n4dev.treespot.core.AbstractQuery
import net.n4dev.treespot.core.entity.FavoriteSpot
import net.n4dev.treespot.db.constants.TreeSpotFavoriteConstants

class GetFavoriteUserSpotsQuery(private val userID : String) : AbstractQuery<FavoriteSpot>(FavoriteSpot::class.java) {

    override fun buildConditions(fields: Array<Property<FavoriteSpot>>): QueryCondition<FavoriteSpot> {
        val field = FavoriteSpot.fieldConverter.get(TreeSpotFavoriteConstants.SPOT_FAV_USER_ID) as Int
        return fields[field].equal(userID)
    }
}