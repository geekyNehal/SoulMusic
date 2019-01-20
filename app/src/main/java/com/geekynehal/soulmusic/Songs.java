package com.geekynehal.soulmusic;

import android.net.Uri;

public class Songs
{
    private String song_name,song_album,song_path,duration,dateIndex;
    private Uri songUri;
    private int songId;
    public Songs()
    {

    }
    public Songs(String song_name,int id,String song_album,String song_path,String duration,String dateIndex,Uri uri)
    {
        this.song_name=song_name;
        this.song_album=song_album;
        this.songId=id;
        this.song_path=song_path;
        this.duration=duration;
        this.dateIndex=dateIndex;
        this.songUri=uri;
    }
    public String getSongName()
    {
        return song_name;
    }
    public String getSongAlbum()
    {
        return song_album;
    }
    public String getSongPath() {
        return song_path;
    }

    public String getDuration() {
        return duration;
    }

    public String getDateIndex() {
        return dateIndex;
    }

    public Uri getSongUri() {
        return songUri;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongName(String song_name) {
        this.song_name = song_name;
    }

    public void setSongAlbum(String song_album) {
        this.song_album = song_album;
    }

    public void setSongPath(String song_path) {
        this.song_path = song_path;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setDateIndex(String dateIndex) {
        this.dateIndex = dateIndex;
    }

    public void setSongUri(Uri songUri) {
        this.songUri = songUri;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }
}
