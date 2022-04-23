package net.n4dev.treespot.db.query

import io.objectbox.Property
import io.objectbox.query.QueryCondition
import net.n4dev.treespot.core.AbstractQuery
import net.n4dev.treespot.core.entity.User
import net.n4dev.treespot.db.constants.TreeSpotUserConstants

class GetLocalUsersQuery : AbstractQuery<User>(User::class.java) {


    override fun buildConditions(fields: Array<Property<User>>): QueryCondition<User> {
        val field = User.fieldConverter.get(TreeSpotUserConstants.EMAIL_ADDRESS) as Int
        return fields[field].notNull()
    }


}