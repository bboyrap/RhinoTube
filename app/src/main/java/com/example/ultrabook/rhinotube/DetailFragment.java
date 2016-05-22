package com.example.ultrabook.rhinotube;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ultrabook.rhinotube.Model.Video;
import com.example.ultrabook.rhinotube.database.VideoDAO;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment {
    private static final String EXTRA_VIDEO = "VIDEO";

    @Bind(R.id.imgDetail)
    ImageView mImgDetail;
    @Bind(R.id.textDetailTitle)
    TextView mTextTitle;
    @Bind(R.id.textDetailDescription)
    TextView mTextDescription;

    private MenuItem mMenu;

    VideoDAO mDao;

    private Video mVideo;


    public static DetailFragment newInstance(Video video) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        Parcelable p = Parcels.wrap(video);
        args.putParcelable(EXTRA_VIDEO, p);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDao = new VideoDAO(getActivity());

        //Necessário para a chamada do onCreateOptionsMenu
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            Parcelable p = getArguments().getParcelable(EXTRA_VIDEO);
            mVideo = Parcels.unwrap(p);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_detail, container, false);

        ButterKnife.bind(this, view);
        Glide.with(getContext()).load(mVideo.getThumbnail()).into(mImgDetail);
        mTextTitle.setText(mVideo.getTitle());
        mTextDescription.setText(mVideo.getDescription());
        return view;
    }

    private void changeIcon() {
        if(mDao.isFavorite(mVideo)){
            mMenu.setIcon(R.drawable.ic_add);
        }else{
            mMenu.setIcon(R.drawable.ic_remove);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_video, menu);
        //Pesquisar como trocar Meno
        mMenu = menu.findItem(R.id.favorite_icon);
        changeIcon();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.favorite_icon:
                if(mDao.isFavorite(mVideo)){
                    //Se for favorito remove
                    mDao.delete(mVideo);
                }else{
                    //Senao adiciona
                    mDao.insert(mVideo);
                }
                changeIcon();
                ((VideoApp)getActivity().getApplication()).getEventBus().post(mVideo);
                return false;
        }
        return super.onOptionsItemSelected(item);
    }
}


