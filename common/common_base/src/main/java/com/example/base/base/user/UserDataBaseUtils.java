package com.example.base.base.user;

import android.content.Context;

import androidx.room.Room;
import com.example.base.base.App;
import com.example.base.base.user.dao.UserInfoDao;
import com.example.base.base.user.database.UserInfoDataBase;
import com.example.base.base.user.entity.UserInfo;
import com.example.base.utils.LogUtils;
import com.example.base.utils.TLog;

import java.util.List;

/**
 * Desc:用户信息
 * <p>
 * Author: li yi
 */
public class UserDataBaseUtils {
    private final String TAG = this.getClass().getSimpleName();
    private static UserDataBaseUtils sInstance;
    private UserInfoDataBase dataBase;

    private UserInfoDao dao;


    public static UserDataBaseUtils getInstance() {
        if (sInstance == null) {
            synchronized (UserDataBaseUtils.class) {
                if (sInstance == null) {
                    sInstance = new UserDataBaseUtils();
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
                dataBase = Room.databaseBuilder(context, UserInfoDataBase.class,
                        "ours_user").allowMainThreadQueries().fallbackToDestructiveMigration().build();
            }
            dao = dataBase.userInfoDao();
        } catch (Exception e) {
        }
    }

    /**
     * 插入好友数据
     *
     * @param userInfo 好友bean
     */
    public void insert(UserInfo userInfo) {

        initDb();
        if (dao == null) {
            return;
        }
        if (dataBase != null) {
            try {
                dataBase.runInTransaction(() -> {
                    dao.delete(userInfo.getRidStr());
                    dao.insert(userInfo);
                });
            } catch (Exception exception) {
                LogUtils.i(TAG,"exception...");
            }
        }
    }

    /**
     * 查询当前用户
     *
     * @return 场景配置
     */
    public UserInfo queryCurrentInfo() {
        initDb();
        if (dao == null) {
            return null;
        }
        return dao.queryCurrentInfo();
    }
    /**
     * 更新uid
     *
     * @return 场景配置
     */
    public int updateUid(String uid) {
        initDb();
        if (dao == null) {
            return 0;
        }
        return dao.updateUid(uid);
    }
    /**
     * 查询用户数量
     *
     * @return 场景配置
     */
    public int queryUserInfoCount() {
        initDb();
        if (dao == null) {
            return 0;
        }
        return dao.getInfoCount();
    }

    /**
     * 查询单个用户
     *
     * @return 好友
     */
    public UserInfo queryUserInfo(String ridStr) {
        initDb();
        if (dao == null) {
            return null;
        }
        return dao.getUserInfo(ridStr);
    }
    /**
     * 查询所有用户
     *
     * @return 好友
     */
    public List<UserInfo> queryUserInfos() {
        initDb();
        if (dao == null) {
            return null;
        }
        return dao.getUserInfos();
    }

    public void update(UserInfo userInfo) {
        initDb();
        if (dao == null) {
            return;
        }
        dao.update(userInfo);
    }

    /**
     * 退出登录
     */
    public void logout() {

        initDb();
        if (dao == null) {
            return;
        }
        dao.logout();
    }


    /**
     * 清空列表
     */
    public void clear() {
        initDb();
        if (dao == null) {
            return;
        }
        dao.deleteAll();
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
}
