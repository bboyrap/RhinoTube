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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ultrabook.rhinotube.model.Video;
import com.example.ultrabook.rhinotube.database.VideoDAO;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
/*
    Fragment de detalhe que mostra as informações do vídeo seleiconado na lista.
-
No onCreateView é carregado o arquivo de layout do fragment.
-
Usamos um método consrutor para criar uma instância do fragment, recebendo um vídeo como parâmetro.
-
Usamos o setArguments() para poder armazenar o vídeo e recupera-lo no onCreate com o getArguments().
*/
public class DetailFragment extends Fragment {
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
        args.putParcelable(Constant.EXTRA_VIDEO, p);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDao = new VideoDAO(getActivity());
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            Parcelable p = getArguments().getParcelable(Constant.EXTRA_VIDEO);
            mVideo = Parcels.unwrap(p);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        Glide.with(getContext()).load(mVideo.getThumbnail()).into(mImgDetail);
        mTextTitle.setText(mVideo.getTitle());
        mTextDescription.setText(mVideo.getDescription());
        return view;
    }

    private void changeIcon() {
        if(mDao.isFavorite(mVideo)){
            mMenu.setIcon(R.drawable.ic_favorite);
        }else{
            mMenu.setIcon(R.drawable.ic_unavorite);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_video, menu);
        mMenu = menu.findItem(R.id.favorite_icon);
        changeIcon();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.favorite_icon:
                if(mDao.isFavorite(mVideo)){
                    mDao.delete(mVideo);
                    Toast.makeText(getActivity(), R.string.removeFavorite, Toast.LENGTH_SHORT).show();
                }else{
                    mDao.insert(mVideo);
                    Toast.makeText(getActivity(), R.string.addFavorite, Toast.LENGTH_SHORT).show();
                }
                changeIcon();
                ((VideoApp)getActivity().getApplication()).getEventBus().post(mVideo);
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}