package com.example.ultrabook.rhinotube;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ultrabook.rhinotube.Model.Search;
import com.example.ultrabook.rhinotube.Model.Video;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VideoFragment extends Fragment {

    @Bind(R.id.list_video)
    ListView mListView;
    @Bind(R.id.swipe)
    SwipeRefreshLayout mSwipe;


    List<Video> mVideos;
    ArrayAdapter<Video> mAdapter;
    VideoTask mTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mVideos = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.bind(this, layout);

        mAdapter = new VideoAdapter(getContext(),mVideos);
        mListView.setAdapter(mAdapter);

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTask = new VideoTask();
                mTask.execute();
            }
        });
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mVideos.size() == 0 && (mTask == null)){
            mTask = new VideoTask();
            mTask.execute();
        }else if(mTask != null && mTask.getStatus() == AsyncTask.Status.RUNNING){
            showProgress();
        }
    }

    private void showProgress(){
        mSwipe.post(new Runnable() {
            @Override
            public void run() {
                mSwipe.setRefreshing(true);
            }
        });
    }

    @OnItemClick(R.id.list_video)
    void onItemSelected(int position){
        Video video = mVideos.get(position);
        if(getActivity() instanceof TouchVideoListner){
            TouchVideoListner listner = (TouchVideoListner)getActivity();
            listner.videoWasClicked(video);
        }
    }

    public interface TouchVideoListner{
        void videoWasClicked(Video video);
    }

    class VideoTask extends AsyncTask<Void, Void, Search>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }

        @Override
        protected Search doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(Constant.URL)
                    .build();
            try {
                Thread.sleep(3000);
                Response response = client.newCall(request).execute();
                String jsonBody = response.body().string();
                Gson gson = new Gson();
                return gson.fromJson(jsonBody, Search.class);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Search search) {
            super.onPostExecute(search);

            if(search != null){
                mVideos.clear();
                mVideos.addAll(search.getVideos());
                Log.d("Teste", "Quantidade de videos da busca: "+search.getVideos().size());
                for(Video v : search.getVideos()){
//                    mVideos.add(v);
                    Log.d("Teste", "video: "+v.getTitle());
                }
                mAdapter.notifyDataSetChanged();
            }
            mSwipe.setRefreshing(false);
        }
    }

}
