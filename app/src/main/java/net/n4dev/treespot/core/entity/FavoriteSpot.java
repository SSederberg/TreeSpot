package net.n4dev.treespot.core.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.n4dev.treespot.core.api.IFavoriteTreeSpot;
import net.n4dev.treespot.db.UUIDConverter;
import net.n4dev.treespot.db.constants.TreeSpotFavoriteConstants;
import net.n4dev.treespot.db.constants.TreeSpotsConstants;

import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.NameInDb;
import io.objectbox.annotation.Unique;

@Entity
public class FavoriteSpot implements IFavoriteTreeSpot {

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

    @NameInDb(TreeSpotFavoriteConstants.SPOT_FAV_USER_ID)
//    @Convert(converter = UUIDConverter.class, dbType = String.class)
    private String favoriteUserID;

    @Index
    @NameInDb(TreeSpotFavoriteConstants.SPOT_FAV_DATE)
    private Long favoriteDate;

    public FavoriteSpot() { }

    public FavoriteSpot(String spotID, String ownerID, String favoriteUserID, Long favoriteDate) {
        this.spotID = spotID;
        this.spotOwnerID = ownerID;
        this.favoriteUserID = favoriteUserID;
        this.favoriteDate = favoriteDate;
    }

    @NonNull
    @Override
    public String getType() {
        return TypeConst.Companion.getTREESPOT();
    }

    @NonNull
    @Override
    public String getEntityID() {
        return null;
    }

    @Override
    public boolean isUser() {
        return false;
    }

    @Override
    public boolean isTreeSpot() {
        return true;
    }

    @NonNull
    @Override
    public String getSpotID() {
        return spotID;
    }

    @Override
    public void setSpotID(@NonNull String uuid) {
        this.spotID = uuid;
    }

    @Override
    public void setSpotOwnerID(@NonNull String userID) {
        this.spotOwnerID = userID;
    }

    @NonNull
    @Override
    public String getSpotOwnerID() {
        return spotOwnerID;
    }

    @NonNull
    @Override
    public String getFavoriteUserID() {
        return favoriteUserID;
    }

    @Override
    public void setFavoriteUserID(@NonNull String userID) {
        this.favoriteUserID = userID;
    }

    @Override
    public long getFavoriteDate() {
        return favoriteDate;
    }

    @Override
    public void setFavoriteDate(long dateLong) {
        this.favoriteDate = dateLong;
    }

    @Override
    public String toString() {
        return "FavoriteSpot{" +
                ", spotID='" + spotID + '\'' +
                ", ownerID='" + spotOwnerID + '\'' +
                ", favoriteUserID='" + favoriteUserID + '\'' +
                ", favoriteDate=" + favoriteDate +
                '}';
    }

    @Override
    public long getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(long date) {
        this.creationDate = date;
    }

    @NonNull
    @Override
    public String getLatNorth() {
        return latNorth;
    }

    @Override
    public void setLatNorth(@NonNull String string) {
        this.latNorth = string;
    }

    @NonNull
    @Override
    public String getLongWest() {
        return longWest;
    }

    @Override
    public void setLongWest(@NonNull String string) {
        this.longWest = string;
    }

    @NonNull
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(@NonNull String description) {
        this.description = description;
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
