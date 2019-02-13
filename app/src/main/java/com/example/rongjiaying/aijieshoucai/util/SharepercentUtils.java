package com.example.rongjiaying.aijieshoucai.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by BODY on 2017/4/18.
 * Sharepercent操作工具类
 */

public class SharepercentUtils {
    public static final String FILE_NAME = "aijiesoucaisp_file.xml";

    private SharedPreferences sp;


    public SharepercentUtils(Context context) {
        sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
    }

    public void putString(String key, String value) {
        if (sp != null) {
            if (key == null || key.equals(""))
                throw new IllegalArgumentException(
                        "Key can't be null or empty string");
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, value);
            editor.commit();
        }

    }

    public String getString(String key) {
        if (key == null || key.equals(""))
            throw new IllegalArgumentException(
                    "Key can't be null or empty string");
        return sp.getString(key, "");
    }

    public int getInt(String key) {
        return sp.getInt(key, 0);
    }

    public int getInt(String key, int defa) {
        return sp.getInt(key, defa);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor e = sp.edit();
        e.putInt(key, value);
        e.commit();
    }

    public void putBoolean(String key, boolean value) {
        if (key == null || key.equals(""))
            throw new IllegalArgumentException(
                    "Key can't be null or empty string");
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }

    public boolean isContainsKey(String key) {
        return sp.contains(key);
    }

    public long getLong(String key) {
        return sp.getLong(key, 0);
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void remove(String key) {
        if (sp != null) {
            if (key == null || key.equals(""))
                throw new IllegalArgumentException(
                        "Key can't be null or empty string");
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(key);
            editor.commit();
        }

    }
}
