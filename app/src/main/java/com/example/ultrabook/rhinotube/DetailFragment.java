package com.example.ultrabook.rhinotube;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ultrabook.rhinotube.Model.Video;

import org.parceler.Parcels;
import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment {
    private static final String EXTRA_VIDEO = "VIDEO";

    @Bind(R.id.text_title)
    TextView mTextTitle;
    @Bind(R.id.text_description)
    TextView mTextDescription;

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
        mTextTitle.setText(mVideo.getTitle());
        mTextDescription.setText(mVideo.getDescription());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}


