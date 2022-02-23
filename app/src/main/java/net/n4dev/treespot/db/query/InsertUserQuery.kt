package net.n4dev.treespot.db.query

import com.couchbase.lite.Database
import com.couchbase.lite.MutableDocument
import net.n4dev.treespot.core.User
import net.n4dev.treespot.db.TreeSpotUserDB

class InsertUserQuery {

    companion object {

        fun insert(user: User, database: Database) {
            val newUserDoc = MutableDocument()

            newUserDoc.setString(TreeSpotUserDB.USERNAME, user.getUsername())
            newUserDoc.setString(TreeSpotUserDB.EMAIL_ADDRESS, user.getEmailAddress())
            newUserDoc.setLong(TreeSpotUserDB.USER_CREATION_DATE, user.getAccountCreationDate())
            newUserDoc.setString(TreeSpotUserDB.USER_ID, user.getUserID())

            database.save(newUserDoc)
        }
    }
}