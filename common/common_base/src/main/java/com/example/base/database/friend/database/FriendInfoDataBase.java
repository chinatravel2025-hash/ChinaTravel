package com.example.base.database.friend.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.base.database.friend.dao.FriendInfoDao;
import com.example.base.database.friend.dao.FriendApplicationDao;
import com.example.base.database.friend.dao.FriendStateV2Dao;
import com.example.base.database.friend.entity.ContactItemBean;
import com.example.base.database.friend.entity.FriendApplicationLocalRecord;
import com.example.base.database.friend.entity.FriendStateV2;

@Database(entities = {ContactItemBean.class, FriendApplicationLocalRecord.class, FriendStateV2.class},version = 4)
public abstract class FriendInfoDataBase extends RoomDatabase {
    public abstract FriendInfoDao friendInfoDao();
    public abstract FriendApplicationDao friendApplicationDao();


    public abstract FriendStateV2Dao friendStateV2Dao();


}
