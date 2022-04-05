package net.n4dev.treespot.db.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.n4dev.treespot.core.api.ITreeSpot;
import net.n4dev.treespot.db.constants.TreeSpotsConstants;

import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.NameInDb;
import io.objectbox.annotation.Unique;

@Entity
public class TreeSpot implements ITreeSpot {


    @Id
    public Long localID;

    @NameInDb(TreeSpotsConstants.SPOT_LAT_NORTH)
    private String latNorth;

    @NameInDb(TreeSpotsConstants.SPOT_LONG_WEST)
    private String longWest;

    @Index
    @NameInDb(TreeSpotsConstants.SPOT_CREATION_DATE)
    private Long  creationDate;

    @Unique(onConflict = ConflictStrategy.REPLACE)
    @NameInDb(TreeSpotsConstants.SPOT_UUID)
    private  String spotID;

    @NameInDb(TreeSpotsConstants.SPOT_DESCRIPTION)
    private String description;

    @Nullable
    @NameInDb(TreeSpotsConstants.SPOT_PRIVATE_DESCRIPTION)
    private String privateDescription;

    @NameInDb(TreeSpotsConstants.SPOT_OWNER_ID)
    private String spotOwnerID;

    @NameInDb(TreeSpotsConstants.SPOT_FAVORITE)
    private Boolean isFavorite;

    public TreeSpot() { }

    public TreeSpot(String latNorth, String longWest, Long creationDate, String spotID, String description, @Nullable String privateDescription, String spotOwnerID, Boolean isFavorite) {
        this.latNorth = latNorth;
        this.longWest = longWest;
        this.creationDate = creationDate;
        this.spotID = spotID;
        this.description = description;
        this.privateDescription = privateDescription;
        this.spotOwnerID = spotOwnerID;
        this.isFavorite = isFavorite;
    }

    public TreeSpot(String latNorth, String longWest, Long creationDate, String spotID, String description, String spotOwnerID, Boolean isFavorite) {
        this.latNorth = latNorth;
        this.longWest = longWest;
        this.creationDate = creationDate;
        this.spotID = spotID;
        this.description = description;
        this.spotOwnerID = spotOwnerID;
        this.isFavorite = isFavorite;
    }

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

    @Override
    public boolean getIsFavorite() {
        return isFavorite;
    }

    @Override
    public void setIsFavorite(boolean favorite) {
        this.isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "TreeSpot{" +
                "localID=" + localID +
                ", latNorth='" + latNorth + '\'' +
                ", longWest='" + longWest + '\'' +
                ", creationDate=" + creationDate +
                ", spotID='" + spotID + '\'' +
                ", description='" + description + '\'' +
                ", privateDescription='" + privateDescription + '\'' +
                ", spotOwnerID='" + spotOwnerID + '\'' +
                ", isFavorite=" + isFavorite +
                '}';
    }
}
