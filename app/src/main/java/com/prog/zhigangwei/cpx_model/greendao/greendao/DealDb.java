package com.prog.zhigangwei.cpx_model.greendao.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.base.library.rxRetrofit.RxRetrofitApp;
import com.prog.zhigangwei.cpx_model.dao.DaoMaster;
import com.prog.zhigangwei.cpx_model.dao.DaoSession;
import com.prog.zhigangwei.cpx_model.dao.DbBeanDao;

import java.util.List;


/**
 * Describe:数据本地数据处理类
 * <p>
 * Created by zhigang wei
 * on 2017/6/4.
 * <p>
 * Company :cpx
 */
public class DealDb {
    private static volatile DealDb db;
    private final static String dbName = "grrendao";
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;


    private DealDb() {
        context = RxRetrofitApp.getApplication();
        openHelper = new DaoMaster.DevOpenHelper(context, dbName);
    }


    /**
     * 获取单例
     *
     * @return
     */
    public static DealDb getInstance() {
        if (db == null) {
            synchronized (DealDb.class) {
                if (db == null) {
                    db = new DealDb();
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


    /**
     * add
     *
     * @param info 信息源
     */
    public void insert(DbBean info) {
        if (info == null) return;
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DbBeanDao dao = daoSession.getDbBeanDao();
        dao.insertOrReplace(info);
    }

    /**
     * update
     *
     * @param info 信息源
     */
    public void update(DbBean info) {
        if (info == null) return;
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DbBeanDao dao = daoSession.getDbBeanDao();
        dao.update(info);
    }


    /**
     * add or update
     *
     * @param info 信息源
     */
    public void savrOrUpdate(DbBean info) {
        if (info == null) return;
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DbBeanDao dao = daoSession.getDbBeanDao();
        dao.insertOrReplace(info);
    }


    /**
     * del
     *
     * @param id
     */
    public void delete(Long id) {
        try{
            DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            DbBeanDao dao = daoSession.getDbBeanDao();
            dao.deleteByKey(id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * del all
     */
    public void deleteAll() {
        try{
            DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            DbBeanDao dao = daoSession.getDbBeanDao();
            dao.deleteAll();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * queryBy/myId
     *
     * @param myId
     * @return
     */
    public DbBean queryBy(String myId) {
        try {
            DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            DbBeanDao dao = daoSession.getDbBeanDao();
            return dao.queryBuilder().where(DbBeanDao.Properties.MyId.eq(myId)).unique();
        } catch (SQLiteException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * queryAll
     * <p>
     * 大数据查询需要切换到子线程中（Rxjava），默认greendao在主线程中处理
     *
     * @return
     */
    public List<DbBean> queryAll() {
        try {
            DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
            DaoSession daoSession = daoMaster.newSession();
            DbBeanDao dao = daoSession.getDbBeanDao();
            /*小数据主线程中处理*/
            return dao.queryBuilder().list();
            /*大数据处理需要切换线程的时候，子线程需要自己通过rxjava在外部自己实现，这里只是切换线程*/
//            return dao.queryBuilder().build().forCurrentThread().list();
        } catch (SQLiteException e) {
            e.printStackTrace();
            return null;
        }
    }

}
