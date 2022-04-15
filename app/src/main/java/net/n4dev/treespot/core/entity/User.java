package net.n4dev.treespot.core.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.n4dev.treespot.core.api.IUser;
import net.n4dev.treespot.db.TreeSpotObjectBox;
import net.n4dev.treespot.db.UUIDConverter;
import net.n4dev.treespot.db.constants.TreeSpotUserConstants;
import net.n4dev.treespot.db.query.GetUserTreeSpotsQuery;

import java.util.List;
import java.util.UUID;

import io.objectbox.Box;
import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;
import io.objectbox.annotation.Transient;
import io.objectbox.annotation.Unique;
import io.objectbox.query.Query;

@Entity
public class User implements IUser {

    @Id
    public Long localID;

    @NameInDb(TreeSpotUserConstants.USER_ID)
    @Convert(converter = UUIDConverter.class, dbType = String.class)
    private UUID userID;

    @Unique(onConflict = ConflictStrategy.REPLACE)
    @NameInDb(TreeSpotUserConstants.USERNAME)
    private String username;

    @NameInDb(TreeSpotUserConstants.EMAIL_ADDRESS)
    private String emailAddress;

    @NameInDb(TreeSpotUserConstants.USER_CREATION_DATE)
    private Long accountCreationDate;

    @NameInDb(TreeSpotUserConstants.USER_ACTIVE_SESSION_ID)
    @Nullable private String currentSessionID;

    @NameInDb(TreeSpotUserConstants.LAST_ONLINE)
    private Long lastOnline;

    @Transient
    private final Box<User> userBoxInstance;

    @Transient
    private final Box<TreeSpot> treeSpotBoxInstance;

    public User() {
        //Required to compile
        userBoxInstance = TreeSpotObjectBox.INSTANCE.getBoxStore().boxFor(User.class);
        treeSpotBoxInstance = TreeSpotObjectBox.INSTANCE.getBoxStore().boxFor(TreeSpot.class);
    }

    public User(String username, String emailAddress, UUID uuid) {
        userBoxInstance = TreeSpotObjectBox.INSTANCE.getBoxStore().boxFor(User.class);
        treeSpotBoxInstance = TreeSpotObjectBox.INSTANCE.getBoxStore().boxFor(TreeSpot.class);

        this.username = username;
        this.emailAddress = emailAddress;
        this.userID = uuid;
    }

    @NonNull
    @Override
    public String getType() {
        return TypeConst.Companion.getUSER();
    }

    @NonNull
    @Override
    public String getEntityID() {
        return userID.toString();
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
    public UUID getUserID() {
        return userID;
    }

    @Override
    public void setUserID(@NonNull UUID userID) {
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
    public List<TreeSpot> getUserSpots() {
        Query<TreeSpot> query = GetUserTreeSpotsQuery.Companion.get(userID.toString());
        List<TreeSpot> returnedSpots = query.find();
        query.close();
        return returnedSpots;
//        return null;
    }

    @NonNull
    @Override
    public List<Friend> getUserFriends() {
        return null;
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

    @Override
    public long getLastOnline() {
        return lastOnline;
    }

    @Override
    public void setLastOnline(long date) {
        this.lastOnline = date;
    }

    public static User convertFromAWUser(io.appwrite.models.User user) {
        User returnedUser = new User(user.getName(), user.getEmail(), UUID.fromString(user.getId()));
        returnedUser.setAccountCreationDate(user.getRegistration());
        returnedUser.setLastOnline(0);
        return returnedUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "localID=" + localID +
                ", userID=" + userID +
                ", username='" + username + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", accountCreationDate=" + accountCreationDate +
                ", currentSessionID='" + currentSessionID + '\'' +
                '}';
    }
}
