package com.example.common.utils;

import android.content.Context;
import android.os.Environment;

import java.util.Locale;

public class URIUtil {

    public enum Schema {

        UNKOWN(""), HTTPS("https"), HTTP("http"), FILE("file"), ASSETS("assert");

        private String value;

        private Schema(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum MediaType {
        UNKOWN(""), JPEG("JPEG"), JPG("JPG"), PNG("PNG"), GIF("GIF"), ThreeGP(
                "3GP"), MP4("MP4");
        private String value;

        private MediaType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 获取uri的schema类型
     *
     * @param uri
     * @return
     */
    public static Schema getSchema(String uri) {
        if (StringUtils.isEmpty(uri)) {
            return Schema.UNKOWN;
        }

        if (uri.startsWith(Schema.HTTPS.value)) {
            return Schema.HTTPS;
        } else if (uri.startsWith(Schema.HTTP.value)) {
            return Schema.HTTP;
        } else if (uri.startsWith(Schema.FILE.value)) {
            return Schema.FILE;
        } else if (uri.startsWith(Schema.ASSETS.value)) {
            return Schema.ASSETS;
        }

        return Schema.UNKOWN;
    }

    /**
     * 根据uri地址获取媒体类型，如：http://pic2.l99.com/64c/1432458082076_9108pe.gif
     *
     * @param uri
     * @return
     */
    public static MediaType getMediaType(String uri) {
        if (StringUtils.isEmpty(uri)) {
            return MediaType.UNKOWN;
        }
        String mime = new String(uri.substring(uri.lastIndexOf(".") + 1)).toUpperCase(Locale.getDefault()); // 获取后缀

        if (MediaType.JPEG.value.equals(mime)) {
            return MediaType.JPEG;
        } else if (MediaType.JPG.value.equals(mime)) {
            return MediaType.JPG;
        } else if (MediaType.PNG.value.equals(mime)) {
            return MediaType.PNG;
        } else if (MediaType.ThreeGP.value.equals(mime)) {
            return MediaType.ThreeGP;
        } else if (MediaType.MP4.value.equals(mime)) {
            return MediaType.MP4;
        } else if (MediaType.GIF.value.equals(mime)) {
            return MediaType.GIF;
        }
        return MediaType.UNKOWN;
    }

    public static String getFileSuffix(String path) {
        return new String(path.substring(path.lastIndexOf("."))).toLowerCase(Locale.getDefault()); // 获取后缀
    }

    /**
     * 根据uri获取文件名称
     *
     * @param uri
     * @return
     */
    public static String getFileName(String uri) {
        if (StringUtils.isEmpty(uri)) {
            return "";
        }

        return uri.substring(uri.lastIndexOf("/") + 1);
    }

    public static String getCameraPath(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath();
    }

    public static boolean isUrl(String url) {
        return StringUtils.isEmpty(url) ? false : (url.toLowerCase().startsWith(Schema.HTTP.getValue()) || url.toLowerCase().startsWith(Schema.HTTPS.getValue()));
    }

}
