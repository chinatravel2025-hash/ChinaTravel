package com.example.base.base.user.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.base.base.user.entity.UserInfo;

import java.util.List;

@Dao
public interface UserInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserInfo... infos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<UserInfo> infos);

    @Query("DELETE FROM UserInfo_v12")
    void deleteAll();

    @Query("DELETE FROM UserInfo_v12 WHERE ridStr_ = :ridStr")
    void delete(String ridStr);

    @Update
    void update(UserInfo userInfo);

    @Query("UPDATE  UserInfo_v12 SET loginState = 0")
    int logout();

    @Query("SELECT * FROM UserInfo_v12  WHERE ridStr_ != '' order by loginTime_ asc")
    List<UserInfo> getUserInfos();

    @Query("SELECT count(*) FROM UserInfo_v12")
    int getInfoCount();


    @Query("SELECT * FROM UserInfo_v12  WHERE ridStr_ = :ridStr")
    UserInfo getUserInfo(String ridStr);

    @Query("SELECT * FROM UserInfo_v12  WHERE loginState = 1")
    UserInfo queryCurrentInfo();

    @Query("UPDATE UserInfo_v12 set uid_ = :uid")
    int updateUid(String uid);

}
