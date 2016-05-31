package com.example.ultrabook.rhinotube;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.ultrabook.rhinotube.model.Video;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
/*
    Activity principal da aplicação, responsável por controlar os fragments.
-
Usamos o setSupportActionBar() já que retiramos a ActionBar da aplicação substituindo pela Toolbar.
-
O setupWithViewPager(), é usado para adicionar o viewPager() as abas.
-
Usamos o viewPager para vai trabalhar em conjunto com o tabLayout.
-
No videoWasClicked() verificamos se o aparelho é um tablet, para poder abrir uma novo fragment.
-
O VideoPager extend de FragmentPagerAdapter pois cada pagina vai ser um fragment.
*/
public class VideoActivity extends AppCompatActivity
    implements TouchVideoListner {
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.toolBar)
    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        setSupportActionBar(mToolBar);
        mViewPager.setAdapter(new VideoPager(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private class VideoPager extends FragmentPagerAdapter{
        public VideoPager(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                return new VideoFragment();
            }
            return new FavoriteFragment();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0){
                return getString(R.string.tabWeb);
            }
            return getString(R.string.tabFavorite);
        }

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
