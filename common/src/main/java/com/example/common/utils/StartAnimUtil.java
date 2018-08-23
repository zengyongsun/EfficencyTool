package com.example.common.utils;

import android.app.Activity;

import com.example.common.R;

/**
 * @author Jacky.Cai
 * @version v1.0
 * @Package com.l99.lotto.utils
 * @Description: Activity跳转动画
 * @date 2015/9/17 11:10
 */
public class StartAnimUtil {

    public static void commonStartAnim(Activity activity){
        activity.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    public static void commonFinishAnim(Activity activity){
        activity.overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

    public static void commonStartAnimFromBottom(Activity activity){
        activity.overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
    }

    public static void commonFinishAnimToTop(Activity activity){
        activity.overridePendingTransition(R.anim.slide_in_from_top, R.anim.slide_out_to_bottom);
    }
}
