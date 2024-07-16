package com.example.base.database.friend.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.aws.bean.util.GsonUtil;
import com.example.base.utils.BirthdayUtil;
import com.example.base.utils.DateUtils;
import com.example.base.utils.LogUtils;
import com.github.promeg.pinyinhelper.Pinyin;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMFriendInfo;
import com.tencent.imsdk.v2.V2TIMGroupInfo;
import com.tencent.imsdk.v2.V2TIMGroupMemberFullInfo;
import com.tencent.imsdk.v2.V2TIMUserFullInfo;
import com.tencent.imsdk.v2.V2TIMUserStatus;


@Entity(tableName = "FriendInfo_v12")
public class ContactItemBean  implements Comparable<ContactItemBean> , Parcelable {
    public static final int TYPE_C2C = V2TIMConversation.V2TIM_C2C;
    public static final int TYPE_GROUP = V2TIMConversation.V2TIM_GROUP;
    public static final int TYPE_INVALID = V2TIMConversation.CONVERSATION_TYPE_INVALID;



    public static final int ITEM_BEAN_TYPE_TOPIC = 4;
    public static final int ITEM_BEAN_TYPE_GROUP = 3;
    public static final int ITEM_BEAN_TYPE_CONTACT = 1;
    public static final int ITEM_BEAN_TYPE_CONTROLLER = 2;

    public static final String INDEX_STRING_TOP = "↑";


//
//    @Ignore
//    private int type = TYPE_C2C;
//    public void setType(int type) {
//        this.type = type;
//    }
//    public int getType() {
//        return type;
//    }



    @Ignore
    private Object extraData;
    public void setExtraData(Object extraData) {
        this.extraData = extraData;
    }
    public Object getExtraData() {
        return extraData;
    }


    public boolean isTopic(){
        return itemBeanType == ITEM_BEAN_TYPE_TOPIC;
    }



    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "ridStr")
    private String ridStr;


    @Ignore
    private boolean isTop;


    @Ignore
    private boolean isSelected;
    private boolean isBlackList;

    @ColumnInfo(name = "remark")
    private String remark;

    @ColumnInfo(name = "nickname")
    private String nickName = "";


    private String nameCard;


    @ColumnInfo(name = "headPic")
    private String avatarUrl = "";

    @Ignore
    private String signature;

    private boolean isGroup;
    private String groupType;
    private boolean isFriend = false;

    @Ignore
    private boolean isEnable = true;
    private int statusType = V2TIMUserStatus.V2TIM_USER_STATUS_UNKNOWN;

    @Ignore
    private int itemBeanType = ITEM_BEAN_TYPE_CONTACT;


    @Ignore
    private int avatarResID;

    @Ignore
    private int weight;


    @ColumnInfo(name = "baseIndexPinyin")
    private String baseIndexPinyin;

    public String getBaseIndexPinyin() {
        return baseIndexPinyin;
    }

    public void setBaseIndexPinyin(String baseIndexPinyin) {
        this.baseIndexPinyin = baseIndexPinyin;
    }


    // 所属的分类（名字的汉语拼音首字母） // The category to which it belongs (the first letter of the Chinese pinyin of the name)
    @ColumnInfo(name = "baseIndexTag")
    private String baseIndexTag;

    public String getBaseIndexTag() {
        return baseIndexTag;
    }

    public void setBaseIndexTag(String baseIndexTag) {
        this.baseIndexTag = baseIndexTag;
    }

    public String getSuspensionTag() {
        if (TextUtils.isEmpty(baseIndexTag)) {
            return "";
        }
        return baseIndexTag;
    }



    private int relationType;

    public void setRelationType(int relationType) {
        this.relationType = relationType;
    }

    public int getRelationType() {
        return relationType;
    }


    public ContactItemBean() {}

    public ContactItemBean(String id) {
        this.ridStr = id;
    }

    public ContactItemBean(String id,String nickName) {
        this.ridStr = id;
        this.nickName = nickName;
    }

    public String getRidStr() {
        return ridStr;
    }

    public void setRidStr(String ridStr) {
        this.ridStr = ridStr;
    }


    public ContactItemBean setItemBeanType(int itemBeanType) {
        this.itemBeanType = itemBeanType;
        return this;
    }

    public int getItemBeanType() {
        return itemBeanType;
    }

    public boolean isTop() {
        return isTop;
    }

    public ContactItemBean setTop(boolean top) {
        isTop = top;
        return this;
    }

    public String getTarget() {
        return getDisplayName();
    }

    public boolean isNeedToPinyin() {
        return !isTop;
    }

    public boolean isShowSuspension() {
        return !isTop;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public boolean isBlackList() {
        return isBlackList;
    }

    public void setBlackList(boolean blacklist) {
        isBlackList = blacklist;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }


    @Ignore
    private V2TIMFriendInfo friendInfo;
    public void setFriendInfo(V2TIMFriendInfo friendInfo) {
        this.friendInfo = friendInfo;
    }
    public V2TIMFriendInfo getFriendInfo() {
        return friendInfo;
    }

    public void putCustomInfo(String key,  byte[] bytes){
        this.getFriendInfo().getFriendCustomInfo().put(key,bytes);
    }

    public byte[] getCustomInfo(String key){
        return this.getFriendInfo().getFriendCustomInfo().get(key);
    }




    //这个是干嘛的
    @ColumnInfo(name = "creatAt")
    private String creatAt = "";
    public void setCreatAt(String creatAt) {
        this.creatAt = creatAt;
    }
    public String getCreatAt() {
        return creatAt;
    }

    @ColumnInfo(name = "spaceData")
    private String spaceData;

    public void setSpaceData(String spaceData) {
        this.spaceData = spaceData;
    }
    public String getSpaceData(){
        return spaceData;
    }

    @ColumnInfo(name = "birthday")
    private String birthday = "";

    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


    @ColumnInfo(name = "spaceLevel")
    private int spaceLevel = 0;
    public void setSpaceLevel(int spaceLevel) {
        this.spaceLevel = spaceLevel;
    }
    //获取空间等级
    public int getSpaceLevel(){
        return spaceLevel;
    }


    @ColumnInfo(name = "level")
    private int level;
    public void setLevel(int level) {
        this.level = level;
    }
    //等级
    public int getLevel(){
        return level;
    }


    @ColumnInfo(name = "spaceImgUrl")
    private String spaceImgUrl = "";
    public void setSpaceImgUrl(String spaceImgUrl) {
        this.spaceImgUrl = spaceImgUrl;
    }
    //获取空间照片
    public String getSpaceImgUrl(){
        return spaceImgUrl;
    }

    @ColumnInfo(name = "bodyPic")
    private String bodyPic = "";
    public void setBodyPic(String bodyPic) {
        this.bodyPic = bodyPic;
    }
    //获取身体照片
    public String getBodyPic(){
        return bodyPic;
    }

    /**
     * 背景
     */
    @ColumnInfo(name = "bg")
    private String bg = "";

    public void setBg(String bg) {
        this.bg = bg;
    }

    //获取背景图
    public String getBg(){
        return bg;
    }

    /**
     * 背景
     */
    @ColumnInfo(name = "bodyData")
    private String bodyData = "";
    public void setBodyData(String bodyData) {
        this.bodyData = bodyData;
    }
    public String getBodyData() {
        return bodyData;
    }

    //设置性别
    @ColumnInfo(name = "gener")
    private int gener = 1;
    public void setGener(int gener) {
        this.gener = gener;
    }

    public int getGener() {
        return gener;
    }

    @ColumnInfo(name = "memberStatus")
    private int memberStatus = 0;
    public void setMemberStatus(int memberStatus) {
        this.memberStatus = memberStatus;
    }
    //获取会员状态
    public int getMemberStatus(){
        return memberStatus;
    }


    public Boolean isVip(){
        return isVip;
    }

    @Ignore
    public Boolean isVip = false;

    public void setVip(Boolean vip) {
        isVip = vip;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setNameCard(String nameCard) {
        this.nameCard = nameCard;
    }

    public String getNameCard() {
        return nameCard;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getStatusType() {
        return statusType;
    }

    public void setStatusType(int statusType) {
        this.statusType = statusType;
    }

    public int getAvatarResID() {
        return avatarResID;
    }

    public void setAvatarResID(int avatarResID) {
        this.avatarResID = avatarResID;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    public ContactItemBean covertTIMGroupBaseInfo(V2TIMGroupInfo group) {
        if (group == null) {
            return this;
        }
        setRidStr(group.getGroupID());
        setRemark(group.getGroupName());
        setAvatarUrl(group.getFaceUrl());
        setGroup(true);
        setGroupType(group.getGroupType());
        return this;
    }

    public ContactItemBean covertTIMGroupMemberFullInfo(V2TIMGroupMemberFullInfo member) {
        if (member == null) {
            return this;
        }
        setRidStr(member.getUserID());
        setNameCard(member.getNameCard());
        setRemark(member.getFriendRemark());
        setNickName(member.getNickName());
        String py = Pinyin.toPinyin(getDisplayName(),"");
        String p = "#";
        if(!TextUtils.isEmpty(py)){
           p = py.substring(0,1);
        }

        setBaseIndexPinyin(p);
        setAvatarUrl(member.getFaceUrl());
        setGroup(false);
        return this;
    }

    public String getDisplayName() {
        if (!TextUtils.isEmpty(nameCard)) {
            return nameCard;
        } else if (!TextUtils.isEmpty(remark)) {
            return remark;
        } else if (!TextUtils.isEmpty(nickName)) {
            return nickName;
        } else {
            return "";
        }
    }

    @Override
    public int compareTo(ContactItemBean contactItemBean) {

        if(this.baseIndexTag.equals("#") && !contactItemBean.baseIndexTag.equals("#")){
            return  1;
        }else if(!this.baseIndexTag.equals("#") && contactItemBean.baseIndexTag.equals("#")){
            return  -1;
        }else {
            int a = this.baseIndexTag.charAt(0);
            int b = contactItemBean.baseIndexTag.charAt(0);
            if(a > b){
                return 1;
            }else if(a == b) {
                return 0;
            }else {
                return -1;
            }
        }
    }


    public static final Creator<ContactItemBean> CREATOR = new Creator<ContactItemBean>() {
        @Override
        public ContactItemBean createFromParcel(Parcel in) {
            return new ContactItemBean(in);
        }

        @Override
        public ContactItemBean[] newArray(int size) {
            return new ContactItemBean[size];
        }
    };

    protected ContactItemBean(Parcel in) {
        ridStr = in.readString();
        nickName = in.readString();
        avatarUrl = in.readString();
        bodyData = in.readString();
        creatAt = in.readString();
        gener = in.readInt();
        baseIndexTag = in.readString();
        baseIndexPinyin = in.readString();
        spaceData = in.readString();
        birthday = in.readString();
        spaceLevel = in.readInt();
        level = in.readInt();
        spaceImgUrl = in.readString();
        bodyPic = in.readString();
        bg = in.readString();
        remark = in.readString();
        memberStatus = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(ridStr);
        dest.writeString(nickName);
        dest.writeString(avatarUrl);
        dest.writeString(bodyData);
        dest.writeString(creatAt);
        dest.writeInt(gener);
        dest.writeString(baseIndexTag);
        dest.writeString(baseIndexPinyin);
        dest.writeString(spaceData);
        dest.writeString(birthday);
        dest.writeInt(spaceLevel);
        dest.writeInt(level);
        dest.writeString(spaceImgUrl);
        dest.writeString(bodyPic);
        dest.writeString(bg);
        dest.writeString(remark);
        dest.writeInt(memberStatus);
    }
}
