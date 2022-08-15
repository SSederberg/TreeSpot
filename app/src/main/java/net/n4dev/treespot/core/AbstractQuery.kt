package net.n4dev.treespot.core

import io.objectbox.Box
import io.objectbox.Property
import io.objectbox.query.QueryCondition
import net.n4dev.treespot.core.api.IEntity
import net.n4dev.treespot.core.api.IQuery
import net.n4dev.treespot.db.TreeSpotObjectBox.getBox

/**
 * Base class for querying data based on an IEntity.
 */
abstract class AbstractQuery<T : IEntity>(klass: Class<T>) : IQuery<T> {

    private val paramBox: Box<T> = getBox(klass)
    abstract fun buildConditions(fields: Array<Property<T>>): QueryCondition<T>
    override fun buildQuery(): QueryCondition<T> {
        return buildConditions(paramBox.entityInfo.allProperties)
    }

}