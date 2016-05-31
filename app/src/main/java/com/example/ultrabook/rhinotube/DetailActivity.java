package com.example.ultrabook.rhinotube;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.ultrabook.rhinotube.model.Video;

import org.parceler.Parcels;
/*
    Activity que gerencia os fragments de detalhe, não é usada em tablets
-
Tem o papel de gerenciar os fragments que serão exibidos ao usuário
*/
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Video video = Parcels.unwrap(getIntent().getParcelableExtra(Constant.EXTRA_VIDEO));
        DetailFragment df = DetailFragment.newInstance(video);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_detaill, df, Constant.DETAIL)
                .commit();
    }
}
