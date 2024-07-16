package com.example.base.database.growth.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 用户会角色的成长值
 *
 */

@Entity(tableName = "RoleGrowthInfo")
public class RoleGrowthInfo implements Parcelable {



    @PrimaryKey()
    @NonNull
    private String rid;


    @ColumnInfo(name = "growth")
    private long growth;

    @ColumnInfo(name = "levelGrowth")
    private long levelGrowth;


    @ColumnInfo(name = "level")
    private long level;

    @ColumnInfo(name = "nextGrowth")
    private long nextGrowth;

    @ColumnInfo(name = "nextLevel")
    private long nextLevel;

    @ColumnInfo(name = "spaceLevel")
    private long spaceLevel;

    @ColumnInfo(name = "spaceLevelGrowth")
    private long spaceLevelGrowth;

    @ColumnInfo(name = "spaceNextGrowth")
    private long spaceNextGrowth;

    @ColumnInfo(name = "spaceNextLevel")
    private long spaceNextLevel;

    public RoleGrowthInfo(String rid, long growth,long levelGrowth, long level, long nextGrowth, long nextLevel, long spaceLevel,long spaceLevelGrowth, long spaceNextGrowth, long spaceNextLevel) {
        this.rid = rid;
        this.growth = growth;
        this.levelGrowth = levelGrowth;
        this.level = level;
        this.nextGrowth = nextGrowth;
        this.nextLevel = nextLevel;
        this.spaceLevel = spaceLevel;
        this.spaceLevelGrowth = spaceLevelGrowth;
        this.spaceNextGrowth = spaceNextGrowth;
        this.spaceNextLevel = spaceNextLevel;
    }

    protected RoleGrowthInfo(Parcel in) {
        rid = in.readString();
        growth = in.readLong();
        levelGrowth = in.readLong();
        level = in.readLong();
        nextGrowth = in.readLong();
        nextLevel = in.readLong();
        spaceLevel = in.readLong();
        spaceLevelGrowth = in.readLong();
        spaceNextGrowth = in.readLong();
        spaceNextLevel = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rid);
        dest.writeLong(growth);
        dest.writeLong(levelGrowth);
        dest.writeLong(level);
        dest.writeLong(nextGrowth);
        dest.writeLong(nextLevel);
        dest.writeLong(spaceLevel);
        dest.writeLong(spaceLevelGrowth);
        dest.writeLong(spaceNextGrowth);
        dest.writeLong(spaceNextLevel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RoleGrowthInfo> CREATOR = new Creator<RoleGrowthInfo>() {
        @Override
        public RoleGrowthInfo createFromParcel(Parcel in) {
            return new RoleGrowthInfo(in);
        }

        @Override
        public RoleGrowthInfo[] newArray(int size) {
            return new RoleGrowthInfo[size];
        }
    };

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public long getGrowth() {
        return growth;
    }

    public void setGrowth(long growth) {
        this.growth = growth;
    }

    public long getLevelGrowth() {
        return levelGrowth;
    }

    public void setLevelGrowth(long levelGrowth) {
        this.levelGrowth = levelGrowth;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public long getNextGrowth() {
        return nextGrowth;
    }

    public void setNextGrowth(long nextGrowth) {
        this.nextGrowth = nextGrowth;
    }

    public long getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(long nextLevel) {
        this.nextLevel = nextLevel;
    }

    public long getSpaceLevel() {
        return spaceLevel;
    }

    public void setSpaceLevel(long spaceLevel) {
        this.spaceLevel = spaceLevel;
    }

    public long getSpaceLevelGrowth() {
        return spaceLevelGrowth;
    }

    public void setSpaceLevelGrowth(long spaceLevelGrowth) {
        this.spaceLevelGrowth = spaceLevelGrowth;
    }

    public long getSpaceNextGrowth() {
        return spaceNextGrowth;
    }

    public void setSpaceNextGrowth(long spaceNextGrowth) {
        this.spaceNextGrowth = spaceNextGrowth;
    }

    public long getSpaceNextLevel() {
        return spaceNextLevel;
    }

    public void setSpaceNextLevel(long spaceNextLevel) {
        this.spaceNextLevel = spaceNextLevel;
    }

}
