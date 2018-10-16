package com.base.library.retrofit_rx.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.base.dao.CookieResulteDao;
import com.base.dao.DaoMaster;
import com.base.dao.DaoSession;
import com.base.library.retrofit_rx.RxRetrofitApp;
import com.base.library.retrofit_rx.http.cookie.CookieResulte;

import java.util.List;


/**
 * 数据缓存
 * 数据库工具类-geendao运用
 * Created by WZG on 2016/10/25.
 */

public class CookieDbUtil {
    private static CookieDbUtil db;
    private final static String dbName = "lib";
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


    public void saveCookie(CookieResulte info) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResulteDao downInfoDao = daoSession.getCookieResulteDao();
        downInfoDao.insert(info);
    }

    public void updateCookie(CookieResulte info) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResulteDao downInfoDao = daoSession.getCookieResulteDao();
        downInfoDao.update(info);
    }

    public void delete() {
        try {
            DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            CookieResulteDao downInfoDao = daoSession.getCookieResulteDao();
            downInfoDao.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCookie(Long key) {
        try {
            DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            CookieResulteDao downInfoDao = daoSession.getCookieResulteDao();
            downInfoDao.deleteByKey(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CookieResulte queryCookieBy(String url) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResulteDao downInfoDao = daoSession.getCookieResulteDao();
        return downInfoDao.queryBuilder()
                .where(CookieResulteDao.Properties.Url.eq(url))
                .unique();
    }

    public List<CookieResulte> queryCookieAll() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResulteDao downInfoDao = daoSession.getCookieResulteDao();
        return downInfoDao.queryBuilder().list();
    }
}
