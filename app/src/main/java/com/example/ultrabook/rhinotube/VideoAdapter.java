package com.example.ultrabook.rhinotube;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ultrabook.rhinotube.Model.Video;

public class VideoAdapter extends ArrayAdapter<Video> {
    public VideoAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Video video = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_video, null);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        TextView textTitle = (TextView) convertView.findViewById(R.id.text_title);
        TextView textDescription = (TextView) convertView.findViewById(R.id.text_description);

        return super.getView(position, convertView, parent);
    }
}
