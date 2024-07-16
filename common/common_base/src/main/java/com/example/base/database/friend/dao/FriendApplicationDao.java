package com.example.base.database.friend.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.base.database.friend.entity.FriendApplicationLocalRecord;

import java.util.List;

@Dao
public interface FriendApplicationDao {



    @Query("SELECT * FROM FriendApplicationLocalRecord WHERE ownerIdStr =:ownerIdStr order by createdAt desc")
    List<FriendApplicationLocalRecord> getFriendApplicationInfolist(String ownerIdStr);



    @Query("SELECT count(*) FROM FriendApplicationLocalRecord WHERE ownerIdStr =:ownerIdStr AND ridStr=:applyFriendIdStr")
    int exsistApplicationFriend(String ownerIdStr,String applyFriendIdStr);

    @Query("SELECT * FROM FriendApplicationLocalRecord WHERE ownerIdStr =:ownerIdStr AND ridStr=:applyFriendIdStr")
    FriendApplicationLocalRecord query(String ownerIdStr,String applyFriendIdStr);



    //update 就使用这个更新吧.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FriendApplicationLocalRecord... infos);


    @Query("UPDATE FriendApplicationLocalRecord set status =:status WHERE ownerIdStr =:ownerIdStr AND ridStr=:ridStr")
    void update(String ownerIdStr,String ridStr,int status);


    //只比较主键条件就能删除.
    @Delete
    void delete(FriendApplicationLocalRecord... infos);

}
