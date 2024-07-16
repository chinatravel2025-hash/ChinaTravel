package com.example.base.database.friend.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.base.database.friend.entity.FriendState;
import com.example.base.database.friend.entity.FriendStateV2;

import java.util.List;

@Dao
public interface FriendStateV2Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FriendStateV2... friendStates);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<FriendStateV2> friendStates);

    @Query("DELETE FROM FriendStateV2 WHERE userId = :userId")
    void deleteAll(String userId);

    @Query("DELETE FROM FriendStateV2 WHERE userId = :userId and ridStr = :ridStr")
    void delete(String userId, String ridStr);

    @Query("DELETE FROM FriendStateV2 WHERE userId = :userId and ridStr = :ridStr and updateTarget = :updateTarget")
    void delete(String userId, String ridStr,int updateTarget);

    @Update
    void update(FriendStateV2 friendStateV2);

    @Query("SELECT * FROM FriendStateV2  WHERE userId = :userId and ridStr = :ridStr AND updateTarget = :updateTarget")
    FriendStateV2 getFriendStateV2(String userId, String ridStr,int updateTarget);


}
