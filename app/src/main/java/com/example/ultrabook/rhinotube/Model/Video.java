package com.example.ultrabook.rhinotube.Model;

import org.parceler.Parcel;

/**
 * Created by ultrabook on 01/05/2016.
 */
@Parcel
public class Video {
    String description;
    String thumbnailURL;
    String title;
//    private String id;
    public Video(){

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
    public String getThumbnailURL() {
        return thumbnailURL;
    }
    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
