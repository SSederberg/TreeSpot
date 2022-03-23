package net.n4dev.treespot.db.entity;

import androidx.annotation.NonNull;

import net.n4dev.treespot.core.api.ITreeSpotMedia;

import io.objectbox.annotation.Entity;

@Entity
public class TreeSpotMedia implements ITreeSpotMedia {

    private final String userID;
    private final String spotID;
    private final String typeConst;
    private final String mediaPath;

    public TreeSpotMedia(String userID, String spotID, String typeConst, String mediaPath) {
        this.userID = userID;
        this.spotID = spotID;
        this.typeConst = typeConst;
        this.mediaPath = mediaPath;
    }

    @NonNull
    @Override
    public String getType() {
        return null;
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
        return false;
    }

    @NonNull
    @Override
    public String getSpotID() {
        return spotID;
    }

    @NonNull
    @Override
    public String getTakenByUserID() {
        return userID;
    }

    @NonNull
    @Override
    public String getMediaPath() {
        return mediaPath;
    }
}
