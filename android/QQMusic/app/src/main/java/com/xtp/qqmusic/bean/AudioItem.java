package com.xtp.qqmusic.bean;

import android.database.Cursor;
import android.provider.MediaStore;

import java.io.Serializable;
import java.util.ArrayList;

public class AudioItem implements Serializable {
    private int id;
    private String title;
    private String artist;
    private int duration;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public static AudioItem InstanceFromCursor(Cursor cursor){
        AudioItem audioItem = new AudioItem();
        audioItem.setId(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
        audioItem.setDuration(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
        audioItem.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
        audioItem.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
        audioItem.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
        return audioItem;
    }

    public static ArrayList<AudioItem> InstanceListFromCursor(Cursor cursor){
        ArrayList<AudioItem> audioItems = new ArrayList<>();
        for (int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            AudioItem audioItem = AudioItem.InstanceFromCursor(cursor);
            audioItems.add(audioItem);
        }
        return audioItems;
    }
}
