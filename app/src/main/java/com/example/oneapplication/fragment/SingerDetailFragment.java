package com.example.oneapplication.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.oneapplication.R;
import com.example.oneapplication.bean.MusicBean;
import com.squareup.picasso.Picasso;

public class SingerDetailFragment extends Fragment {


    private ImageView singerDetailImage;
    private TextView singerNameTv;
    private TextView singerIntroduceTv;
    private MusicBean musicBean;

    public static SingerDetailFragment getInstance(MusicBean musicBean){
        SingerDetailFragment singerDetailFragment = new SingerDetailFragment();
        Bundle bundle = new Bundle();

        bundle.putSerializable("data",musicBean);
        singerDetailFragment.setArguments(bundle);

        return singerDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        musicBean = ((MusicBean) bundle.getSerializable("data"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_singer_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        singerDetailImage.post(new Runnable() {
            @Override
            public void run() {
                Picasso.with(getContext())
                        .load(musicBean.getData().getAuthor().getWeb_url())
                        .into(singerDetailImage);

                singerNameTv.setText(musicBean.getData().getAuthor().getUser_name());
                singerIntroduceTv.setText(musicBean.getData().getAuthor().getDesc());
            }
        });

    }

    private void initViews(View view) {
        singerDetailImage = ((ImageView) view.findViewById(R.id.iv_singer_detail));
        singerNameTv = ((TextView) view.findViewById(R.id.tv_singer_detail));
        singerIntroduceTv = ((TextView) view.findViewById(R.id.tv1_singer_detail));

    }
}
