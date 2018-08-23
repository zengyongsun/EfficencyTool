/*
 * Copyright (C) 2013  WhiteCat 白猫 (www.thinkandroid.cn)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.common.netstatus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.common.packaging.LoggerUtil;
import com.example.common.utils.NetWorkUtil;

import java.util.ArrayList;

/**
 * @author 白猫
 * @version V1.2
 * @Title NetworkStateReceiver
 * @Package com.ta.util.netstate
 * @Description 是一个检测网络状态改变的，需要配置 <receiver
 * android:name="com.ta.util.netstate.TANetworkStateReceiver" >
 * <intent-filter> <action
 * android:name="android.net.conn.CONNECTIVITY_CHANGE" /> <action
 * android:name="android.gzcpc.conn.CONNECTIVITY_CHANGE" />
 * </intent-filter> </receiver>
 * <p/>
 * 需要开启权限 <uses-permission
 * android:name="android.permission.CHANGE_NETWORK_STATE" />
 * <uses-permission
 * android:name="android.permission.CHANGE_WIFI_STATE" />
 * <uses-permission
 * android:name="android.permission.ACCESS_NETWORK_STATE" />
 * <uses-permission
 * android:name="android.permission.ACCESS_WIFI_STATE" />
 * @date 2013-5-5 下午 22:47
 */
public class NetworkStateReceiver extends BroadcastReceiver {
    private static Boolean networkAvailable = false;
    private static NetWorkUtil.NetType netType;
    private static ArrayList<NetChangeObserver> netChangeObserverArrayList = new ArrayList<NetChangeObserver>();
    private final static String ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    public final static String TA_ANDROID_NET_CHANGE_ACTION = "ta.android.net.conn.CONNECTIVITY_CHANGE";
    private static BroadcastReceiver receiver;

    private static BroadcastReceiver getReceiver() {
        if (receiver == null) {
            receiver = new NetworkStateReceiver();
        }
        return receiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        receiver = NetworkStateReceiver.this;
        if (intent == null) {
            return;
        }
        if (ANDROID_NET_CHANGE_ACTION.equalsIgnoreCase(intent.getAction())
                || TA_ANDROID_NET_CHANGE_ACTION.equalsIgnoreCase(
                intent.getAction())) {
            LoggerUtil.i("网络状态改变.");
            if (!NetWorkUtil.isConn(context)) {
                LoggerUtil.i("没有网络连接.");
                networkAvailable = false;
            } else {
                LoggerUtil.i("网络连接成功.");
                netType = NetWorkUtil.getAPNType(context);
                networkAvailable = true;
            }
            notifyObserver();
        }
    }

    /**
     * 注册网络状态广播
     *
     * @param mContext
     */
    public static void registerNetworkStateReceiver(Context mContext) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(TA_ANDROID_NET_CHANGE_ACTION);
        filter.addAction(ANDROID_NET_CHANGE_ACTION);
        mContext.getApplicationContext()
                .registerReceiver(getReceiver(), filter);
    }

    /**
     * 检查网络状态
     *
     * @param mContext
     */
    public static void checkNetworkState(Context mContext) {
        Intent intent = new Intent();
        intent.setAction(TA_ANDROID_NET_CHANGE_ACTION);
        mContext.sendBroadcast(intent);
    }

    /**
     * 注销网络状态广播
     *
     * @param mContext
     */
    public static void unRegisterNetworkStateReceiver(Context mContext) {
        if (receiver != null) {
            try {
                mContext.getApplicationContext().unregisterReceiver(receiver);
            } catch (Exception e) {
                LoggerUtil.i("TANetworkStateReceiver:%s", e.getMessage());
            }
        }

    }

    /**
     * 获取当前网络状态，true为网络连接成功，否则网络连接失败
     *
     * @return
     */
    public static Boolean isNetworkAvailable() {
        return networkAvailable;
    }

    public static NetWorkUtil.NetType getAPNType() {
        return netType;
    }

    private void notifyObserver() {

        for (int i = 0; i < netChangeObserverArrayList.size(); i++) {
            NetChangeObserver observer = netChangeObserverArrayList.get(i);
            if (observer != null) {
                if (isNetworkAvailable()) {
                    observer.onConnect(netType);
                } else {
                    observer.onDisConnect();
                }
            }
        }

    }

    /**
     * 注册网络连接观察者
     *
     * @param observer
     */
    public static void registerObserver(NetChangeObserver observer) {
        if (netChangeObserverArrayList == null) {
            netChangeObserverArrayList = new ArrayList<NetChangeObserver>();
        }
        //避免重复添加监听
        if(netChangeObserverArrayList.contains(observer)){
            return;
        }
        netChangeObserverArrayList.add(observer);
    }

    /**
     * 注销网络连接观察者
     *
     * @param observer
     */
    public static void removeRegisterObserver(NetChangeObserver observer) {
        if (netChangeObserverArrayList != null) {
            netChangeObserverArrayList.remove(observer);
        }
    }

}