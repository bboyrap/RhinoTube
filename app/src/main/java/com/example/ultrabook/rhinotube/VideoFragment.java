package com.example.ultrabook.rhinotube;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ultrabook.rhinotube.model.Search;
import com.example.ultrabook.rhinotube.model.Video;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/*
    Fragment responsável por listar todos os vídeos resolvidos pela web(json)
-
No onCreateView() é carregado o arquivo de layout do fragment.
-
No onActivityCreated() verificamos se a lista ja está carregada ou se a task está executando.
-
No loadJson() é chamado no createView(), ou no swipe do usuário para atualizar a lista.
-
No onItemSelected() se gato herda de animal, então gato é um animal, rs.
Fazendo com que a activity registrada, trate o evento.
-
Usamos a assyncTask() para realizar o processamento em segundo plano, sem interromper a IOThread.
Sabendo que outras threads não podem atualizar diretamente a IOThrad.
Sendo necessário utilizar 3 tipos de parâmetros, sendo eles:
1º Parâmetro de entrada da assyncTask(que vai ser usado no doInBackground())
2º Usado para mostrar o progresso de execução da thread.
3º Identifica o tipo de retorno da assyncTask(retornado no postExecute().
O único método obrigatório da assyncTask() é o doInBackground().
*/
public class VideoFragment extends Fragment {
    @Bind(R.id.list_video)
    ListView mListView;
    @Bind(R.id.swipe)
    SwipeRefreshLayout mSwipe;
    @Bind(R.id.empty)
    View mEmpty;
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
        mListView.setEmptyView(mEmpty);
        mListView.setAdapter(mAdapter);
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJson();
            }
        });
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mVideos.size() == 0 && (mTask == null)){
            loadJson();
        }else if(mTask != null && mTask.getStatus() == AsyncTask.Status.RUNNING){
            showProgress();
        }
    }

    private void loadJson(){
        ConnectivityManager cm = (ConnectivityManager)getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info != null && info.isConnected()){
            mTask = new VideoTask();
            mTask.execute();
        } else{
            mSwipe.setRefreshing(false);
            Toast.makeText(getActivity(), R.string.networkFail, Toast.LENGTH_SHORT).show();
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
                Response response = client.newCall(request).execute();
                String json = response.body().string();
                Gson gson = new Gson();
                return gson.fromJson(json, Search.class);
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
                mAdapter.notifyDataSetChanged();
                if(getResources().getBoolean(R.bool.tablet) && mVideos.size() > 0){
                    onItemSelected(0);
                }
            }
            mSwipe.setRefreshing(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
