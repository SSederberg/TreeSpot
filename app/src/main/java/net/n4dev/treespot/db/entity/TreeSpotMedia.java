package net.n4dev.treespot.db.entity;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import net.n4dev.treespot.core.api.ITreeSpotMedia;
import net.n4dev.treespot.db.constants.TreeSpotMediaConstants;

import java.util.UUID;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;
import io.objectbox.tree.Tree;

@Entity
public class TreeSpotMedia implements ITreeSpotMedia {

    @Id public Long localID;

    @NameInDb(TreeSpotMediaConstants.USER_ID)
    private String userID;

    @NameInDb(TreeSpotMediaConstants.SPOT_ID)
    private String spotID;

    private String typeConst;

    @NameInDb(TreeSpotMediaConstants.DEVICE_PATH)
    private String mediaPath;

    @NameInDb(TreeSpotMediaConstants.FILENAME)
    private String fileName;

    private boolean isUploaded = false;

    @NameInDb(TreeSpotMediaConstants.TAKEN_AT)
    private Long dateCreated;

    @NameInDb(TreeSpotMediaConstants.MEDIA_ID)
    private String mediaID;

    public TreeSpotMedia() { }

    public TreeSpotMedia(String userID, String spotID, String typeConst, String mediaPath, String filename) {
        this.userID = userID;
        this.spotID = spotID;
        this.typeConst = typeConst;
        this.mediaPath = mediaPath;
        this.fileName = filename;
        dateCreated = System.currentTimeMillis();
        mediaID = UUID.randomUUID().toString();
    }

    @NonNull
    @Override
    public String getType() {
        return TypeConst.Companion.getMEDIA();
    }

    @NonNull
    @Override
    public String getEntityID() {
        return mediaID;
    }

    @Override
    public boolean isUser() {
        return false;
    }

    @Override
    public boolean isTreeSpot() {
        return false;
    }

    @Override
    public boolean isVideo() {
        return typeConst.equals(TypeConst.Companion.getVIDEO());
    }

    @Override
    public boolean isPicture() {
        return typeConst.equals(TypeConst.Companion.getPICTURE());
    }

    @Override
    public boolean isUploaded() {
        return isUploaded;
    }

    @NonNull
    @Override
    public String getSpotID() {
        return spotID;
    }

    @NonNull
    @Override
    public String getUserID() {
        return userID;
    }

    @NonNull
    @Override
    public String getMediaPath() {
        return mediaPath;
    }

    @NonNull
    @Override
    public String getMediaFileName() {
        return fileName;
    }

    @Override
    public long getMediaCreationDate() {
        return dateCreated;
    }

    @NonNull
    @Override
    public String getMediaID() {
        return mediaID;
    }

    public String getTypeConst() {
        return typeConst;
    }

    public String getFileName() {
        return fileName;
    }

    public Long getDateCreated() {
        return dateCreated;
    }

    @Override
    public String toString() {
        return "TreeSpotMedia{" +
                "localID=" + localID +
                ", userID='" + userID + '\'' +
                ", spotID='" + spotID + '\'' +
                ", typeConst='" + typeConst + '\'' +
                ", mediaPath='" + mediaPath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", isUploaded=" + isUploaded +
                ", dateCreated=" + dateCreated +
                ", mediaID='" + mediaID + '\'' +
                '}';
    }

    @NonNull
    @Override
    public Bitmap getImageAsBitMap() {
        return null;
    }
}
