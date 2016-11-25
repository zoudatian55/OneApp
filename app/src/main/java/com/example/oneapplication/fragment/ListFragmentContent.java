package com.example.oneapplication.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oneapplication.R;


public class ListFragmentContent extends Fragment {


    private TextView text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_fragment_content, container, false);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        text = ((TextView) view.findViewById(R.id.tv_dic));
        Bundle bundle = getArguments();
        if(bundle!=null) {
            String keywords = bundle.getString("keywords");
            text.setText(keywords);
        }
    }
}
