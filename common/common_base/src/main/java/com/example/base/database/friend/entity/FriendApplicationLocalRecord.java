package com.example.base.database.friend.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/**
 * 通讯录
 *
 * @Description:通讯录
 */
@Entity(tableName = FriendApplicationLocalRecord.TAB_NAME,primaryKeys = {"ownerIdStr","ridStr"})
public class FriendApplicationLocalRecord implements Comparable<FriendApplicationLocalRecord> {

    public static final String TAB_NAME = "FriendApplicationLocalRecord";

    /**
     *   case 0: return Applying;
     *   case 1: return Agree;
     *   case 2: return Refuse;
     *   case 3: return Expire;
     *   case 4: return Delete;
     */
    //申请中
    public static int Applying = 0;
    //同意
    public static int Agree = 1;
    //拒绝
    public static int Refuse = 2;
    //已过期
    public static int Expire = 3;
    //删除
    public static int Delete = 4;

    public String getStatusText(){
        //发送者
        if(isSender){
            if(status == Applying){
                return "等待验证";
            }else if(status == Agree){
                return "对方已同意";
            }else if(status == Refuse){
                return "对方已拒绝";
            }else if(status == Expire){
                return "已过期";
            }else if(status == Delete){
                return "已删除";
            }
        }else { //别人发过来的请求
            if(status == Applying){
                return ""; //这个时候展示添加，取消按钮
            }else if(status == Agree){
                return "已添加";
            }else if(status == Refuse){
                return "已忽略";
            }else if(status == Expire){
                return "已过期";
            }else if(status == Delete){
                return "已删除";
            }
        }

        return "未知的状态";
    }


    //分角色的....哪个角色下面的数据
    @NonNull
    private String ownerIdStr;

    public String getOwnerIdStr() {
        return ownerIdStr;
    }

    public void setOwnerIdStr(String ownerIdStr) {
        this.ownerIdStr = ownerIdStr;
    }

    @NonNull
    @ColumnInfo(name = "ridStr")
    private String ridStr;

    public void setRidStr(String ridStr) {
        this.ridStr = ridStr;
    }

    public String getRidStr() {
        return ridStr;
    }



    /**
     * 展示名称
     */
    @ColumnInfo(name = "displayName")
    private String displayName;

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * 状态
     */
    @ColumnInfo(name = "status")
    private int status = Applying ;

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    /**
     * 头像
     */
    @ColumnInfo(name = "headPic")
    private String headPic;

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getHeadPic() {
        return headPic;
    }

    /**
     * 背景
     */
    @ColumnInfo(name = "createdAt")
    private long createdAt;

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    /**
     * 会员状态设置
     */
    @ColumnInfo(name = "memberStatus")
    private int memberStatus;
    public void setMemberStatus(int memberStatus) {
        this.memberStatus = memberStatus;
    }

    public int getMemberStatus() {
        return memberStatus;
    }


    /**
     * 添加来源
     */
    @ColumnInfo(name = "addSource")
    private String addSource;


    public void setAddSource(String addSource) {
        this.addSource = addSource;
    }

    public String getAddSource() {
        return  addSource;
    }


    /**
     * 添加词，打招呼语
     */
    @ColumnInfo(name = "addWording")
    private String addWording;

    public void setAddWording(String addWording) {
        this.addWording = addWording;
    }

    public String getAddWording() {
        return addWording;
    }

    /**
     * isSender 好友申请发送者
     */
    @ColumnInfo(name = "isSender")
    private boolean isSender ;

    public boolean isSender() {
        return isSender;
    }

    public void setSender(boolean sender) {
        isSender = sender;
    }

    @Override
    public int compareTo(FriendApplicationLocalRecord other) {
        int order =  (int)(this.createdAt / 1000 - other.createdAt / 1000);

        return order;
    }

}
