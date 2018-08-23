package com.example.common.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * AppUtils
 * <ul>
 * <li>{@link AppUtils#isNamedProcess(Context, String)}</li>
 * </ul>
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-5-07
 */
public class AppUtils {

	private AppUtils() {
		throw new AssertionError();
	}

	/**
	 * whether this process is named with processName
	 * 
	 * @param context
	 * @param processName
	 * @return <ul>
	 *         return whether this process is named with processName
	 *         <li>if context is null, return false</li>
	 *         <li>if {@link ActivityManager#getRunningAppProcesses()} is null,
	 *         return false</li>
	 *         <li>if one process of
	 *         {@link ActivityManager#getRunningAppProcesses()} is equal to
	 *         processName, return true, otherwise return false</li>
	 *         </ul>
	 */
	public static boolean isNamedProcess(Context context, String processName) {
		if (context == null) {
			return false;
		}

		int pid = android.os.Process.myPid();
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> processInfoList = manager
				.getRunningAppProcesses();
		if (processInfoList == null) {
			return true;
		}

		for (RunningAppProcessInfo processInfo : processInfoList) {
			if (processInfo.pid == pid
					&& TextUtils.equals(processName, processInfo.processName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * whether application is in background
	 * <ul>
	 * <li>need use permission android.permission.GET_TASKS in Manifest.xml</li>
	 * </ul>
	 * 
	 * @param context
	 * @return if application is in background return true, otherwise return
	 *         false
	 */

	@SuppressWarnings("deprecation")
	public static boolean isApplicationInBackground(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> taskList = am.getRunningTasks(1);
		if (taskList != null && !taskList.isEmpty()) {
			ComponentName topActivity = taskList.get(0).topActivity;
			if (topActivity != null
					&& !topActivity.getPackageName().equals(
							context.getPackageName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 调用shell命令杀死当前进程
	 */
	public static void killByShell() {
		String killCommand = "kill -9 " + android.os.Process.myPid();
		try {
			Runtime.getRuntime().exec(killCommand);
		} catch (Exception e) {
			Logger.e(e, "AppUtil.killByShell");
		}
	}

	/**
	 * 调用系统Process工具直接杀死进程
	 */
	public static void kill() {
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * 通过ActivityManager管理器重启应用
	 * 
	 * @param context
	 */
	@SuppressWarnings("deprecation")
	public static void restartByPackageName(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		am.restartPackage(context.getPackageName());
	}

	/**
	 * 直接退出应用
	 */
	public static void exit() {
		System.exit(0);
	}

	/**
	 * 重启应用启动页
	 * 
	 * @param context
	 */
	public static void restart(Context context) {
		Intent intent = context.getPackageManager().getLaunchIntentForPackage(
				context.getPackageName());
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}
}
