package net.n4dev.treespot.db.query

import com.couchbase.lite.*
import net.n4dev.treespot.db.TreeSpotUserDB

class searchUsersQuery {

    companion object {
        fun get(db : Database, userID : String) : Result {
            val query = QueryBuilder.select(SelectResult.all())
                .from(DataSource.database(db))
                .where(Expression.property(TreeSpotUserDB.USER_ID)
                    .equalTo(Expression.property(userID)))

            val results = query.execute()
            return results.next()
        }
    }
}