package com.prog.zhigangwei.cpx_model.greendao.greendao;

import com.base.library.rxbus.annotation.Subscribe;
import com.base.library.rxbus.annotation.Tag;
import com.base.library.rxbus.thread.EventThread;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Describe:数据原型
 * <p>
 * Created by zhigang wei
 * on 2018/11/16
 * <p>
 * Company :cpx
 */
@Entity
public class DbBean {
    /*id默认是自增字段不需要处理*/
    @Id
    private Long id;
    private String myId;
    private String name;
    private String mail;





    @Keep
    public DbBean(Long id, String myId, String name, String mail) {
        this.id = id;
        this.myId = myId;
        this.name = name;
        this.mail = mail;
    }

    @Keep
    public DbBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMyId() {
        return this.myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


}
