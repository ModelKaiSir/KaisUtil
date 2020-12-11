package com.kais.core;

import java.util.AbstractMap;
import java.util.Map;

/**
 * 工具类
 * @author QiuKai
 */
public class KaisUtils {

    /**
     * entry of K, V
     *
     * @param k
     * @param v
     * @return Entry
     */
    public static <T1, T2> Map.Entry<T1, T2> entryOf(T1 k, T2 v) {

        return new AbstractMap.SimpleEntry<T1, T2>(k, v);
    }
}
