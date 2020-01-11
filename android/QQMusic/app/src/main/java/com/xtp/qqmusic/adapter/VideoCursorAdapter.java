package com.xtp.qqmusic.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.xtp.qqmusic.R;
import com.xtp.qqmusic.utils.StringUtil;
import com.xtp.qqmusic.bean.VideoItem;

public class VideoCursorAdapter extends CursorAdapter {


    public VideoCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    private class VideoListHolder{
        TextView video_list_item_tv_title,video_list_item_tv_duration,video_list_item_tv_size;
    }

    /***
     * 创建子布局
     * @param context
     * @param cursor
     * @param parent
     * @return
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = View.inflate(context, R.layout.video_list_item,null);
        VideoListHolder videoListHolder = new VideoListHolder();
        videoListHolder.video_list_item_tv_title = view.findViewById(R.id.video_list_item_tv_title);
        videoListHolder.video_list_item_tv_duration = view.findViewById(R.id.video_list_item_tv_duration);
        videoListHolder.video_list_item_tv_size = view.findViewById(R.id.video_list_item_tv_size);
        view.setTag(videoListHolder);
        return view;
    }

    /**
     * 子布局填充控件
     * @param view
     * @param context
     * @param cursor
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        VideoListHolder holder = (VideoListHolder)view.getTag();
        VideoItem videoItem = VideoItem.InstanceFromCursor(cursor);
        holder.video_list_item_tv_title.setText(videoItem.getTitle());
        String duration = StringUtil.formatTime(videoItem.getDuration());
        holder.video_list_item_tv_duration.setText(duration);
        holder.video_list_item_tv_size.setText(Formatter.formatFileSize(context,videoItem.getSize()));
    }
}
