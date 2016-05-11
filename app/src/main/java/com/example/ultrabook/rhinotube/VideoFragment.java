package com.example.ultrabook.rhinotube;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    List<Video> mVideos;
    ArrayAdapter<Video> mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.bind(this, layout);

        mVideos = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mVideos);
        mListView.setAdapter(mAdapter);

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new VideoTask().execute();
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
        protected Search doInBackground(Void... params) {
            String url = "https://dl.dropboxusercontent.com/s/qt8hhfqo0bj02ds/arquivo.json";
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String jsonBody = response.body().string();
                Log.d("Teste", jsonBody);
                Gson gson = new Gson();
                //Search search = gson.fromJson(jsonBody, Search.class);
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
                for(Video v : search.getVideos()){
                    mVideos.addAll(search.getVideos());
                }
                mAdapter.notifyDataSetChanged();
            }
        }
    }

}
