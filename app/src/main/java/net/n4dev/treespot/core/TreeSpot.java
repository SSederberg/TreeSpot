package net.n4dev.treespot.core;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

import net.n4dev.treespot.core.api.ITreeSpot;

@Fts4
@Entity(tableName = "treespot_spot")
public class TreeSpot implements ITreeSpot {

    public TreeSpot() {
    }

    private static final String LOCAL_UID          = "rowid";
    private static final String SPOT_OWNER         = "spot_owner_uuid";
    private static final String SPOT_LAT_NORTH     = "spot_lat_north";
    private static final String SPOT_LONG_WEST     = "spot_long_west";
    private static final String SPOT_CREATION_DATE = "spot_creation_date";
    private static final String SPOT_UUID          = "spot_uuid";
    private static final String SPOT_DESCRIPTION   = "spot_description";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = LOCAL_UID) private Integer localUID;

    private String latNorth;
    private String longWest;
    private Long   creationDate;
    private String spotID;
    private String description;
    private String spotOwnerID;

    public Integer getLocalUID() {
        return localUID;
    }

    public void setLocalUID(Integer localUID) {
        this.localUID = localUID;
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
}
