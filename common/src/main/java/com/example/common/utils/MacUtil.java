package com.example.common.utils;

import android.content.Context;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * mac地址工具类
 * @author Kim.H
 *
 */
public class MacUtil {

	/**
	 * 获得手机的mac地址
	 * 
	 * @param context
	 * @return
	 */
	public static String getLocalMacAddress(Context context) {
		/*WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();*/
		try {
			List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface nif : all) {
				if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

				byte[] macBytes = nif.getHardwareAddress();
				if (macBytes == null) {
					return "";
				}

				StringBuilder res1 = new StringBuilder();
				for (byte b : macBytes) {
					res1.append(String.format("%02X:",b));
				}

				if (res1.length() > 0) {
					res1.deleteCharAt(res1.length() - 1);
				}
				return res1.toString();
			}
		} catch (Exception ex) {
		}
		return "02:00:00:00:00:00";
	}
}
