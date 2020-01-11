package com.xtp.qqmusic.utils;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.widget.CursorAdapter;

/**
 * 查询完成后调用onQueryComplete()方法
 */
public class MyAsyncQueryHandler extends AsyncQueryHandler {
    public MyAsyncQueryHandler(ContentResolver cr) {
        super(cr);
    }

    /**
     * //token msg.what,用于区分多次查询
     * //cookie用于传递的数据 msg.obj
     * @param token
     * @param cookie
     * @param cursor
     */
    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        CursorUtil.printCursor(cursor);
        //刷新适配器
        if(cookie instanceof CursorAdapter){
            CursorAdapter cursorAdapter = (CursorAdapter) cookie;
            cursorAdapter.swapCursor(cursor);
        }
    }
}
