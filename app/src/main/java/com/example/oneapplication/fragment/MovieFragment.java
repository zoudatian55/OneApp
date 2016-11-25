package com.example.oneapplication.fragment;

import android.content.Intent;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.oneapplication.R;
import com.example.oneapplication.activity.MovieDetailActivity;
import com.example.oneapplication.adapter.MainMovieListAdapter;
import com.example.oneapplication.bean.MovieList;
import com.example.oneapplication.utils.Constant;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MovieFragment extends Fragment {


    private ListView listview;
    private List<MovieList.DataBean> data;
    private int page=0;
    private boolean isBottom;
    private int[] ids = {0, 130, 107, 94, 70, 47, 27};

    private MainMovieListAdapter mainadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_movie, null);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listview = ((ListView) view.findViewById(R.id.movie_list));
        TextView title = (TextView) view.findViewById(R.id.text_main);
        title.setText("电影");
        //第一次加载的数据
      String firsturl= Constant.BASE_URL+Constant.LIST_BASE_URL+ids[page]+Constant.MOVIE_DETAIL_LIST;
      data=getNetWork(firsturl);


        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && isBottom) {
                     page++;
                    List<MovieList.DataBean> been = getNetWork(Constant.BASE_URL + Constant.LIST_BASE_URL + ids[page] + Constant.MOVIE_DETAIL_LIST);
                    MovieFragment.this.data.addAll(been);
                    mainadapter.notifyDataSetChanged();
                 }

            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isBottom=(firstVisibleItem+visibleItemCount==totalItemCount);
            }
        });


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieList.DataBean bean = data.get(position);
                String beanId = bean.getId();
                Intent intent=new Intent();
                intent.putExtra("id",beanId);
                String title = bean.getTitle();
                intent.putExtra("title",title);
                intent.setClass(getContext(),MovieDetailActivity.class);
                startActivity(intent);
            }
        });


    }
    public List<MovieList.DataBean> getNetWork(String url){
        Request request=new Request.Builder().get().url(url).build();
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(12,TimeUnit.SECONDS)
                .writeTimeout(8,TimeUnit.SECONDS)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                data = new Gson().fromJson(result, MovieList.class).getData();
                listview.post(new Runnable() {
                    @Override
                    public void run() {
                        mainadapter = new MainMovieListAdapter(getContext(),data);
                        listview.setAdapter(mainadapter);


                    }
                });

            }
        });
    return data;
    }
}
