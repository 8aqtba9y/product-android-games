package com.syun.and.flowercollector.utils;

/**
 * Created by qijsb on 2017/11/12.
 */
public class RanStrUtil {
    /**
     * @param size 文字列のサイズ
     * @return ランダムな文字列
     */
    public static String generate(int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append( seed[(int)(seed.length*Math.random())] );
        }
        return sb.toString();
    }

    private static final char[] seed = new char[62];
    static {
        int i = 0;
        for ( char c = 'a'; c <= 'z'; c++ ) seed[i++] = c;
        for ( char c = '0'; c <= '9'; c++ ) seed[i++] = c;
        for ( char c = 'A'; c <= 'Z'; c++ ) seed[i++] = c;
    }
}
