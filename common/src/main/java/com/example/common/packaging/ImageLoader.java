package com.example.common.packaging;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * <pre>
 *    @author : Zeyo
 *     e-mail : zengyongsun@163.com
 *     time   : 2018/05/07
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ImageLoader {

    public static void loadImage(Context context, String url, ImageView view) {
        Glide.with(context).load(url).into(view);
    }

}
