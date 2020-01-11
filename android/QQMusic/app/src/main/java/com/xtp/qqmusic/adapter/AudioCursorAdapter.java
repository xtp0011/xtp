package com.xtp.qqmusic.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.xtp.qqmusic.R;
import com.xtp.qqmusic.bean.AudioItem;

public class AudioCursorAdapter extends CursorAdapter {
    public AudioCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    private class AudioListHolder{
        TextView audio_list_item_tv_title,audio_list_item_tv_artist;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = View.inflate(context, R.layout.audio_list_item,null);
        AudioListHolder holder = new AudioListHolder();
        holder.audio_list_item_tv_title= view.findViewById(R.id.audio_list_item_tv_title);
        holder.audio_list_item_tv_artist= view.findViewById(R.id.audio_list_item_tv_artist);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        AudioListHolder holder = (AudioListHolder) view.getTag();
        AudioItem audioItem = AudioItem.InstanceFromCursor(cursor);
        holder.audio_list_item_tv_title.setText(audioItem.getTitle());
        holder.audio_list_item_tv_artist.setText(audioItem.getArtist());
    }
}
