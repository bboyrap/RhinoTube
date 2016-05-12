package com.example.ultrabook.rhinotube;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ultrabook.rhinotube.Model.Video;

import java.util.List;

public class VideoAdapter extends ArrayAdapter<Video> {
    public VideoAdapter(Context context, List<Video> videos) {
        super(context, 0, videos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Video video = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_video, null);
        }

        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
        TextView textTitle = (TextView)convertView.findViewById(R.id.textVideoTitle);
        TextView textDescription = (TextView)convertView.findViewById(R.id.textVideoDescription);

        Glide.with(getContext()).load(video.getThumbnail()).into(imageView);
        textTitle.setText(video.getTitle());
        textDescription.setText(video.getDescription());

        return convertView;
    }
}
