package com.xtp.qqmusic.bean;

import android.database.Cursor;
import android.provider.MediaStore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class VideoItem implements Serializable {

    private  int id ;
    private int size;
    private int duration;
    private String title;
    private String path;

    public static VideoItem InstanceFromCursor(Cursor cursor){
        VideoItem videoItem = new VideoItem();
        videoItem.setId(cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID)));
        videoItem.setSize(cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.SIZE)));
        videoItem.setDuration(cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.DURATION)));
        videoItem.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE)));
        videoItem.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)));
        return videoItem;
    }

    public static ArrayList<VideoItem> InstanceListFromCursor(Cursor cursor) {
        ArrayList<VideoItem> videoItems = new ArrayList<>();
        for (int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            VideoItem videoItem = InstanceFromCursor(cursor);
            videoItems.add(videoItem);
        }
        return videoItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
