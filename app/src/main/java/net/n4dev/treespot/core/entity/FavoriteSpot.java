package net.n4dev.treespot.core.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.n4dev.treespot.core.api.IFavoriteTreeSpot;
import net.n4dev.treespot.core.api.ITreeSpotMedia;
import net.n4dev.treespot.db.UUIDConverter;
import net.n4dev.treespot.db.constants.TreeSpotFavoriteConstants;
import net.n4dev.treespot.db.constants.TreeSpotUserConstants;
import net.n4dev.treespot.db.constants.TreeSpotsConstants;

import java.util.HashMap;
import java.util.List;

import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.NameInDb;
import io.objectbox.annotation.Transient;
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

    @Transient
    public static HashMap<String, Integer> fieldConverter = initHashMap();

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

    private static HashMap<String, Integer> initHashMap() {
        HashMap<String, Integer> staticMap = new HashMap<>();
        staticMap.put("localID", 0);
        staticMap.put(TreeSpotsConstants.SPOT_LAT_NORTH, 1);
        staticMap.put(TreeSpotsConstants.SPOT_LONG_WEST, 2);
        staticMap.put(TreeSpotsConstants.SPOT_CREATION_DATE, 3);
        staticMap.put(TreeSpotsConstants.SPOT_UUID, 4);
        staticMap.put(TreeSpotsConstants.SPOT_DESCRIPTION, 5);
        staticMap.put(TreeSpotsConstants.SPOT_PRIVATE_DESCRIPTION, 6);
        staticMap.put(TreeSpotsConstants.SPOT_OWNER_ID, 7);
        staticMap.put(TreeSpotFavoriteConstants.SPOT_FAV_USER_ID, 8);
        staticMap.put(TreeSpotFavoriteConstants.SPOT_FAV_DATE, 9);
        return staticMap;
    }

    @NonNull
    @Override
    public List<ITreeSpotMedia> getSpotPhotos() {
        return null;
    }
}
