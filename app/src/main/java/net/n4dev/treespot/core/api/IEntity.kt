package net.n4dev.treespot.core.api


interface IEntity {

    fun getType(): String

    fun getEntityID(): String
//    fun setEntityID(entityID : String)

    fun isUser(): Boolean

    fun isTreeSpot(): Boolean

}