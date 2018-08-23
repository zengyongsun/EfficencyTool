package com.example.common.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * ImageUtils
 * <ul>
 * convert between Bitmap, byte array, Drawable
 * <li>{@link #bitmapToByte(Bitmap)}</li>
 * <li>{@link #bitmapToDrawable(Bitmap)}</li>
 * <li>{@link #byteToBitmap(byte[])}</li>
 * <li>{@link #byteToDrawable(byte[])}</li>
 * <li>{@link #drawableToBitmap(Drawable)}</li>
 * <li>{@link #drawableToByte(Drawable)}</li>
 * </ul>
 * <ul>
 * scale image
 * <li>{@link #scaleImageTo(Bitmap, int, int)}</li>
 * <li>{@link #scaleImage(Bitmap, float, float)}</li>
 * </ul>
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2012-6-27
 */
public class ImageUtils {

    private ImageUtils() {
        throw new AssertionError();
    }

    /**
     * convert Bitmap to byte array
     *
     * @param b
     * @return
     */
    public static byte[] bitmapToByte(Bitmap b) {
        if (b == null) {
            return null;
        }

        ByteArrayOutputStream o = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, o);
        return o.toByteArray();
    }

    /**
     * convert byte array to Bitmap
     *
     * @param b
     * @return
     */
    public static Bitmap byteToBitmap(byte[] b) {
        return (b == null || b.length == 0) ? null : BitmapFactory
                .decodeByteArray(b, 0, b.length);
    }

    /**
     * convert Drawable to Bitmap
     *
     * @param d
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable d) {
        return d == null ? null : ((BitmapDrawable) d).getBitmap();
    }

    /**
     * convert Bitmap to Drawable
     *
     * @param b
     * @return
     */
    public static Drawable bitmapToDrawable(Bitmap b) {
        return b == null ? null : new BitmapDrawable(b);
    }

    /**
     * convert Drawable to byte array
     *
     * @param d
     * @return
     */
    public static byte[] drawableToByte(Drawable d) {
        return bitmapToByte(drawableToBitmap(d));
    }

    /**
     * convert byte array to Drawable
     *
     * @param b
     * @return
     */
    public static Drawable byteToDrawable(byte[] b) {
        return bitmapToDrawable(byteToBitmap(b));
    }

    /**
     * scale image
     *
     * @param org
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) {
        return scaleImage(org, (float) newWidth / org.getWidth(),
                (float) newHeight / org.getHeight());
    }

    /**
     * scale image
     *
     * @param org
     * @param scaleWidth  sacle of width
     * @param scaleHeight scale of height
     * @return
     */
    public static Bitmap scaleImage(Bitmap org, float scaleWidth,
                                    float scaleHeight) {
        if (org == null) {
            return null;
        }

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(),
                matrix, true);
    }

    /**
     * compress the bitmap
     *
     * @param orginalBitmap  目标图片
     * @param compressFormat 格式
     * @param quality        图片质量
     * @param destPath       保存路径
     */
    public static void CompressImage(Bitmap orginalBitmap,
                                     CompressFormat compressFormat, int quality, String destPath) {
        if (orginalBitmap == null || orginalBitmap.isRecycled()) {
            throw new RuntimeException(
                    "the orignal bitmap is null or has been rescycled.");
        }
        try {
            OutputStream outputStream = new FileOutputStream(new File(destPath));
            orginalBitmap.compress(compressFormat, quality, outputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * show bitmap in center and scale to best size
     *
     * @param bitmap               要显示的图片
     * @param imageView            显示图片的控件
     * @param width                要显示的宽度
     * @param height               要显示的高度
     * @param isHeightMorePriority 是否以填充高度为优先
     */
    public static void setImageScaleCenter(Bitmap bitmap, ImageView imageView, int width,
                                           int height, boolean isHeightMorePriority) {
        if (bitmap == null || bitmap.isRecycled()) {
            Logger.e("bitmap is null or maybe recycled...");
            return;
        }

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        if (bitmapWidth < width || bitmapHeight < height) {
            Logger.w("bitmap maybe Stretched...");
        }

        float scaleX = ((float) width) / bitmapWidth;
        float scaleY = ((float) height) / bitmapHeight;

        float bestScale = scaleX > scaleY ? scaleX : scaleY;
        //以填充高度的前提计算最佳缩放倍数
        if (isHeightMorePriority) {
            bestScale = scaleY;
        }

        //计算中心点
        float centerX = (width - bitmapWidth * bestScale) / 2;
        float centerY = (height - bitmapHeight * bestScale) / 2;

        Matrix martrix = new Matrix();
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        martrix.postScale(bestScale, bestScale);
        martrix.postTranslate(centerX, centerY);
        imageView.setImageMatrix(martrix);
        imageView.setImageBitmap(bitmap);

    }

    /**
     * 将彩色图转换为纯黑白二色
     *
     * @param bmp 位图
     * @return 返回转换好的位图
     */
    public static Bitmap convertToBlackWhite(Bitmap bmp) {
        int width = bmp.getWidth(); // 获取位图的宽
        int height = bmp.getHeight(); // 获取位图的高
        int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];

                //分离三原色
                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                //转化成灰度像素
                grey = (int) (red * 0.3 + green * 0.59 + blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        //新建图片
        Bitmap newBmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        //设置图片数据
        newBmp.setPixels(pixels, 0, width, 0, 0, width, height);

        //Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(newBmp, 380, 460);
        //return resizeBmp;
        return newBmp;
    }
}
