package net.n4dev.treespot.db.query

import io.objectbox.query.Query
import net.n4dev.treespot.core.api.IQuery
import net.n4dev.treespot.core.entity.FavoriteSpot
import net.n4dev.treespot.core.entity.FavoriteSpot_
import net.n4dev.treespot.db.TreeSpotObjectBox

class GetSingleFavoriteSpotQuery : IQuery {

    companion object {
        private val favoriteBox = TreeSpotObjectBox.getBox(FavoriteSpot::class.java)

        fun get(spotID : String) : Query<FavoriteSpot> {
            return favoriteBox.query(FavoriteSpot_.spotID.equal(spotID)).build()
        }
    }
}