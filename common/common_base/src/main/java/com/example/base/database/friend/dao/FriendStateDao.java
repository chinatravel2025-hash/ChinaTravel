package com.example.base.database.friend.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.base.database.friend.entity.FriendState;

import java.util.List;

@Dao
public interface FriendStateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FriendState... friendStates);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<FriendState> friendStates);

    @Query("DELETE FROM FriendState WHERE userId = :userId")
    void deleteAll(String userId);

    @Query("DELETE FROM FriendState WHERE userId = :userId and rid = :rid")
    void delete(String userId, String rid);

    @Update
    void update(FriendState hs);

    @Query("SELECT * FROM FriendState  WHERE userId = :userId and rid = :rid")
    FriendState getUserInfo(String userId, String rid);


}
