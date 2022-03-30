package net.n4dev.treespot.db.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.n4dev.treespot.core.api.IFriend;
import net.n4dev.treespot.db.constants.TreeSpotFriendsConstants;
import net.n4dev.treespot.db.UUIDConverter;
import net.n4dev.treespot.db.constants.TreeSpotUserConstants;

import java.util.List;
import java.util.UUID;

import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;
import io.objectbox.annotation.Unique;

@Entity
public class Friend implements IFriend {

    @Id public Long localID;

    /**
     * Unique ID for the friend object
     */
    @NameInDb(TreeSpotFriendsConstants.FRIEND_ID)
    @Convert(converter = UUIDConverter.class, dbType = String.class)
    private UUID friendID;

    /**
     * How long they have been friends
     */
    @NameInDb(TreeSpotFriendsConstants.FRIENDS_SINCE)
    private Long friendsSince;

    /**
     * User ID of the friend
     */
    @NameInDb(TreeSpotFriendsConstants.USER_ID)
    @Convert(converter = UUIDConverter.class, dbType = String.class)
    private UUID userID;

    /**
     * ID for the user they are partnered with
     */
    @NameInDb(TreeSpotFriendsConstants.FRIEND_PAIR_ID)
    @Convert(converter = UUIDConverter.class, dbType = String.class)
    private UUID friendPairID;

    @Unique(onConflict = ConflictStrategy.REPLACE)
    @NameInDb(TreeSpotUserConstants.USERNAME)
    private String username;

    @NameInDb(TreeSpotUserConstants.EMAIL_ADDRESS)
    private String emailAddress;

    @NameInDb(TreeSpotUserConstants.USER_CREATION_DATE)
    private Long accountCreationDate;

    @NameInDb(TreeSpotUserConstants.USER_ACTIVE_SESSION_ID)
    @Nullable private String currentSessionID;

    public Friend() {}

    public Friend(UUID friendID, Long friendsSince) {
        this.friendID = friendID;
        this.friendsSince = friendsSince;
    }

    @NonNull
    @Override
    public String getType() {
        return TypeConst.Companion.getFRIEND();
    }

    @NonNull
    @Override
    public String getEntityID() {
        return friendID.toString();
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
    public UUID getFriendID() {
        return friendID;
    }

    @Override
    public long getFriendsSince() {
        return friendsSince;
    }

    @Override
    public void setFriendsSince(long date) {
        this.friendsSince = date;
    }

    @Override
    public long getLocalID() {
        return localID;
    }

    @NonNull
    @Override
    public UUID getUserID() {
        return userID;
    }

    @Override
    public void setUserID(@NonNull UUID uuid) {
        this.userID = uuid;
    }

    @Override
    public void setFriendID(@NonNull UUID uuid) {
        this.friendID = uuid;
    }

    @NonNull
    @Override
    public UUID getFriendPairID() {
        return friendPairID;
    }

    @Override
    public void setFriendPairID(@NonNull UUID uuid) {
        this.friendPairID = uuid;
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

    @Override
    public long getAccountCreationDate() {
        return accountCreationDate;
    }

    @Override
    public void setAccountCreationDate(long date) {
        this.accountCreationDate = date;
    }

    @Nullable
    @Override
    public String getCurrentSessionID() {
        return currentSessionID;
    }

    @Override
    public void setCurrentSessionID(@NonNull String string) {
        this.currentSessionID = string;
    }

    @NonNull
    @Override
    public List<TreeSpot> getUserSpots() {
        return null;
    }

    @NonNull
    @Override
    public List<Friend> getUserFriends() {
        return null;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "localID=" + localID +
                ", friendID=" + friendID +
                ", friendsSince=" + friendsSince +
                ", userID=" + userID +
                ", friendPairID=" + friendPairID +
                ", username='" + username + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", accountCreationDate=" + accountCreationDate +
                ", currentSessionID='" + currentSessionID + '\'' +
                '}';
    }
}
