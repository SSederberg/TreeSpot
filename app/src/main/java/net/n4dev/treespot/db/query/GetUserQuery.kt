package net.n4dev.treespot.db.query

import com.couchbase.lite.*
import net.n4dev.treespot.db.TreeSpotDatabases
import net.n4dev.treespot.db.TreeSpotUserDB

class GetUserQuery {


    companion object {
        private val db = TreeSpotDatabases()
        fun get(username : String) : Where {
            return QueryBuilder.select(SelectResult.all())
                .from(DataSource.database(db.userDB))
                .where(
                    Expression.property(TreeSpotUserDB.USERNAME)
                    .equalTo(Expression.property(username)))
        }
    }
}