package com.example.common.utils;

import android.content.Context;

import java.util.HashMap;

/**
 * 获取系统信息
 * @author Kim.H
 *
 */
public class Agent {

	public static final String AGENT_APP_VERSIONNAME = "appVersionName";
	public static final String AGENT_APP_VERSIONCODE = "appVersionCode";
	public static final String AGENT_OS_MODEL = "OSModel";
	public static final String AGENT_OS_SDKVERSION = "OSSDKVersion";
	public static final String AGENT_OS_VRESION_RELEASE = "OSSDKVersionRelease";
	public static final String AGENT_IMEI = "imei";

	private static final Agent INSTANCE = new Agent();

	private Context context;

	private Agent() {
	}

	public static Agent getInstance() {
		return INSTANCE;
	}

	public void init(Context context) {
		INSTANCE.context = context;
	}

	/**
	 * 获取系统信息，用于构建agent<br/>
	 * appVersionName 应用名称<br/>
	 * appVersionCode 应用版本号<br/>
	 * OSModel 操作系统<br/>
	 * OSSDKVersion 操作系统版本<br/>
	 * OSSDKVersionRelease<br/>
	 * imei {@link HashMap}
	 * @return
	 */
	public HashMap<String, String> getAgent() {
		HashMap<String, String> agentMap = new HashMap<String, String>();
		agentMap.put(AGENT_APP_VERSIONNAME, SystemUtils.getAppVersionName(context));
		agentMap.put(AGENT_APP_VERSIONCODE, String.valueOf(SystemUtils.getAppVersionCode(context)));
		agentMap.put(AGENT_OS_MODEL, SystemUtils.getSystemModle());
		agentMap.put(AGENT_OS_SDKVERSION, SystemUtils.getSdkVersion());
		agentMap.put(AGENT_OS_VRESION_RELEASE, SystemUtils.getSystemVersion());
		agentMap.put(AGENT_IMEI, SystemUtils.getIMEI(context));
		return agentMap;
	}
}
