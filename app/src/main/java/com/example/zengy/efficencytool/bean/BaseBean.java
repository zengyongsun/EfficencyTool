package com.example.zengy.efficencytool.bean;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2018/8/249:56
 * desc   :
 * version: 1.0
 */
public class BaseBean<T> {

    /**
     * {
     * "data": {
     * "collectIds": [],
     * "email": "",
     * "icon": "",
     * "id": 874,
     * "password": "zf19890124127",
     * "token": "",
     * "type": 0,
     * "username": "zengyongsun"
     * },
     * "errorCode": 0,
     * "errorMsg": ""
     * }
     */

    public String errorMsg;

    public int errorCode;

    public T data;

}
