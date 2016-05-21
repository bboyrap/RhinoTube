package com.example.ultrabook.rhinotube;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.ultrabook.rhinotube.Model.Video;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_video, menu);
        return true;
    }

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
