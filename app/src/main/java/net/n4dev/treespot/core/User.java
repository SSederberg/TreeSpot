package net.n4dev.treespot.core;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import net.n4dev.treespot.core.api.ITreeSpot;
import net.n4dev.treespot.core.api.IUser;

import java.util.List;
import java.util.UUID;

@Entity(tableName = "treespot_user")
public class User implements IUser {

    public User() {
    }

    public User(String username, String emailAddress, UUID uuid) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.userID = uuid.toString();
    }

    public static final  String name     = "treespot_user";
    public static final String UID           = "local_uid";
    public static final String USERNAME      = "user_username";
    public static final String EMAIL_ADDRESS = "user_email_address";
    public static final  String USER_CREATION_DATE = "user_creation_date";
    public static final  String USER_ID            = "user_id";

    @PrimaryKey(autoGenerate = true)
    private Long localUID;
    private String userID;
    private String username;
    private String emailAddress;
    private Long accountCreationDate;

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
    public long getLocalUID() {
        return localUID;
    }

    @Override
    public void setLocalUID(long l) {
        this.localUID = l;
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
    public List<IUser> getUserFriends() {
        return null;
    }

    @Override
    public void addFriend(@NonNull UUID friendUUID) {

    }

    @Override
    public void removeFriend(@NonNull UUID friendUUID) {

    }

//    public String getUserID() {
//        return userID;
//    }
//
//    public void setUserID(String userID) {
//        this.userID = userID;
//    }
//
//    public Long getLocalUID() {
//        return localUID;
//    }
//
//    public void setLocalUID(Long localUID) {
//        this.localUID = localUID;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getEmailAddress() {
//        return emailAddress;
//    }
//
//    public void setEmailAddress(String emailAddress) {
//        this.emailAddress = emailAddress;
//    }
//
//    public long getAccountCreationDate() {
//        return accountCreationDate;
//    }
//
//    public void setAccountCreationDate(Long accountCreationDate) {
//        this.accountCreationDate = accountCreationDate;
//    }

}