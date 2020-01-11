package com.xtp.qqmusic.fragment;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.xtp.qqmusic.R;
import com.xtp.qqmusic.activity.VideoPlayerActivity;
import com.xtp.qqmusic.bean.VideoItem;
import com.xtp.qqmusic.utils.CursorUtil;
import com.xtp.qqmusic.adapter.VideoCursorAdapter;
import com.xtp.qqmusic.utils.MyAsyncQueryHandler;
import com.xtp.qqmusic.utils.PermisionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 视频列表
 */
public class VideoListFragment extends BaseFragment {

    private ListView simple_listview ;

    private  VideoCursorAdapter videoCursorAdapter;

    private class VideoListOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            //1.listview 添加条目点击
            Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
//            VideoItem videoItem = VideoItem.InstanceFromCursor(cursor);
            ArrayList<VideoItem> videoItems = VideoItem.InstanceListFromCursor(cursor);
            //2.传递javabeen,跳转到播放界面
            Intent intent = new Intent(context, VideoPlayerActivity.class);
            intent.putExtra("videoItems",videoItems);
            intent.putExtra("position",position);
            startActivity(intent);
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.video_list;
    }

    @Override
    public void initView(View view) {
        simple_listview = view.findViewById(R.id.simple_listview);
    }
    @Override
    public void initListener() {
        videoCursorAdapter = new VideoCursorAdapter(context,null);
        simple_listview.setAdapter(videoCursorAdapter);
        simple_listview.setOnItemClickListener(new VideoListOnItemClickListener());
    }

    @Override
    public void initData() {
        //从ContentResolver读取数据
        ContentResolver contentResolver = context.getContentResolver();
       /* Cursor cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DATA}, null, null, null);
        CursorUtil.printCursor(cursor);
        //刷新适配器
        videoCursorAdapter.swapCursor(cursor);*/

         //异步查询数据库
        AsyncQueryHandler queryHandler = new MyAsyncQueryHandler(contentResolver);
        //token msg.what,用于区分多次查询
        //cookie用于传递的数据 msg.obj
        //开启查询
        //配置权限
        PermisionUtils.verifyStoragePermissions(getActivity());
        queryHandler.startQuery(0,videoCursorAdapter,MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                new String[]{
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DATA}, null, null, null);
    }
}
