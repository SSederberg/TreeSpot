package net.n4dev.treespot.core;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

@Fts4
@Entity(tableName = "treespot_spot")
public class TreeSpot {

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
    private Long creationDate;
    private String spotUUID;
    private String description;
    private String spotOwnerUUID;

    public Integer getLocalUID() {
        return localUID;
    }

    public void setLocalUID(Integer localUID) {
        this.localUID = localUID;
    }

    public String getLatNorth() {
        return latNorth;
    }

    public void setLatNorth(String latNorth) {
        this.latNorth = latNorth;
    }

    public String getLongWest() {
        return longWest;
    }

    public void setLongWest(String longWest) {
        this.longWest = longWest;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

    public String getSpotUUID() {
        return spotUUID;
    }

    public void setSpotUUID(String spotUUID) {
        this.spotUUID = spotUUID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpotOwnerUUID() {
        return spotOwnerUUID;
    }

    public void setSpotOwnerUUID(String spotOwnerUUID) {
        this.spotOwnerUUID = spotOwnerUUID;
    }
}
