package com.example.base.database.growth.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.base.database.growth.dao.RoleGrowthInfoDao;
import com.example.base.database.growth.entity.RoleGrowthInfo;

@Database(entities = {RoleGrowthInfo.class},version = 3)
public abstract class RoleGrowthInfoDataBase extends RoomDatabase {
    public abstract RoleGrowthInfoDao roleGrowthInfoDao();
}
