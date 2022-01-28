package net.n4dev.treespot.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import net.n4dev.treespot.core.TreeSpot;
import net.n4dev.treespot.core.User;
import net.n4dev.treespot.db.dao.TreeSpotDAO;
import net.n4dev.treespot.db.dao.UserDAO;

@Database(entities = {User.class, TreeSpot.class}, version = 0, exportSchema = true)
@TypeConverters(value = {UUIDConverter.class})
public abstract class TreeSpotDatabase extends RoomDatabase {

    public UserDAO userDAO;
    public TreeSpotDAO treeSpotDAO;
    private static TreeSpotDatabase instance;

    public static TreeSpotDatabase getDatabase(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context, TreeSpotDatabase.class, "treespot")
                .enableMultiInstanceInvalidation()
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }
}
