package com.xtp.qqmusic.utils;

import android.database.Cursor;

public class CursorUtil {
    public static void printCursor(Cursor cursor){
        if(cursor==null) return;
        while (cursor.moveToNext()){
            LogUtils.i("CursorUtil","---------------------------------------------------");
            for (int i=0;i<cursor.getColumnCount();i++){
                LogUtils.i("CursorUtil",cursor.getColumnName(i)+":"+cursor.getString(i));
            }
        }
    }
}
