package com.example.base.utils;

import android.content.Context;

import androidx.room.Room;

import com.example.base.base.App;
import com.example.base.database.growth.dao.RoleGrowthInfoDao;
import com.example.base.database.growth.database.RoleGrowthInfoDataBase;
import com.example.base.database.growth.entity.RoleGrowthInfo;

import java.util.List;


/**
 * 用户经验值
 *
 */
public class UserGrowthInfoDataBaseUtils {
    private static UserGrowthInfoDataBaseUtils sInstance;
    private RoleGrowthInfoDataBase dataBase;

    private RoleGrowthInfoDao dao;


    public static UserGrowthInfoDataBaseUtils getInstance() {
        if (sInstance == null) {
            synchronized (UserGrowthInfoDataBaseUtils.class) {
                if (sInstance == null) {
                    sInstance = new UserGrowthInfoDataBaseUtils();
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
                dataBase = Room.databaseBuilder(context, RoleGrowthInfoDataBase.class,
                        "ours_user_growth_info").allowMainThreadQueries().fallbackToDestructiveMigration().build();
            }
            dao = dataBase.roleGrowthInfoDao();
        } catch (Exception e) {
        }
    }


    public void insertItem(RoleGrowthInfo item) {
        initDb();
        if (dao == null) {
            return;
        }
        if (dataBase != null) {
            try {
                dataBase.runInTransaction(() -> {
                    dao.delete(item.getRid());
                    dao.insert(item);
                });
            } catch (Exception exception) {
            }
        }
    }


    public void update(RoleGrowthInfo info) {
        initDb();
        if (dao == null) {
            return;
        }
        dao.update(info);
    }


    public List<RoleGrowthInfo> getUserGrowthList() {
        initDb();
        if (dao == null) {
            return null;
        }
        return dao.getUserGrowthList();
    }


    public RoleGrowthInfo getRoleGrowthInfo(String rid) {
        initDb();
        if (dao == null) {
            return null;
        }
        return dao.getRoleGrowthInfo(rid);
    }



    /**
     * 删除所有
     */
    public void clear() {
        initDb();
        if (dao == null) {
            return;
        }
        dao.deleteAll();
    }

    /**
     * 删除单个
     */
    public void delete(String rid) {
        initDb();
        if (dao == null) {
            return;
        }
        dao.delete(rid);
    }

}
