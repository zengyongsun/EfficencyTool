package com.example.common.utils;

import android.app.Activity;
import android.content.Context;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @author Kim.H
 * @version v1.0
 * @Package com.force.librarybase.utils
 * @Description:
 * @date 2015-09-28 15:05
 */
public class InputMethodUtils {

    /**
     * 不弹出键盘,显示光标
     *
     * @param edit
     */
    public static void hideKeyboardShowCursor(final EditText edit) {
        edit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = edit.getInputType();
                edit.setInputType(InputType.TYPE_NULL);
                edit.onTouchEvent(event);
                edit.setInputType(inType);
                // 光标置后
                CharSequence text = edit.getText();
                if (text instanceof Spannable) {
                    Spannable spanText = (Spannable) text;
                    Selection.setSelection(spanText, text.length());
                }
                return true;
            }
        });
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm.isActive()
                && (!Null.isNull(activity.getCurrentFocus()) && !Null.isNull(activity.getCurrentFocus().getWindowToken()))) {
            try {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                //ignore
            }
        }
    }

    /**
     * 关闭软件盘
     *
     * @param context
     * @param v
     */
    public static void hideSoftwareKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 显示软件盘
     *
     * @param context
     * @param v
     */
    public static void showSoftwareKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, 0);
    }

    /**
     * 关闭软键盘
     *
     * @param context
     * @param v
     */
    public static void hideSoftKeyboardEvennotActive(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (v != null) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 在系统级别隐藏键盘
     *
     * @param activity
     */
    public static void hideSoftKeyboardSystem(Activity activity) {
        // 隐藏软键盘
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        activity.getWindow().getAttributes().softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN;
    }

    /**
     * 检测键盘是否展开
     *
     * @param activity
     * @return
     */
    public static boolean checkSoftKeyboardShowing(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        if (params.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE) {
            return true;
        }
        return false;
    }
}
