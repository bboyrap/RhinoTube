package com.example.ultrabook.rhinotube.Model;

import org.parceler.Parcel;

@Parcel
public class Video {
    String description;
    String thumbnail;
    String title;
//    private String id;
    public Video(){

    }

    @Override
    public String toString() {
        return "Title: " + title + "\nDescription: " + description;
    }

    public Video(String title, String description){
        this.title = title;
        this.description=description;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(String thumbnailURL) {
        this.thumbnail = thumbnailURL;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
