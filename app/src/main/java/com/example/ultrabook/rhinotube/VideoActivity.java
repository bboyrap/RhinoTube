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

import com.example.ultrabook.rhinotube.Model.Video;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
/*
!Actvity Principal da execução do aplicativo!
-
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

        //usado para adicionar a ToolBar como barra de ações,
        //já que desativamos a actionBar manualmente no manifest
        setSupportActionBar(mToolBar);

        //Colocando o item na toolbar para adcionar aos favoritos.


        //Adapter para trazer os fragments para o viewPager
        mViewPager.setAdapter(new VideoPager(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    class VideoPager extends FragmentPagerAdapter{

        public VideoPager(FragmentManager fm) {
            super(fm);
        }

        //Onde intancia as páginas do viewPager
        @Override
        public Fragment getItem(int position) {
            //Instancia a video fragment (json)
            if(position == 0){
                return new VideoFragment();
            }else if(position == 1 ){
                return new FavoriteFragment();
            }
            return null;

            //instancia a favorite fragment (SQLite)
        }

        //Metodo necessário para retornar o titulo referente a cada aba do TabLayout
        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0){
                return getString(R.string.tabWeb);
            }else if(position == 1){
                return getString(R.string.tabFavorite);
            }
            return getString(R.string.tabSearch);
        }

        //Quantidade de abas do ViewPager
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
