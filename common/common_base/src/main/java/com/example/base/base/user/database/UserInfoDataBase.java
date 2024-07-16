package com.example.base.base.user.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.base.base.user.dao.UserInfoDao;
import com.example.base.base.user.entity.UserInfo;

@Database(entities = {UserInfo.class},version = 3)
public abstract class UserInfoDataBase extends RoomDatabase {
    public abstract UserInfoDao userInfoDao();
}
