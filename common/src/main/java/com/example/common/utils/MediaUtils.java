package com.example.common.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

public class MediaUtils {

	/**
	 * 保存图片到本地时通知系统生成缩略图
	 * @param context
	 * @param fileName
	 * @param dirPath
	 * @param fileSize
	 */
	public static void addNewMediaToLocal(Context context, String fileName, String dirPath, long fileSize){
		// 自动扫描已保存的图片，以便在相册中查看
		// String uriStr = Media.insertImage(getContentResolver(), dirPath + fileName, fileName, "FirstTime");
		// Uri data = Uri.parse(uriStr);       
		// sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));   
		
		 ContentValues values = new ContentValues(6);
         values.put(MediaStore.Images.Media.TITLE,fileName);
         values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
         values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
         values.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
         //values.put(MediaStore.Images.Media.ORIENTATION, degree[0]);
         values.put(MediaStore.Images.Media.DATA, dirPath + fileName);
         values.put(MediaStore.Images.Media.SIZE, fileSize);
         Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
         context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
	}
}
