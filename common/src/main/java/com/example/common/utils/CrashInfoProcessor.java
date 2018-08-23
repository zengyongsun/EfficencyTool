package com.example.common.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;

import com.orhanobut.logger.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * @author Kim.H 处理异常信息
 */
public class CrashInfoProcessor {

    /**
     * @param ctx 手机设备相关信息
     */
    public static HashMap<String, String> collectDeviceInfo(Context ctx) {
        HashMap<String, String> infos = new HashMap<String, String>();
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
                infos.put("crashTime", TimeUtils.getTimeInStrByFormater(
                        new Date(), TimeUtils.YEAR_MONTH_DAY));
            }
        } catch (NameNotFoundException e) {
            Logger.e("an error occured when collect package info: %s", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Logger.d(field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Logger.e("an error occured when collect crash info: %s", e);
            }
        }
        return infos;
    }

    /**
     * @param ex 将崩溃写入文件系统
     */
    public static String writeCrashInfoToFile(Throwable ex,
                                              HashMap<String, String> infos) {
        StringBuffer sb = new StringBuffer();
        for (Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        // 这里把刚才异常堆栈信息写入SD卡的Log日志里面
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            String sdcardPath = Environment.getExternalStorageDirectory()
                    .getPath();
            String filePath = sdcardPath + "/cym/crash/";
            return writeLog(sb.toString(), filePath);
        }
        return null;
    }

    /**
     * @param log
     * @param name
     * @return 返回写入的文件路径 写入Log信息的方法，写入到SD卡里面
     */
    private static String writeLog(String log, String name) {
        CharSequence timestamp = new Date().toString().replace(" ", "");
        timestamp = "crash";
        String filename = name + timestamp + ".log";
        File file = new File(filename);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            Logger.d("写入到SD卡");
            // FileOutputStream stream = new FileOutputStream(new
            // File(filename));
            // OutputStreamWriter output = new OutputStreamWriter(stream);
            file.createNewFile();
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            // 写入相关Log到文件
            bw.write(log);
            bw.newLine();
            bw.close();
            fw.close();
            return filename;
        } catch (IOException e) {
            Logger.e("an error occured while writing file: %s", e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 重启应用
     *
     * @param context
     * @param clazz
     */
    public static void restart(Context context, Class<?> clazz) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Logger.e("error: %s", e);
        }
        Intent intent = new Intent(context.getApplicationContext(), clazz);
        PendingIntent restartIntent = PendingIntent.getActivity(
                context.getApplicationContext(), 0, intent,
                Intent.FLAG_ACTIVITY_NEW_TASK);
        // 退出程序
        AlarmManager mgr = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
                restartIntent); // 1秒钟后重启应用
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
