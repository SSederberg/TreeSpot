package net.n4dev.treespot.db.query

import io.objectbox.query.Query
import net.n4dev.treespot.core.entity.FavoriteSpot
import net.n4dev.treespot.core.entity.FavoriteSpot_
import net.n4dev.treespot.db.TreeSpotObjectBox

class GetFavoriteUserSpotsQuery {

    companion object {
        private val queyrBox = TreeSpotObjectBox.getBox(FavoriteSpot::class.java)

        fun get(userID : String) : Query<FavoriteSpot> {
            return queyrBox.query(FavoriteSpot_.favoriteUserID.equal(userID)).build()
        }
    }
}