package net.n4dev.treespot.db.query

import com.couchbase.lite.Database

class UpdateUserQuery {

    companion object {
        fun update(sessionID : String, userID : String, db : Database) {

//            val doc2 = searchUsersQuery.get(db, userID)
//            val doc = db.getDocument().toMutable()
//            doc.setString(TreeSpotUserDB.USER_ACTIVE_SESSION_ID, sessionID)
        }

        fun update() {
            TODO()
        }
    }
}