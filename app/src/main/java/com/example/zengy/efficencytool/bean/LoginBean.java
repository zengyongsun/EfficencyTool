package com.example.zengy.efficencytool.bean;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2018/8/2415:47
 * desc   :
 * version: 1.0
 */
public class LoginBean extends BaseBean<LoginBean> {
    @Override
    public String toString() {
        return "LoginBean{" +
                "collectIds='" + collectIds + '\'' +
                ", email='" + email + '\'' +
                ", icon='" + icon + '\'' +
                ", id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", type=" + type +
                ", username='" + username + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }

    /**
     * "collectIds": [],
     * "email": "",
     * "icon": "",
     * "id": 874,
     * "password": "zf19890124127",
     * "token": "",
     * "type": 0,
     * "username": "zengyongsun"
     */
    public String collectIds;
    public String email;
    public String icon;
    public String id;
    public String password;
    public String token;
    public int type;
    public String username;

}
