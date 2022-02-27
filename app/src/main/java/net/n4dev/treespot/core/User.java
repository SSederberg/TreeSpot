package net.n4dev.treespot.core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.n4dev.treespot.core.api.IFriend;
import net.n4dev.treespot.core.api.ITreeSpot;
import net.n4dev.treespot.core.api.IUser;

import java.util.List;
import java.util.UUID;

import io.appwrite.models.Jwt;

public class User implements IUser {

    public User() {
        //Required to compile
    }

    public User(String username, String emailAddress, UUID uuid) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.userID = uuid.toString();
    }

    private String userID;
    private String username;
    private String emailAddress;
    private Long accountCreationDate;
    @Nullable private String currentSessionID;

    @NonNull
    @Override
    public String getType() {
        return TypeConst.Companion.getUSER();
    }

    @NonNull
    @Override
    public String getEntityID() {
        return null;
    }

    @Override
    public boolean isUser() {
        return true;
    }

    @Override
    public boolean isTreeSpot() {
        return false;
    }

    @NonNull
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    @Override
    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public void setEmailAddress(@NonNull String email) {
        this.emailAddress = email;
    }

    @NonNull
    @Override
    public String getUserID() {
        return userID;
    }

    @Override
    public void setUserID(@NonNull String userID) {
        this.userID = userID;
    }

    @Override
    public long getAccountCreationDate() {
        return accountCreationDate;
    }

    @Override
    public void setAccountCreationDate(long date) {
        this.accountCreationDate = date;
    }

    @NonNull
    @Override
    public List<ITreeSpot> getUserSpots() {
        return null;
    }

    @Override
    public void assignSpot(@NonNull ITreeSpot treespot) {

    }

    @Override
    public void removeSpot(@NonNull ITreeSpot treespot) {

    }

    @NonNull
    @Override
    public List<IFriend> getUserFriends() {
        return null;
    }

    @Override
    public void addFriend(@NonNull UUID friendUUID) {

    }

    @Override
    public void removeFriend(@NonNull UUID friendUUID) {

    }

    @Override
    public String getCurrentSessionID() {
        return currentSessionID;
    }

    @Override
    public void setCurrentSessionID(@NonNull String string) {
        this.currentSessionID = string;
    }

    public static User convertFromAWUser(io.appwrite.models.User user) {
        User returnedUser = new User(user.getName(), user.getEmail(), UUID.fromString(user.getId()));
        returnedUser.setAccountCreationDate(user.getRegistration());
        return returnedUser;
    }
}
