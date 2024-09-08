package com.example.base.database.friend;

import android.content.Context;

import androidx.room.Room;

import com.example.base.base.App;
import com.example.base.base.User;
import com.example.base.database.friend.dao.FriendStateDao;
import com.example.base.database.friend.database.FriendStateDataBase;
import com.example.base.database.friend.entity.FriendState;


/**
 * Desc:
 * <p>
 * Author: li yi
 */
public class FriendStateDataBaseUtils {
    private static FriendStateDataBaseUtils sInstance;
    private FriendStateDataBase dataBase;

    private FriendStateDao dao;


    public static FriendStateDataBaseUtils getInstance() {
        if (sInstance == null) {
            synchronized (FriendStateDataBaseUtils.class) {
                if (sInstance == null) {
                    sInstance = new FriendStateDataBaseUtils();
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
                dataBase = Room.databaseBuilder(context, FriendStateDataBase.class,
                        "ours_friend_state").allowMainThreadQueries().fallbackToDestructiveMigration().build();
            }
            dao = dataBase.friendStateDao();
        } catch (Exception e) {
        }
    }

    /**
     * 插入好友状态数据
     *
     * @param friendState 好友bean
     */
    public void insertFriendState(FriendState friendState) {
        initDb();
        if (dao == null) {
            return;
        }
        if (dataBase != null) {
            try {
                dataBase.runInTransaction(() -> {
                    dao.delete(User.INSTANCE.getUid(), friendState.getRid());
                    dao.insert(friendState);
                });
            } catch (Exception exception) {
            }
        }
    }

    /**
     * 查询单个用户
     *
     * @return 好友
     */
    public FriendState queryUserInfo(String rid) {
        initDb();
        if (dao == null) {
            return null;
        }
        return dao.getUserInfo(User.INSTANCE.getUid(), rid);
    }


    public void update(FriendState friendInfo) {
        initDb();
        if (dao == null) {
            return;
        }
        dao.update(friendInfo);
    }


}
