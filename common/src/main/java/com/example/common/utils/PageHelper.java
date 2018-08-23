package com.example.common.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.orhanobut.logger.Logger;

public class PageHelper {

    public static enum MediaType {
        IMAGE("image/*"),
        AUDIO("audio/*"),
        VIDEO("video/*"),
        AUDIO_VIDEO("audio/*;video/*"),
        ALL("*/*");

        String value;

        private MediaType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 打开文件选择器
     *
     * @param activity
     * @param pageTitle   文件选择题标题
     * @param requestCode 请求码
     * @param mediaType   文件类型<strong> {@link MediaType} </strong>
     * @throws ActivityNotFoundException
     */
    public static void showFileChooser(Activity activity, String pageTitle,
                                       int requestCode, MediaType mediaType) throws ActivityNotFoundException {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(mediaType.getValue());
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(
                Intent.createChooser(intent, pageTitle), requestCode);
    }

    /**
     * 跳转到浏览器
     *
     * @param context
     * @param url
     */
    public static void openBrowser(Context context, String url) {
        try {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            Logger.e("open browser error:%s", e);
        }
    }

}