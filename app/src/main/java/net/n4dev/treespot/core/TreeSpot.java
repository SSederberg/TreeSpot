package net.n4dev.treespot.core;

import androidx.annotation.NonNull;

import net.n4dev.treespot.core.api.ITreeSpot;

public class TreeSpot implements ITreeSpot {

    public TreeSpot() { }

    private String latNorth;
    private String longWest;
    private Long   creationDate;
    private String spotID;
    private String description;
    private String privateDescription;
    private String spotOwnerID;

    @Override
    public String getLatNorth() {
        return latNorth;
    }

    @Override
    public void setLatNorth(String latNorth) {
        this.latNorth = latNorth;
    }

    @Override
    public String getLongWest() {
        return longWest;
    }

    @Override
    public void setLongWest(String longWest) {
        this.longWest = longWest;
    }

    @Override
    public long getCreationDate() {
        return creationDate;
    }

    @Override
    public String getSpotID() {
        return spotID;
    }

    @Override
    public void setSpotID(String spotUUID) {
        this.spotID = spotUUID;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getSpotOwnerID() {
        return spotOwnerID;
    }

    @Override
    public void setSpotOwnerID(String spotOwnerUUID) {
        this.spotOwnerID = spotOwnerUUID;
    }

    @NonNull
    @Override
    public String getType() {
        return TypeConst.Companion.getTREESPOT();
    }

    @NonNull
    @Override
    public String getEntityID() {
        return this.spotID;
    }

    @Override
    public boolean isUser() {
        return false;
    }

    @Override
    public boolean isTreeSpot() {
        return true;
    }

    @Override
    public void setCreationDate(long date) {
        this.creationDate = date;
    }

    @NonNull
    @Override
    public String getPrivateDescription() {
        return privateDescription;
    }

    @Override
    public void setPrivateDescription(@NonNull String string) {
        this.privateDescription = string;
    }
}
