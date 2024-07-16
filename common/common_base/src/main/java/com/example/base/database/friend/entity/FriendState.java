package com.example.base.database.friend.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


/**
 * 好友状态
 *
 * @Description:通讯录好友状态
 */

@Entity(tableName = "FriendState")
public class FriendState implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    /**
     * 当前用户id
     */
    @ColumnInfo(name = "userId")
    private String userId;

    private String rid;

    /**
     * 心情有更新 0没有 1有
     */
    @ColumnInfo(name = "isNewMood")
    private int isNewMood;
    /**
     * 空间有更新 0没有 1有
     */
    @ColumnInfo(name = "isNewSpace")
    private int isNewSpace;

    protected FriendState(Parcel in) {
        userId = in.readString();
        rid = in.readString();
        isNewMood = in.readInt();
        isNewSpace = in.readInt();
    }

    public static final Creator<FriendState> CREATOR = new Creator<FriendState>() {
        @Override
        public FriendState createFromParcel(Parcel in) {
            return new FriendState(in);
        }

        @Override
        public FriendState[] newArray(int size) {
            return new FriendState[size];
        }
    };

    @Ignore
    public FriendState(String userId, String rid_,int isNewMood, int isNewSpace) {
        this.userId = userId;
        this.rid = rid_;
        this.isNewMood = isNewMood;
        this.isNewSpace = isNewSpace;
    }

    public FriendState(String userId) {
        this.userId = userId;
        this.rid = "";
        this.isNewMood = 0;
        this.isNewSpace = 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(rid);
        dest.writeInt(isNewMood);
        dest.writeInt(isNewSpace);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getIsNewMood() {
        return isNewMood;
    }

    public void setIsNewMood(int isNewMood) {
        this.isNewMood = isNewMood;
    }

    public int getIsNewSpace() {
        return isNewSpace;
    }

    public void setIsNewSpace(int isNewSpace) {
        this.isNewSpace = isNewSpace;
    }
}
