package com.base.share_data.share_msg;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Describe:共享数据库
 * <p>
 * Created by zhigang wei
 * on 2018/5/28
 * <p>
 * Company :cpx
 */
@Entity
public class ShareData {
    /**
     * 类型定义在ShareSparse中
     */
    @Id
    private String id;
    /**
     * 信息-json数据
     */
    private String msg;


    /**
     * 备用字段
     */
    private String remarkA;
    private String remarkB;
    private String remarkC;
    private String remarkD;
    private String remarkE;
    private String remarkF;
    private String remarkG;
    private String remarkH;
    private String remarkK;
    private String remarkM;

    @Keep
    public ShareData(String id, String msg, String remarkA, String remarkB,
                     String remarkC, String remarkD, String remarkE, String remarkF,
                     String remarkG, String remarkH, String remarkK, String remarkM) {
        this.id = id;
        this.msg = msg;
        this.remarkA = remarkA;
        this.remarkB = remarkB;
        this.remarkC = remarkC;
        this.remarkD = remarkD;
        this.remarkE = remarkE;
        this.remarkF = remarkF;
        this.remarkG = remarkG;
        this.remarkH = remarkH;
        this.remarkK = remarkK;
        this.remarkM = remarkM;
    }

    @Keep
    public ShareData() {
    }

    public ShareData(String id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRemarkA() {
        return this.remarkA;
    }

    public void setRemarkA(String remarkA) {
        this.remarkA = remarkA;
    }

    public String getRemarkB() {
        return this.remarkB;
    }

    public void setRemarkB(String remarkB) {
        this.remarkB = remarkB;
    }

    public String getRemarkC() {
        return this.remarkC;
    }

    public void setRemarkC(String remarkC) {
        this.remarkC = remarkC;
    }

    public String getRemarkD() {
        return this.remarkD;
    }

    public void setRemarkD(String remarkD) {
        this.remarkD = remarkD;
    }

    public String getRemarkE() {
        return this.remarkE;
    }

    public void setRemarkE(String remarkE) {
        this.remarkE = remarkE;
    }

    public String getRemarkF() {
        return this.remarkF;
    }

    public void setRemarkF(String remarkF) {
        this.remarkF = remarkF;
    }

    public String getRemarkG() {
        return this.remarkG;
    }

    public void setRemarkG(String remarkG) {
        this.remarkG = remarkG;
    }

    public String getRemarkH() {
        return this.remarkH;
    }

    public void setRemarkH(String remarkH) {
        this.remarkH = remarkH;
    }

    public String getRemarkK() {
        return this.remarkK;
    }

    public void setRemarkK(String remarkK) {
        this.remarkK = remarkK;
    }

    public String getRemarkM() {
        return this.remarkM;
    }

    public void setRemarkM(String remarkM) {
        this.remarkM = remarkM;
    }

}
