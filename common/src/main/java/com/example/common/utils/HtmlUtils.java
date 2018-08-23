package com.example.common.utils;

import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Html.TagHandler;
import android.text.Spanned;
import android.widget.TextView;

/**
 * Html处理工具类
 * 
 * @author Kim.Huang
 * 
 */
public class HtmlUtils {

	/**
	 * 将html内容转为spanned
	 * 
	 * @param htmlStr
	 * @return
	 */
	public static Spanned fromHtml(String htmlStr) {
		return Html.fromHtml(htmlStr);
	}

	/**
	 * 将html内容转为spanned
	 * 
	 * @param htmlStr
	 *            html原始内容
	 * @param imageGetter
	 *            图片获取器
	 * @param tagHandler
	 *            标签处理器
	 * @return
	 */
	public static Spanned fromHtml(String htmlStr, ImageGetter imageGetter,
                                   TagHandler tagHandler) {
		return Html.fromHtml(htmlStr, imageGetter, tagHandler);
	}

	/**
	 * 将spanned转为text
	 * 
	 * @param spanned
	 * @return
	 */
	public static String html2Text(Spanned spanned) {
		return Html.toHtml(spanned);
	}

	/**
	 * 在TextView内容尾部显示html内容
	 * 
	 * @param textView
	 * @param htmlStr
	 */
	public static void showHtmlEnd(TextView textView, String htmlStr) {
		showHtmlAtIndex(textView, htmlStr, textView.getText().length());
	}

	/**
	 * 在TextView内容头部显示html内容
	 * 
	 * @param textView
	 * @param htmlStr
	 */
	public static void showHtmlHead(TextView textView, String htmlStr) {
		showHtmlAtIndex(textView, htmlStr, 0);
	}

	/**
	 * 在TextView中显示html内容
	 * 
	 * @param textView
	 * @param htmlStr
	 * @param index
	 */
	public static void showHtmlAtIndex(TextView textView, String htmlStr,
                                       int index) {
		String orginalText = textView.getText().toString();
		if (!StringUtils.isBlank(orginalText)) {
			if (index < 0 || index > orginalText.length()) {
				throw new RuntimeException();
			}
			String header = orginalText.substring(0, index);
			String tail = orginalText.substring(index, orginalText.length());
			String newStr = header + "<span>" + htmlStr + "</span>" + tail;
			textView.setText(fromHtml(newStr));
		} else {
			Spanned spanned = fromHtml(htmlStr);
			textView.setText(spanned);
		}
	}
}
