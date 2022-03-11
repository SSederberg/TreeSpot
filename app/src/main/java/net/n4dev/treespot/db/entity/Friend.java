package net.n4dev.treespot.db.entity;

import androidx.annotation.NonNull;

import net.n4dev.treespot.core.api.IFriend;
import net.n4dev.treespot.db.constants.TreeSpotFriendsConstants;
import net.n4dev.treespot.db.UUIDConverter;

import java.util.UUID;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;

@Entity
public class Friend implements IFriend {

    @Id public Long localID;

    @NameInDb(TreeSpotFriendsConstants.FRIEND_ID)
    @Convert(converter = UUIDConverter.class, dbType = String.class)
    private UUID friendID;

    @NameInDb(TreeSpotFriendsConstants.FRIENDS_SINCE) private Long friendsSince;

    @NameInDb(TreeSpotFriendsConstants.USER_ID)
    @Convert(converter = UUIDConverter.class, dbType = String.class)
    private UUID userID;

    @NameInDb(TreeSpotFriendsConstants.FRIEND_PAIR_ID)
    @Convert(converter = UUIDConverter.class, dbType = String.class)
    private UUID friendPairID;

    public Friend(UUID friendID, Long friendsSince) {
        this.friendID = friendID;
        this.friendsSince = friendsSince;
    }

    public Friend() {}


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
}
