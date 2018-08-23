package com.example.common.packaging;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.util.concurrent.ExecutionException;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @author Jacky.Cai
 * @version v1.0
 * @Package com.force.librarybase.utils
 * @Description:
 * @date 16/7/21 下午5:08
 */
public class GlideLoader {

    private volatile static GlideLoader instance;


    /**
     * Returns singleton class instance
     */
    public static GlideLoader getInstance() {
        if (instance == null) {
            synchronized (GlideLoader.class) {
                if (instance == null) {
                    instance = new GlideLoader();
                }
            }
        }
        return instance;
    }

    public void load(Context context, String url, ImageView imageView) {
        RequestOptions requestOptions= new RequestOptions()
                .centerCrop();
        Glide.with(context)
                .load(url)
                .transition(withCrossFade())
                .apply(requestOptions)
                .into(imageView);
    }

    public void load(Context context, int resId, ImageView imageView) {
        RequestOptions requestOptions= new RequestOptions()
                .fitCenter();
        Glide.with(context)
                .load(resId)
                .transition(withCrossFade())
                .apply(requestOptions)
                .into(imageView);
    }

    public void load(Context context, String url, ImageView imageView, int placeHolderRes) {
        RequestOptions requestOptions= new RequestOptions()
                .centerCrop()
                .placeholder(placeHolderRes);
        Glide.with(context)
                .load(url)
                .transition(withCrossFade())
                .apply(requestOptions)
                .into(imageView);
    }

    public void load(Context context, String url, int placeHolder, DrawableImageViewTarget target) {
        RequestOptions requestOptions= new RequestOptions()
                .centerCrop()
                .placeholder(placeHolder    );
        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(target);

    }

    public void loadLetterDetailImage(Context context, String url, SimpleTarget target) {
        RequestOptions requestOptions= new RequestOptions()
                .centerCrop();
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(requestOptions)
                .into(target);

    }

    public void loadRoundBm(Context context, String url, int placeHolder, ImageView imageView) {
        RequestOptions requestOptions= new RequestOptions()
                .centerCrop()
                .transforms(new CropCircleTransformation())
                .placeholder(placeHolder);
        Glide.with(context)
                .load(url)
                .transition(withCrossFade())
                .apply(requestOptions)
                .into(imageView);
    }

    public void clear(Context context) {
        Glide.get(context).clearMemory();
    }

    public void loadRoundBmListens(Context context, String url, int placeHolder, DrawableImageViewTarget target) {
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .bitmapTransform(new CropCircleTransformation())
                .placeholder(placeHolder);
        Glide.with(context)
                .load(url)
                .transition(withCrossFade())
                .apply(requestOptions)
                .into(target);
    }

    public File downImage(Context context, String url) throws ExecutionException, InterruptedException {
        return Glide.with(context)
                .asBitmap()
                .load(url)
                .downloadOnly(100, 100)
                .get();
    }
}
