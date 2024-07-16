package com.example.base.database.friend;

import android.content.Context;
import android.text.TextUtils;

import androidx.room.Room;

import com.example.base.base.App;
import com.example.base.database.friend.dao.FriendApplicationDao;
import com.example.base.database.friend.dao.FriendInfoDao;
import com.example.base.database.friend.dao.FriendStateV2Dao;
import com.example.base.database.friend.database.FriendInfoDataBase;
import com.example.base.database.friend.entity.FriendApplicationLocalRecord;
import com.example.base.database.friend.entity.ContactItemBean;
import com.example.base.database.friend.entity.FriendStateV2;

import java.util.List;

/**
 * Desc:
 * <p>
 * Author: li yi
 */
public class FriendDataBaseUtils {
    private static FriendDataBaseUtils sInstance;
    private FriendInfoDataBase dataBase;

    private final String TAG = this.getClass().getSimpleName();

    public FriendInfoDataBase getDataBase() {
        initDb();
        return dataBase;
    }

    private FriendInfoDao dao;

    private FriendApplicationDao friendApplicationDao;

    private FriendStateV2Dao friendStateV2Dao;



    private final int groupType = 1000;


    public static FriendDataBaseUtils getInstance() {
        if (sInstance == null) {
            synchronized (FriendDataBaseUtils.class) {
                if (sInstance == null) {
                    sInstance = new FriendDataBaseUtils();
                    sInstance.initDb();
                }
            }
        }
        return sInstance;
    }

    /**
     * 初始化数据库
     */
    private void initDb() {
        if (dao != null) {
            return;
        }
        Context context = App.getContext();
        if (context == null) {
            return;
        }
        try {
            if (dataBase == null) {
                dataBase = Room.databaseBuilder(context, FriendInfoDataBase.class,
                        "ours_friend").allowMainThreadQueries().fallbackToDestructiveMigration().build();
            }
            dao = dataBase.friendInfoDao();
            friendApplicationDao = dataBase.friendApplicationDao();
            friendStateV2Dao = dataBase.friendStateV2Dao();
        } catch (Exception e) {
        }
    }



    public void runInTransaction(Runnable runnable) {
        initDb();
        dataBase.runInTransaction(runnable);
    }

    /**
     * 插入好友数据
     *
     * @param friendInfo 好友bean
     */
    public void insertFriend(ContactItemBean friendInfo) {
        initDb();
        dao.insert(friendInfo);
    }

    public List<ContactItemBean> queryFriendInfo(List<String> ridStrs) {
        initDb();
        if (dao == null) {
            return null;
        }
        return dao.getFriendInfo(ridStrs);
    }

    /**
     * 查询单个用户
     * ownerId
     * @return 好友
     */
    public ContactItemBean queryFriendInfo(String ridStr) {
        initDb();
        if (dao == null) {
            return null;
        }
        return dao.getFriendInfo(ridStr);
    }

    public void update(ContactItemBean friendInfo) {
        initDb();
        if (dao == null) {
            return;
        }
        dao.update(friendInfo);
    }

    public void setRemarkAndPinyin(String ridStr, String remark, String pingYin) {
        initDb();
        if (dao == null) {
            return;
        }
        ContactItemBean friendInfo = queryFriendInfo(ridStr);
        if(!TextUtils.isEmpty(ridStr)){
            friendInfo.setRemark(remark);
            friendInfo.setBaseIndexPinyin(pingYin);
            dao.update(friendInfo);
        }

    }


    /**
     * 删除好友列表
     */
    public void clearAll() {
        initDb();
        if (dao == null) {
            return;
        }
        dao.clearAll();
    }

    /**
     * 删除单个好友列表
     */
    public void delete(String ridStr) {
        initDb();
        if (dao == null) {
            return;
        }
        dao.delete(ridStr);
    }









    /**
     * 查询好友申请记录
     * @return 场景配置
     */
    public List<FriendApplicationLocalRecord> queryFriendApplicationRecord(String ownerRidStr) {
        initDb();
        if (friendApplicationDao == null) {
            return null;
        }
        return friendApplicationDao.getFriendApplicationInfolist(ownerRidStr);
    }


    public boolean isExsistApplicationFriend(String ownerRidStr,String applicationUserIdStr){
        initDb();
        int num = friendApplicationDao.exsistApplicationFriend(ownerRidStr,applicationUserIdStr);
        if(num > 0){
            return true;
        }
        return false;
    }


    public FriendApplicationLocalRecord queryApplyFriend(String ownerRidStr,String applicationUserIdStr){
        initDb();
        FriendApplicationLocalRecord record = friendApplicationDao.query(ownerRidStr,applicationUserIdStr);
        return record;
    }

    /**
     * 插入好友申请记录
     * @param record
     */
    public void insertFriendApplicationRecord(FriendApplicationLocalRecord... record){
        initDb();
        friendApplicationDao.insert(record);
    }


    public void updateFriendApplyStatus(String ownerIdStr,String ridStr,int status){
        initDb();
        friendApplicationDao.update(ownerIdStr,ridStr,status);
    }

    /**
     * 删除好友申请记录
     * @param record
     */
    public void deleteFriendApplicationRecord(FriendApplicationLocalRecord record){
        initDb();
        friendApplicationDao.delete(record);
    }


    /**
     * 好友状态.
     * @param ownerRidStr
     * @param friendRidStr
     * @param target
     * @return
     */
    public FriendStateV2 queryFriendState(String ownerRidStr, String friendRidStr,int  target){
        initDb();
        FriendStateV2 record = friendStateV2Dao.getFriendStateV2(ownerRidStr,friendRidStr,target);
        return record;
    }

    public boolean isExisistFriendState(String ownerRidStr, String friendRidStr,int  target){

        FriendStateV2 item = queryFriendState(ownerRidStr,friendRidStr,target);

        if(item == null){
            return false;
        }else {
            return true;
        }

    }



    /**
     * 插入好友申请记录
     * @param friendStateV2
     */
    public void insertFriendState(FriendStateV2 friendStateV2){
        initDb();
        FriendStateV2 item = queryFriendState(friendStateV2.getUserId(),friendStateV2.getRidStr(),
                friendStateV2.getUpdateTarget());
        if(item == null){
            friendStateV2Dao.insert(friendStateV2);
        }else {
            //本地存在，无需插入... 无需更新.
        }

    }


    /**
     * 删除好友申请记录
     * @param userId
     * @param friendRidStr
     * @param target 要删除哪种更新
     */
    public void deleteFriendStateV2(String userId,String friendRidStr,int target){
        initDb();
        friendStateV2Dao.delete(userId,friendRidStr,target);
    }





}
