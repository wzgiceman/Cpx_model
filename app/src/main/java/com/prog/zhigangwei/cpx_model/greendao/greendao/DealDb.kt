package com.prog.zhigangwei.cpx_model.greendao.greendao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.base.library.rxRetrofit.RxRetrofitApp
import com.prog.zhigangwei.cpx_model.dao.DaoMaster
import com.prog.zhigangwei.cpx_model.dao.DbBeanDao


/**
 * Describe:数据本地数据处理类
 *
 *
 * Created by zhigang wei
 * on 2017/6/4.
 *
 *
 * Company :cpx
 */
class DealDb private constructor() {
    private var openHelper: DaoMaster.DevOpenHelper? = null
    private val context: Context


    /**
     * 获取可读数据库
     */
    private val readableDatabase: SQLiteDatabase
        get() {
            if (openHelper == null) {
                openHelper = DaoMaster.DevOpenHelper(context, dbName)
            }
            return openHelper!!.readableDatabase
        }

    /**
     * 获取可写数据库
     */
    private val writableDatabase: SQLiteDatabase
        get() {
            if (openHelper == null) {
                openHelper = DaoMaster.DevOpenHelper(context, dbName)
            }
            return openHelper!!.writableDatabase
        }


    init {
        context = RxRetrofitApp.getApplication()
        openHelper = DaoMaster.DevOpenHelper(context, dbName)
    }


    /**
     * add
     *
     * @param info 信息源
     */
    fun insert(info: DbBean?) {
        if (info == null) return
        val daoMaster = DaoMaster(writableDatabase)
        val daoSession = daoMaster.newSession()
        val dao = daoSession.dbBeanDao
        dao.insertOrReplace(info)
    }

    /**
     * update
     *
     * @param info 信息源
     */
    fun update(info: DbBean?) {
        if (info == null) return
        val daoMaster = DaoMaster(writableDatabase)
        val daoSession = daoMaster.newSession()
        val dao = daoSession.dbBeanDao
        dao.update(info)
    }


    /**
     * add or update
     *
     * @param info 信息源
     */
    fun savrOrUpdate(info: DbBean?) {
        if (info == null) return
        val daoMaster = DaoMaster(writableDatabase)
        val daoSession = daoMaster.newSession()
        val dao = daoSession.dbBeanDao
        dao.insertOrReplace(info)
    }


    /**
     * del
     *
     * @param id
     */
    fun delete(id: Long?): Boolean {
        try {
            val daoMaster = DaoMaster(writableDatabase)
            val daoSession = daoMaster.newSession()
            val dao = daoSession.dbBeanDao
            dao.deleteByKey(id!!)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * del all
     */
    fun deleteAll(): Boolean {
        try {
            val daoMaster = DaoMaster(writableDatabase)
            val daoSession = daoMaster.newSession()
            val dao = daoSession.dbBeanDao
            dao.deleteAll()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }


    /**
     * queryBy/myId
     *
     * @param myId
     * @return
     */
    fun queryBy(myId: String): DbBean? {
        try {
            val daoMaster = DaoMaster(readableDatabase)
            val daoSession = daoMaster.newSession()
            val dao = daoSession.dbBeanDao
            return dao.queryBuilder().where(DbBeanDao.Properties.MyId.eq(myId)).unique()
        } catch (e: SQLiteException) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * queryAll
     *
     *
     * 大数据查询需要切换到子线程中（Rxjava），默认greendao在主线程中处理
     *
     * @return
     */
    fun queryAll(): List<DbBean> {
        try {
            val daoMaster = DaoMaster(readableDatabase)
            val daoSession = daoMaster.newSession()
            val dao = daoSession.dbBeanDao
            /*小数据主线程中处理*/
            return dao.queryBuilder().list()
            /*大数据处理需要切换线程的时候，子线程需要自己通过rxjava在外部自己实现，这里只是切换线程*/
            //            return dao.queryBuilder().build().forCurrentThread().list();
        } catch (e: SQLiteException) {
            e.printStackTrace()
            return emptyList()
        }

    }

    companion object {
        @Volatile
        private var db: DealDb? = null
        private val dbName = "grendao"


        /**
         * 获取单例
         *
         * @return
         */
        val instance: DealDb
            get() {
                if (db == null) {
                    synchronized(DealDb::class.java) {
                        if (db == null) {
                            db = DealDb()
                        }
                    }
                }
                return db!!
            }
    }

}
