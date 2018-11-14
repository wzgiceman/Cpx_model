package com.base.library.rxRetrofit.downlaod;


import com.base.library.rxRetrofit.listener.HttpDownOnNextListener;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * apk下载请求数据基础类
 * Created by WZG on 2016/10/20.
 */

@Entity
public class DownInfo {
    @Id
    private Long id;
    /*存储位置*/
    private String savePath;
    /*文件总长度*/
    private Long countLength;
    /*下载长度*/
    private Long readLength;
    /*下载唯一的HttpService*/
    @Transient
    private HttpDownService service;
    /*回调监听*/
    @Transient
    private HttpDownOnNextListener listener;
    /*超时设置 单位：秒*/
    private int connectionTime = 15;
    /*state状态数据库保存*/
    private int downState;
    /*url*/
    private String url;
    /*是否需要实时更新下载进度,避免线程的多次切换*/
    @Transient
    private boolean updateProgress;


    @Keep
    public DownInfo(Long id, String savePath, Long countLength, Long readLength,
                    int connectionTime, int downState, String url) {
        this.id = id;
        this.savePath = savePath;
        this.countLength = countLength;
        this.readLength = readLength;
        this.connectionTime = connectionTime;
        this.downState = downState;
        this.url = url;
    }

    @Keep
    public DownInfo() {
        setId(System.currentTimeMillis());
        setReadLength(0l);
        setCountLength(0l);
        setDownState(DownState.START.getState());
    }

    public DownState getState() {
        switch (getDownState()) {
            case 0:
                return DownState.START;
            case 1:
                return DownState.DOWN;
            case 2:
                return DownState.PAUSE;
            case 3:
                return DownState.STOP;
            case 4:
                return DownState.ERROR;
            case 5:
            default:
                return DownState.FINISH;
        }
    }

    public boolean isUpdateProgress() {
        return updateProgress;
    }

    public void setUpdateProgress(boolean updateProgress) {
        this.updateProgress = updateProgress;
    }

    public void setState(DownState state) {
        setDownState(state.getState());
    }


    public int getDownState() {
        return downState;
    }

    public void setDownState(int downState) {
        this.downState = downState;
    }

    public HttpDownOnNextListener getListener() {
        return listener;
    }

    public void setListener(HttpDownOnNextListener listener) {
        this.listener = listener;
    }

    public HttpDownService getService() {
        return service;
    }

    public void setService(HttpDownService service) {
        this.service = service;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }


    public Long getCountLength() {
        return countLength;
    }

    public void setCountLength(Long countLength) {
        this.countLength = countLength;
    }


    public Long getReadLength() {
        return readLength;
    }

    public void setReadLength(Long readLength) {
        this.readLength = readLength;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getConnectionTime() {
        return this.connectionTime;
    }

    public void setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
    }
}
