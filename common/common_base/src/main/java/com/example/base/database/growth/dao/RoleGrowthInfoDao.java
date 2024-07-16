package com.example.base.database.growth.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.base.database.growth.entity.RoleGrowthInfo;

import java.util.List;


@Dao
public interface RoleGrowthInfoDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RoleGrowthInfo... infos);

    @Query("DELETE FROM RoleGrowthInfo")
    void deleteAll();

    @Update
    void update(RoleGrowthInfo ug);

    @Query("DELETE FROM RoleGrowthInfo WHERE  rid = :rid")
    void delete(String rid);


    @Query("SELECT * FROM RoleGrowthInfo  WHERE rid = :rid")
    RoleGrowthInfo getRoleGrowthInfo(String rid);

    @Query("SELECT * FROM RoleGrowthInfo")
    List<RoleGrowthInfo> getUserGrowthList();
}
