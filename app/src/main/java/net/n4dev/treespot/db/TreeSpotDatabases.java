package net.n4dev.treespot.db;

import android.content.Context;

import com.couchbase.lite.CouchbaseLite;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseConfiguration;

public class TreeSpotDatabases {

    private static Database treeSpotDB;
    private static Database spotsDB;
    private static Database friendsDB;
    private static Database friendRequestsDB;
    private static Database userDB;

    public void init(Context context) throws CouchbaseLiteException {
        CouchbaseLite.init(context);

        DatabaseConfiguration configuration = new DatabaseConfiguration();

        configuration.setDirectory(context.getFilesDir().getAbsolutePath());


      if(treeSpotDB == null || spotsDB == null || friendsDB == null || friendRequestsDB == null) {
          treeSpotDB = new Database(TreeSpotsDatabase.name, configuration);
          spotsDB = new Database(TreeSpotsDatabase.name, configuration);
          friendsDB = new Database(TreeSpotFriendsDatabase.name, configuration);
          friendRequestsDB = new Database(TreeSpotFriendRequestsDatabase.name, configuration);
          userDB = new Database(TreeSpotUserDB.name, configuration);
      }
    }

    public Database getTreeSpotDB() {
        return treeSpotDB;
    }

    public Database getSpotsDB() {
        return spotsDB;
    }

    public Database getFriendsDB() {
        return friendsDB;
    }

    public Database getFriendRequestsDB() {
        return friendRequestsDB;
    }

    public Database getUserDB() {
        return userDB;
    }
}
