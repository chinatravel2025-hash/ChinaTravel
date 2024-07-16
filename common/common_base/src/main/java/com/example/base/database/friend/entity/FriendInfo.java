package com.example.base.database.friend.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


/**
 * 通讯录
 *
 * @Description:通讯录
 */

//@Entity(tableName = "FriendInfo_v12")
public class FriendInfo implements Parcelable {

    /**
     * 当前用户id 系统用户的uid -> 下面很多角色ridStr
     */
    @ColumnInfo(name = "uid")
    private long uid;


    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "ridStr")
    private String ridStr;


    private long rid_;

    public void setRid_(long rid_) {
        this.rid_ = rid_;
    }

    public long getRid_() {
        return rid_;
    }

    public void setRidStr(String ridStr) {
        this.ridStr = ridStr;
    }

    public String getRidStr() {
        return ridStr;
    }

    /**
     * 昵称
     */
    @ColumnInfo(name = "nickname_")
    private String nickname_;
    /**
     * 备注
     */
    @ColumnInfo(name = "remark")
    private String remark;

    /**
     * 头像
     */
    @ColumnInfo(name = "headPic_")
    private String headPic_;

    /**
     * 身体数据
     */
    @ColumnInfo(name = "avatar_")
    private String avatar_;

    /**
     * 创建时间
     */
    @ColumnInfo(name = "createdAt_")
    private String createdAt_;

    /**
     * 性别 群组时这个字段是成员数
     */
    @ColumnInfo(name = "gender_")
    private int gender_;

    /**
     * 排序ID
     */
    @ColumnInfo(name = "sortId")
    private String sortId;

    /**
     * 排序名称
     */
    @ColumnInfo(name = "sortName")
    private String sortName;



    /**
     * 上线状态 0正常  1被对方拉黑  或被对方删除
     */
    @ColumnInfo(name = "sideState")
    private int sideState;

    /**
     * 空间数据
     */
    @ColumnInfo(name = "spaceData_")
    private String spaceData_;
    /**
     * 生日
     */
    @ColumnInfo(name = "birthday_")
    private String birthday_;

    /**
     * 空间等级
     */
    @ColumnInfo(name = "spaceLevel_")
    private int spaceLevel_;

    /**
     * 等级
     */
    @ColumnInfo(name = "level_")
    private int level_;

    /**
     * 空间照片
     */
    @ColumnInfo(name = "spaceImgUrl")
    private String spaceImgUrl;

    /**
     * 身体照片
     */
    @ColumnInfo(name = "bodyPic_")
    private String bodyPic;
    /**
     * 背景
     */
    @ColumnInfo(name = "bg")
    private String bg;

    /**
     * 会员
     */
    @ColumnInfo(name = "memberStatus_")
    private int memberStatus;
    protected FriendInfo(Parcel in) {
        uid = in.readLong();
        ridStr = in.readString();
        nickname_ = in.readString();
        headPic_ = in.readString();
        avatar_ = in.readString();
        createdAt_ = in.readString();
        gender_ = in.readInt();
        sortId = in.readString();
        sortName = in.readString();
        spaceData_ = in.readString();
        birthday_ = in.readString();
        spaceLevel_ = in.readInt();
        level_ = in.readInt();
        sideState = in.readInt();
        spaceImgUrl = in.readString();
        bodyPic = in.readString();
        bg = in.readString();
        remark = in.readString();
        memberStatus = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(uid);
        dest.writeString(ridStr);
        dest.writeString(nickname_);
        dest.writeString(headPic_);
        dest.writeString(avatar_);
        dest.writeString(createdAt_);
        dest.writeInt(gender_);
        dest.writeInt(spaceLevel_);
        dest.writeInt(level_);
        dest.writeString(sortId);
        dest.writeString(sortName);
        dest.writeString(spaceData_);
        dest.writeString(birthday_);
        dest.writeInt(sideState);
        dest.writeString(spaceImgUrl);
        dest.writeString(bodyPic);
        dest.writeString(bg);
        dest.writeString(remark);
        dest.writeInt(memberStatus);
    }

    public static final Creator<FriendInfo> CREATOR = new Creator<FriendInfo>() {
        @Override
        public FriendInfo createFromParcel(Parcel in) {
            return new FriendInfo(in);
        }

        @Override
        public FriendInfo[] newArray(int size) {
            return new FriendInfo[size];
        }
    };

    @Ignore
    public FriendInfo(long userId, String ridStr , String nickname_, String headPic_, String avatar_, String createdAt_, int gender_, String sortId,
                      String sortName, int spaceLevel_, int level_, int sideState,int memberStatus_) {
        this.uid = userId;
        this.nickname_ = nickname_;
        this.headPic_ = headPic_;
        this.avatar_ = avatar_;
        this.createdAt_ = createdAt_;
        this.gender_ = gender_;
        this.sortId = sortId;
        this.sortName = sortName;
        this.spaceLevel_ = spaceLevel_;
        this.level_ = level_;
        this.sideState = sideState;
        this.memberStatus=memberStatus_;
    }

    public FriendInfo(long userId, String ridStr, String nickname_, String headPic_, String avatar_,
                      String createdAt_, int gender_, String sortId, String sortName, String spaceData,
                      String birthday, int spaceLevel_, int level_,
                      int sideState,String spaceImgUrl,String bodyPic,String bg,String remark,int memberStatus_) {
        this.uid = userId;
        this.ridStr = ridStr;
        this.nickname_ = nickname_;
        this.headPic_ = headPic_;
        this.avatar_ = avatar_;
        this.createdAt_ = createdAt_;
        this.gender_ = gender_;
        this.sortId = sortId;
        this.sortName = sortName;
        this.spaceData_ = spaceData;
        this.birthday_ = birthday;
        this.spaceLevel_ = spaceLevel_;
        this.level_ = level_;
        this.sideState = sideState;
        this.spaceImgUrl = spaceImgUrl;
        this.bodyPic = bodyPic;
        this.bg = bg;
        this.remark = remark;
        this.memberStatus = memberStatus_;
    }

    public FriendInfo(String ridStr) {
        this.ridStr = ridStr;
        this.nickname_ = "";
        this.headPic_ = "";
        this.avatar_ = "";
        this.createdAt_ = "";
        this.gender_ = 0;
        this.spaceLevel_ = 0;
        this.level_ = 0;
        this.sortId = "#";
        this.sortName = "#";
        this.spaceData_ = "";
        this.birthday_ = "";
        this.sideState = 0;
        this.spaceImgUrl = "";
        this.bodyPic = "";
        this.bg = "";
        this.remark = "";
        this.memberStatus = 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public String getNickname_() {
        return nickname_;
    }

    public String getNickname() {
        if(!TextUtils.isEmpty(remark)){
            return remark;
        }
        return nickname_;
    }

    public void setNickname_(String nickname_) {
        this.nickname_ = nickname_;
    }

    public String getHeadPic_() {
        return headPic_;
    }

    public void setHeadPic_(String headPic_) {
        this.headPic_ = headPic_;
    }

    public String getAvatar_() {
        return avatar_;
    }

    public void setAvatar_(String avatar_) {
        this.avatar_ = avatar_;
    }

    public String getCreatedAt_() {
        return createdAt_;
    }

    public void setCreatedAt_(String createdAt_) {
        this.createdAt_ = createdAt_;
    }

    public int getGender_() {
        return gender_;
    }

    public void setGender_(int gender_) {
        this.gender_ = gender_;
    }

    public int getSpaceLevel_() {
        return spaceLevel_;
    }

    public void setSpaceLevel_(int spaceLevel_) {
        this.spaceLevel_ = spaceLevel_;
    }

    public int getLevel_() {
        return level_;
    }

    public void setLevel_(int level_) {
        this.level_ = level_;
    }

    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        if(TextUtils.isEmpty(sortId) || TextUtils.isEmpty(sortId.trim())){
            this.sortId = "#";
            return;
        }
        this.sortId = sortId;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }


    public String getSpaceData_() {
        return spaceData_;
    }

    public void setSpaceData_(String spaceData_) {
        this.spaceData_ = spaceData_;
    }

    public String getBirthday_() {
        return birthday_;
    }

    public void setBirthday_(String birthday_) {
        this.birthday_ = birthday_;
    }

    public int getSideState() {
        return sideState;
    }

    public void setSideState(int sideState) {
        this.sideState = sideState;
    }

    public String getSpaceImgUrl() {
        return spaceImgUrl;
    }

    public void setSpaceImgUrl(String spaceImgUrl) {
        this.spaceImgUrl = spaceImgUrl;
    }

    public String getBodyPic() {
        return bodyPic;
    }

    public void setBodyPic(String bodyPic) {
        this.bodyPic = bodyPic;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(int memberStatus_) {
        this.memberStatus = memberStatus_;
    }


}
