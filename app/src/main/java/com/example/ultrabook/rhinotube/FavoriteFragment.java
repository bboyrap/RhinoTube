package com.example.ultrabook.rhinotube;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ultrabook.rhinotube.model.Video;
import com.example.ultrabook.rhinotube.database.VideoDAO;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class FavoriteFragment extends Fragment {

    @Bind(R.id.list_video)
    ListView mListView;
    @Bind(R.id.empty)
    View mEmpty;

    List<Video> mVideos;
    ArrayAdapter<Video> mAdapter;
    VideoDAO mDao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mDao = new VideoDAO(getActivity());
        mVideos = mDao.getList();
        ((VideoApp)getActivity().getApplication()).getEventBus().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((VideoApp)getActivity().getApplication()).getEventBus().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, layout);

        mAdapter = new VideoAdapter(getContext(),mVideos);

        mListView.setEmptyView(mEmpty);

        mListView.setAdapter(mAdapter);
        return layout;
    }

    @OnItemClick(R.id.list_video)
    void onItemSelected(int position){
        Video video = mVideos.get(position);
        if(getActivity() instanceof TouchVideoListner){
            TouchVideoListner listner = (TouchVideoListner)getActivity();
            listner.videoWasClicked(video);
        }
    }

    @Subscribe
    public void refresh(Video video){
        mVideos.clear();
        mVideos.addAll(mDao.getList());
        mAdapter.notifyDataSetChanged();

    }


}
