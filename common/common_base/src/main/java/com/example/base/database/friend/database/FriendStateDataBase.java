package com.example.base.database.friend.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.base.database.friend.dao.FriendStateDao;
import com.example.base.database.friend.entity.FriendState;

@Database(entities = {FriendState.class},version = 3)
public abstract class FriendStateDataBase extends RoomDatabase {
    public abstract FriendStateDao friendStateDao();
}
