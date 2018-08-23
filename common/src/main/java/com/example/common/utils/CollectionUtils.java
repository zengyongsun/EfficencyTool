package com.example.common.utils;

import android.text.TextUtils;

import java.util.Collection;
import java.util.Map;

/**
 * CollectionUtils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2012-7-22
 */
public class CollectionUtils {

    /**
     * default join separator
     **/
    public static final CharSequence DEFAULT_JOIN_SEPARATOR = ",";

    private CollectionUtils() {
        throw new AssertionError();
    }

    /**
     * is null or its size is 0
     * <p/>
     * <pre>
     * isEmpty(null)   =   true;
     * isEmpty({})     =   true;
     * isEmpty({1})    =   false;
     * </pre>
     *
     * @param <V>
     * @param c
     * @return if collection is null or its size is 0, return true, else return
     * false.
     */
    public static <V> boolean isEmpty(Collection<V> c) {
        return (c == null || c.size() == 0);
    }

    /**
     * is null or empty iterable
     * <p/>
     * <pre>
     * isEmpty(null)   =   true;
     * isEmpty({})     =   true;
     * isEmpty({1})    =   false;
     * </pre>
     *
     * @param iterable
     * @return
     */
    public static <V> boolean isEmpty(Iterable<V> iterable) {
        return iterable == null || !iterable.iterator().hasNext();
    }

    /**
     * is null or empty map
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.size() == 0;
    }

    /**
     * 将数据拼接成一个字符串, 分隔符{@link #DEFAULT_JOIN_SEPARATOR}
     * <p/>
     * <pre>
     * join(null)      =   "";
     * join({})        =   "";
     * join({a,b})     =   "a,b";
     * </pre>
     *
     * @param iterable
     */
    public static <V> String join(Iterable<V> iterable) {
        return isEmpty(iterable) ? "" : TextUtils.join(DEFAULT_JOIN_SEPARATOR,
                iterable);
    }

    /**
     * 将数据拼接成一个字符串，如"a|b|c"
     *
     * @param iterable  迭代器
     * @param separator 分隔符
     * @return
     */
    public static <V> String join(Iterable<V> iterable, String separator) {
        if (StringUtils.isEmpty(separator)) {
            throw new AssertionError(String.format("bad separator:%s",
                    separator));
        }
        return isEmpty(iterable) ? "" : TextUtils.join(separator, iterable);
    }
}
