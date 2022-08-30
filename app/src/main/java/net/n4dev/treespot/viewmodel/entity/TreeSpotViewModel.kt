package net.n4dev.treespot.viewmodel.entity

import androidx.lifecycle.MutableLiveData
import net.n4dev.treespot.core.EntityViewModel
import net.n4dev.treespot.core.api.ITreeSpot
import net.n4dev.treespot.core.api.ITreeSpotMedia
import net.n4dev.treespot.core.entity.TreeSpot
import net.n4dev.treespot.core.entity.TypeConst

class TreeSpotViewModel() : EntityViewModel<TreeSpot>(), ITreeSpot {

    private var spotID = MutableLiveData<String>()
    private var spotOwnerID = MutableLiveData<String>()
    private var creationDate = MutableLiveData<Long>()
    private var latNorth = MutableLiveData<String>()
    private var longWest = MutableLiveData<String>()
    private var description = MutableLiveData<String>()
    private var privateDescription = MutableLiveData<String>()

    constructor(treeSpot: TreeSpot) : this() {
        spotID.value = treeSpot.getSpotID()
        spotOwnerID.value = treeSpot.getSpotOwnerID()
        creationDate.value = treeSpot.getCreationDate()
        latNorth.value = treeSpot.getLatNorth()
        longWest.value = treeSpot.getLongWest()
        description.value = treeSpot.getDescription()
        privateDescription.value = treeSpot.getPrivateDescription()
    }

    override fun copyFrom(entity: TreeSpot) {
        TODO("Not yet implemented")
    }

    override fun copyTo(entity: TreeSpot) {
        TODO("Not yet implemented")
    }

    override fun getSpotID(): String {
        return spotID.value!!
    }

    override fun setSpotID(uuid: String) {
        spotID.postValue(uuid)
    }

    override fun setSpotOwnerID(userID: String) {
        spotOwnerID.postValue(userID)
    }

    override fun getSpotOwnerID(): String {
        return spotOwnerID.value!!
    }

    override fun getCreationDate(): Long {
        return creationDate.value!!
    }

    override fun setCreationDate(date: Long) {
        creationDate.postValue(date)
    }

    override fun getLatNorth(): String {
        return latNorth.value!!
    }

    override fun setLatNorth(string: String) {
        latNorth.postValue(string)
    }

    override fun getLongWest(): String {
        return longWest.value!!
    }

    override fun setLongWest(string: String) {
        longWest.postValue(string)
    }

    override fun getDescription(): String {
       return description.value!!
    }

    override fun setDescription(description: String) {
        this.description.postValue(description)
    }

    override fun getPrivateDescription(): String? {
        return privateDescription.value
    }

    override fun setPrivateDescription(string: String) {
        privateDescription.postValue(string)
    }

    override fun getSpotPhotos(): List<ITreeSpotMedia> {
        TODO("Not yet implemented")
    }

    override fun getType(): String {
        return TypeConst.TREESPOT
    }

    override fun getEntityID(): String {
       return spotID.value!!
    }

    override fun isUser(): Boolean {
        return false
    }

    override fun isTreeSpot(): Boolean {
        return true
    }

}