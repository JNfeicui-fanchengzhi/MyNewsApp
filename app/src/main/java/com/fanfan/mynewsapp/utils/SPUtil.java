package com.fanfan.mynewsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 包含保存和取出数据方法的类
 * Created by Administrator on 2016/6/15.
 */
public class SPUtil {
    private static SharedPreferences mPreferences;
    private static final String NAME="ViewPager";
    //保存 数据
    public static void putString(Context context, String key, String value){
        SharedPreferences sp=getPreferences(context);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    private static SharedPreferences getPreferences(Context context) {
        if (mPreferences==null){
            mPreferences=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        }
        return mPreferences;
    }

    /**
     *获得一个String类型的数据，如果没有，则返回null
     * @param context
     * 上下文
     * @param key
     * sp里的key
     * @return
     */
    public static String  getString(Context context, String key) {
        return getString(context, key,null);
    }

    /**
     *获得一个String类型的数据
     * @param context
     * 上下文
     * @param key
     * sp里的key
     * @param defValue
     * sp里的defValue
     * @return
     */
    private static String getString(Context context, String key, String defValue) {
        SharedPreferences sp=getPreferences(context);
        return sp.getString(key, defValue);
    }
}
