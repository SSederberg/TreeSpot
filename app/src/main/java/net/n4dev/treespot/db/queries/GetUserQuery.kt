package net.n4dev.treespot.db.queries

import io.zeko.db.sql.Query
import io.zeko.db.sql.dsl.eq
import net.n4dev.treespot.core.User

class GetUserQuery {

    companion object {
        fun get(username : String): Query {
            return Query().fields("*").from(User.name).where(User.USERNAME eq username)
        }
    }


}