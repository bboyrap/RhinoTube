package com.example.ultrabook.rhinotube;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ultrabook.rhinotube.Model.Video;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VideoActivity extends AppCompatActivity
    implements TouchVideoListner {

    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);

        mViewPager.setAdapter(new VideoPager(getSupportFragmentManager()));
    }

    class VideoPager extends FragmentPagerAdapter{

        public VideoPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //Dependendo da posição retornar o fragment para a aba
            if(position == 0){
                return new VideoFragment();
            }
            return new FavoriteFragment();
        }
        //Metodo para definir a quantidade de abas do viewPager
        //tentar alterar para três abas adicionando o player[extra]
        @Override
        public int getCount() {
            return 2;
        }
    }


    @Override
    public void videoWasClicked(Video video) {
        if(getResources().getBoolean(R.bool.tablet)){
            DetailFragment df = DetailFragment.newInstance(video);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_detaill, df, Constant.DETAIL)
                    .commit();
        }else {
            Intent it = new Intent(this, DetailActivity.class);
            Parcelable p = Parcels.wrap(video);
            it.putExtra(Constant.EXTRA_VIDEO, p);
            startActivity(it);
        }
    }
}
