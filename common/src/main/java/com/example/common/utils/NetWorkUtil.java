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
package com.example.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author 猫
 * @version V1.0
 * @Title NetWorkUtil
 * @Package com.ta.util.netstate
 * @Description 是检测网络 一个工具包
 * @date 2013-1-22 下午 9:35
 */
public class NetWorkUtil {

    public static final String NETWORK_TYPE_WIFI = "wifi";
    public static final String NETWORK_TYPE_3G = "3g";
    public static final String NETWORK_TYPE_2G = "2g";
    public static final String NETWORK_TYPE_WAP = "wap";
    public static final String NETWORK_TYPE_UNKNOWN = "unknown";
    public static final String NETWORK_TYPE_DISCONNECT = "disconnect";

    /**
     * 未知网络
     */
    public static final int TYPE_UNKNOW = -1;
    /**
     * WIFI网络
     */
    public static final int TYPE_WIFI = 1;
    /**
     * 蜂窝网络
     */
    public static final int TYPE_MOBILE = 2;

    public static enum NetType {
        wifi, CMNET, CMWAP, noneNet
    }

    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isConn(Context context) {
        boolean bisConnFlag = false;
        ConnectivityManager conManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = conManager.getActiveNetworkInfo();
        if (network != null) {
            bisConnFlag = conManager.getActiveNetworkInfo().isAvailable();
        }
        return bisConnFlag;
    }

    /**
     * 判断WIFI网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null && !mWiFiNetworkInfo.getState().name().equals("DISCONNECTED")) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean canMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断是否是MOBILE网络
     *
     * @param context
     * @return
     */

    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager == null) {
                return false;
            }
            NetworkInfo ni = mConnectivityManager.getActiveNetworkInfo();
            if (ni != null) {
                if (ni.getType() == ConnectivityManager.TYPE_WIFI) {
                    return false;
                } else if (ni.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return true;
                }
            }
        }
        return false;
    }

//	/**
//	 * 返回当前网络类型
//	 * @param context
//	 * @return
//	 */
//	public static int getNetworkType(final Context context) {
//		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//		if (cm == null) return TYPE_UNKNOW;
//		NetworkInfo ni = cm.getActiveNetworkInfo();
//		if (ni != null) {
//			if (ni.getType() == ConnectivityManager.TYPE_WIFI) {
//				return TYPE_WIFI;
//			} else if (ni.getType() == ConnectivityManager.TYPE_MOBILE) {
//				return TYPE_MOBILE;
//			}
//		}
//		return TYPE_UNKNOW;
//	}

    /**
     * @param context
     * @return
     * @author 猫
     * <p/>
     * 获取当前的网络状态 -1：没有网络 1：WIFI网络2：wap 网络3：net网络
     */
    public static NetType getAPNType(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NetType.noneNet;
        }
        int nType = networkInfo.getType();

        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if (!TextUtils.isEmpty(networkInfo.getExtraInfo()) && networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                return NetType.CMNET;
            } else {
                return NetType.CMWAP;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            return NetType.wifi;
        }
        return NetType.noneNet;

    }

    /**
     * Get network type
     *
     * @param context
     * @return
     */
    public static String getNetWorkType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        String type = NETWORK_TYPE_DISCONNECT;
        if (manager == null
                || (networkInfo = manager.getActiveNetworkInfo()) == null) {
            return type;
        }
        if (networkInfo.isConnected()) {
            String typeName = networkInfo.getTypeName();
            if ("WIFI".equalsIgnoreCase(typeName)) {
                type = NETWORK_TYPE_WIFI;
            } else if ("MOBILE".equalsIgnoreCase(typeName)) {
                String proxyHost = android.net.Proxy.getDefaultHost();
                type = TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork(context) ? NETWORK_TYPE_3G
                        : NETWORK_TYPE_2G)
                        : NETWORK_TYPE_WAP;
            } else {
                type = NETWORK_TYPE_UNKNOWN;
            }
        }
        return type;
    }

    /**
     * Whether is fast mobile network
     *
     * @param context
     * @return
     */
    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            return false;
        }

        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false;
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false;
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false;
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true;
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true;
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false;
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true;
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true;
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true;
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true;
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }

    public static String getConnectWifiSsid(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        Log.d("wifiInfo", wifiInfo.toString());
        Log.d("SSID", wifiInfo.getSSID());
        return wifiInfo.getSSID();
    }
}
