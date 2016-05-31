package com.example.ultrabook.rhinotube;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.ultrabook.rhinotube.model.Video;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
/*
    Adapter responsável por carregar os videos no listView
*/
public class VideoAdapter extends ArrayAdapter<Video> {
    public VideoAdapter(Context context, List<Video> videos) {
        super(context, 0, videos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Video video = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_video, parent, false);
            viewHolder = new ViewHolder(convertView);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(getContext()).load(video.getThumbnail()).into(viewHolder.imageView);
        viewHolder.textTitle.setText(video.getTitle());
        return convertView;
    }

    static class ViewHolder{
        @Bind(R.id.imageView)
        ImageView imageView;
        @Bind(R.id.textVideoTitle)
        TextView textTitle;
        public ViewHolder(View parent){
            ButterKnife.bind(this,parent);
            parent.setTag(this);
        }
    }
}
