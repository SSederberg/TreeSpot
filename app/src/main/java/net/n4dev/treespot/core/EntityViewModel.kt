package net.n4dev.treespot.core

abstract class EntityViewModel<T> : AbstractViewModel() {

    /**
     * Take variables from T object and copy them locally to this viewmodel.
     */
    abstract fun copyFrom(entity : T)

    /**
     * Take variables from this viewmodel and copy them to entity
     */
    abstract fun copyTo(entity: T)


}