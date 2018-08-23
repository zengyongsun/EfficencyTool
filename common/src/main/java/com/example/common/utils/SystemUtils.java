package com.example.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * SystemUtils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-5-15
 */
public class SystemUtils {

	/**
	 * recommend default thread pool size according to system available
	 * processors, {@link #getDefaultThreadPoolSize()}
	 **/
	public static final int DEFAULT_THREAD_POOL_SIZE = getDefaultThreadPoolSize();

	public static String IMEI = "";

	private SystemUtils() {
		throw new AssertionError();
	}

	/**
	 * get recommend default thread pool size
	 * 
	 * @return if 2 * availableProcessors + 1 less than 8, return it, else
	 *         return 8;
	 * @see {@link #getDefaultThreadPoolSize(int)} max is 8
	 */
	public static int getDefaultThreadPoolSize() {
		return getDefaultThreadPoolSize(8);
	}

	/**
	 * get recommend default thread pool size
	 * 
	 * @param max
	 * @return if 2 * availableProcessors + 1 less than max, return it, else
	 *         return max;
	 */
	public static int getDefaultThreadPoolSize(int max) {
		int availableProcessors = 2 * Runtime.getRuntime()
				.availableProcessors() + 1;
		return availableProcessors > max ? max : availableProcessors;
	}

	// 获取手机型号
	public static String getSystemModle() {
		return android.os.Build.MODEL;
	}

	// 获取SDK版本
	public static String getSdkVersion() {
		return android.os.Build.VERSION.SDK;
	}

	// 获取系统版本
	public static String getSystemVersion() {
		return android.os.Build.VERSION.RELEASE;
	}

	// 获取软件版本名称
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			versionName = packageInfo.versionName;
			if (TextUtils.isEmpty(versionName)) {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionName;
	}

	/**
	 * 获取软件版本号
	 * 
	 * @param context
	 * @return
	 */
	public static int getAppVersionCode(Context context) {
		int versionCode = 0;
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			versionCode = packageInfo.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 获取IMEI
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		String imei = "";

		if(TextUtils.isEmpty(IMEI)){
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			imei = tm.getDeviceId();
			if (StringUtils.isBlank(imei)) {
				imei = tm.getSubscriberId();
			}
			IMEI = imei;
		}else {
			imei = IMEI;
		}

		if (StringUtils.isBlank(imei)) {
			imei = android.provider.Settings.Secure.getString(
					context.getContentResolver(),
					android.provider.Settings.Secure.ANDROID_ID);
		}
		if(StringUtils.isBlank(imei)){
			imei = MacUtil.getLocalMacAddress(context);
		}
		return imei;
	}

	public static  int getStatusBarHeight(Activity activity){
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		return statusBarHeight;
	}

}
