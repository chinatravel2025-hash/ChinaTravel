package com.example.base.database.friend.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


/**
 * 好友的一些状态变化,都存放于此
 */

@Entity(tableName = "FriendStateV2")
public class FriendStateV2 {

    //衣橱柜，更新
    public static final int TYPE_STATE_MOOD = 0;

    public static final int TYPE_STATE_ROOM = 1;

    public static final int TYPE_STATE_LOG = 2;

    public static final int WARDROBE = 3;


    @PrimaryKey(autoGenerate = true)
    private long id;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    /**
     * 当前用户id
     */
    private String userId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    //角色rid
    private String ridStr;

    public void setRidStr(String ridStr) {
        this.ridStr = ridStr;
    }

    public String getRidStr() {
        return ridStr;
    }

    //更新了什么..
    private int updateTarget = WARDROBE;

    public void setUpdateTarget(int updateTarget) {
        this.updateTarget = updateTarget;
    }

    public int getUpdateTarget() {
        return updateTarget;
    }

    //更新次数
    private int number = 1;

    public void setNumber(int number) {
        this.number = number;
    }
    public int getNumber() {
        return number;
    }

    private String extra = "";

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getExtra() {
        return extra;
    }

    @Ignore
    public FriendStateV2(String userId, String ridStr, int updateTarget) {
        this.userId = userId;
        this.ridStr = ridStr;
        this.updateTarget = updateTarget;
    }

    public FriendStateV2(){

    }


}
