package com.base.library.rxRetrofit.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.base.dao.CookieResultDao;
import com.base.dao.DaoMaster;
import com.base.dao.DaoSession;
import com.base.library.rxRetrofit.RxRetrofitApp;
import com.base.library.rxRetrofit.http.cookie.CookieResult;

import java.util.List;


/**
 * 数据缓存
 * 数据库工具类-geendao运用
 * Created by WZG on 2016/10/25.
 */

public class CookieDbUtil {
    private final static String dbName = "lib";
    private static CookieDbUtil db;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;


    public CookieDbUtil() {
        context = RxRetrofitApp.getApplication();
        openHelper = new DaoMaster.DevOpenHelper(context, dbName);
    }


    /**
     * 获取单例
     *
     * @return
     */
    public static CookieDbUtil getInstance() {
        if (db == null) {
            synchronized (CookieDbUtil.class) {
                if (db == null) {
                    db = new CookieDbUtil();
                }
            }
        }
        return db;
    }


    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }


    public void saveCookie(CookieResult info) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResultDao downInfoDao = daoSession.getCookieResultDao();
        downInfoDao.insert(info);
    }

    public void updateCookie(CookieResult info) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResultDao downInfoDao = daoSession.getCookieResultDao();
        downInfoDao.update(info);
    }

    public void delete() {
        try {
            DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            CookieResultDao downInfoDao = daoSession.getCookieResultDao();
            downInfoDao.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCookie(Long key) {
        try {
            DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            CookieResultDao downInfoDao = daoSession.getCookieResultDao();
            downInfoDao.deleteByKey(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CookieResult queryCookieBy(String url) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResultDao downInfoDao = daoSession.getCookieResultDao();
        return downInfoDao.queryBuilder()
                .where(CookieResultDao.Properties.Url.eq(url))
                .unique();
    }

    public List<CookieResult> queryCookieAll() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResultDao downInfoDao = daoSession.getCookieResultDao();
        return downInfoDao.queryBuilder().list();
    }
}
