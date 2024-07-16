package com.example.base.database.friend.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.base.database.friend.entity.ContactItemBean;

import java.util.List;

@Dao
public interface FriendInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ContactItemBean... infos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ContactItemBean> friendInfos);


    @Query("DELETE FROM FriendInfo_v12")
    void clearAll();

    @Query("DELETE FROM FriendInfo_v12 WHERE  ridStr = :ridStr")
    void delete(String ridStr);

    @Update
    void update(ContactItemBean hs);

    //查询单个人信息
    @Query("SELECT * FROM FriendInfo_v12 WHERE ridStr = :ridStr")
    ContactItemBean getFriendInfo(String ridStr);


    /**
     * 查询多个人信息
     * @param ridStrs
     * @return
     */
    @Query("SELECT * FROM FriendInfo_v12  WHERE  ridStr in (:ridStrs)  order by baseIndexTag asc")
    List<ContactItemBean> getFriendInfo(List<String> ridStrs);



//    @Query("SELECT * FROM FriendInfo_v12 WHERE ridStr = :ridStr order by sortId asc")
//    List<FriendInfo> getFriendInfos(String ridStr);

//    @Query("SELECT * FROM FriendInfo_v12 WHERE ridStr = :ridStr  and nickname_ like (:keyword) order by sortId asc")
//    List<FriendInfo> getFriendInfo(String ridStr, String keyword);
//
//    @Query("SELECT * FROM FriendInfo_v12  WHERE ridStr = :ridStr and rid_ in (:rid) and friendState = :friendState order by sortId asc")
//    List<FriendInfo> getFriendInfo(String ridStr, List<Long> rid, int friendState);
//
//    @Query("SELECT count(*) FROM FriendInfo_v12  WHERE userId = :userId and rid_ = :rid and friendState = :friendState")
//    int getFriendInfoRidCount(long userId, long rid, int friendState);
//
//    @Query("SELECT * FROM FriendInfo_v12  WHERE userId = :userId and rid_ = :rid order by sortId asc")
//    FriendInfo getUserInfo(long userId, long rid);
//
//    @Query("SELECT count(*) FROM FriendInfo_v12  WHERE userId = :userId")
//    int getFriendInfoCount(long userId);
//
//    @Query("SELECT ridStr FROM FriendInfo_v12  WHERE userId = :userId")
//    List<Long> getFriendInfoRids(long userId);

}
