package com.xtp.qqmusic.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xtp.qqmusic.R;
import com.xtp.qqmusic.activity.AudioPlayerActivity;
import com.xtp.qqmusic.adapter.AudioCursorAdapter;
import com.xtp.qqmusic.bean.AudioItem;
import com.xtp.qqmusic.utils.MyAsyncQueryHandler;
import com.xtp.qqmusic.utils.PermisionUtils;

import java.util.ArrayList;

/**
 * 音频列表
 */
public class AudioListFragment extends BaseFragment {

    private ListView simple_listview;
    private AudioCursorAdapter cursorAdapter;

    private class AudioOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //传递当前点击位置的数据，跳转到播放界面
            Cursor cursor = (Cursor) parent.getItemAtPosition(position);
           // AudioItem audioItem = AudioItem.InstanceFromCursor(cursor);
            ArrayList<AudioItem> audioItems = AudioItem.InstanceListFromCursor(cursor);
            Intent intent = new Intent(context, AudioPlayerActivity.class);
            intent.putExtra("AudioItems",audioItems);
            intent.putExtra("position",position);
            startActivity(intent);
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.audio_list;
    }

    @Override
    public void initView(View view) {
        simple_listview = view.findViewById(R.id.simple_listview);
    }

    @Override
    public void initListener() {
        cursorAdapter = new AudioCursorAdapter(context,null);
        simple_listview.setAdapter(cursorAdapter);
        simple_listview.setOnItemClickListener(new AudioOnItemClickListener()   );
    }

    @Override
    public void initData() {
        ContentResolver contentResolver = context.getContentResolver();
        //获取数据
        MyAsyncQueryHandler handler = new MyAsyncQueryHandler(contentResolver);
        //配置权限
        PermisionUtils.verifyStoragePermissions(getActivity());
        handler.startQuery(0,cursorAdapter, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA
            },null,null,null);
    }
}
