package net.n4dev.treespot.core

import net.n4dev.treespot.core.api.IEntity

abstract class EntityViewModel<T : IEntity> : AbstractViewModel() {

    /**
     * Take variables from T object and copy them locally to this viewmodel.
     */
    abstract fun copyFrom(entity : T)

    /**
     * Take variables from this viewmodel and copy them to entity
     */
    abstract fun copyTo(entity: T)


}