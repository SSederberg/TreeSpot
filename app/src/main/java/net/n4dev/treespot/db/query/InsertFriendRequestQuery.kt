package net.n4dev.treespot.db.query

import com.couchbase.lite.Database
import com.couchbase.lite.MutableDocument
import net.n4dev.treespot.db.TreeSpotFriendRequestsDatabase

class InsertFriendRequestQuery {

    companion object {

        fun insert(data: Map<String, Any>, database: Database) {
            val requestDoc = MutableDocument()

            requestDoc.setString(TreeSpotFriendRequestsDatabase.CALLED_BY_ID, data[TreeSpotFriendRequestsDatabase.CALLED_BY_ID] as String)
            requestDoc.setString(TreeSpotFriendRequestsDatabase.FRIEND_ID, data[TreeSpotFriendRequestsDatabase.FRIEND_ID] as String)
            requestDoc.setLong(TreeSpotFriendRequestsDatabase.REQUEST_DATE, data[TreeSpotFriendRequestsDatabase.REQUEST_DATE] as Long)
            requestDoc.setBoolean(TreeSpotFriendRequestsDatabase.WAS_ACCEPTED, data[TreeSpotFriendRequestsDatabase.WAS_ACCEPTED] as Boolean)

            database.save(requestDoc)
        }
    }
}