package com.example.ultrabook.rhinotube;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ultrabook.rhinotube.Model.Video;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Video video = Parcels.unwrap(getIntent().getParcelableExtra(Constant.EXTRA_VIDEO));

        DetailFragment df = DetailFragment.newInstance(video);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_detaill, df, "detail")
                .commit();
    }
}
