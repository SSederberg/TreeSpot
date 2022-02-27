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
    DatabaseConfiguration configuration;

    public void init(Context context) throws CouchbaseLiteException {
        CouchbaseLite.init(context);


        configuration = new DatabaseConfiguration();

        String destPath = context.getFilesDir().getPath();
        destPath = destPath.substring(0, destPath.lastIndexOf("/")) + "/databases";

        configuration.setDirectory(destPath);

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
        if(userDB != null) {
            return userDB;
        }

        try {
            return new Database(TreeSpotUserDB.name, configuration);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        return null;
    }
}
