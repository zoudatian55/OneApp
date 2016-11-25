package com.example.oneapplication.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.oneapplication.R;
import com.example.oneapplication.activity.SingerDetailActivity;
import com.example.oneapplication.adapter.CommentAdapter;
import com.example.oneapplication.bean.BymonthBean;
import com.example.oneapplication.bean.CommentBean;
import com.example.oneapplication.bean.MusicBean;
import com.example.oneapplication.utils.Constant;
import com.example.oneapplication.utils.MyApp;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment {

    public static final String ID_KEY = "key";
    private MusicBean musicBean;
    private TextView leftTv;
    private TextView centerTv;
    private TextView rightTv;
    private ImageView headImage;
    private WebView mainContentTv;
    private ImageView videoImage;
    private TextView videoTv;
    private TextView videoTv1;
    private TextView videoTvDate;
    private ImageView ivVideoPlayer;
    private BymonthBean bymonthBean;
    private ProgressDialog progressDialog;
    private ListView listView;

    public static MusicFragment getInstance(int id){
        MusicFragment musicFragment = new MusicFragment();
        Bundle bundle = new Bundle();

        bundle.putInt(ID_KEY,id);
        musicFragment.setArguments(bundle);

        return musicFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            int id = bundle.getInt(ID_KEY);
            Log.e("tag","id:-----" + id);
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("提示：");
            progressDialog.setMessage("正在刷新...");
            progressDialog.show();

            getNetworkData(id);

        }
    }

   private void getNetworkData2(String id) {
        Request request =
                new Request.Builder().get().url(Constant.COMMENT_URL_HEAD + id + Constant.COMMENT_URL_FOOT).build();
        MyApp.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("tag","网络请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result2 = response.body().string();

                if (result2 != null){
                    CommentBean commentBean = new Gson().fromJson(result2, CommentBean.class);

                    final CommentAdapter adapter = new CommentAdapter(getActivity(),commentBean);
                    listView.post(new Runnable() {
                        @Override
                        public void run() {
                             listView.setAdapter(adapter);

                            //解决ScrollView里面嵌套ListView的问题
//                            setListViewHeightBasedOnChildren(listView);
                        }
                    });
                }
            }
        });
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }


    private void getNetworkData(int id) {
        String url = Constant.REQUEST_MUSIC_BASE_URL + id + Constant.REQUEST_MUSIC_DETAIL_URL;
        //创建请求
        final Request request = new Request.Builder().get()
                .url(url)
                .build();
        Log.e("tag","URL---" + url);
        //发送网络异步请求
        MyApp.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("tag","网络请求失败！！！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                progressDialog.dismiss();
                //网络请求成功获取的数据
                String result = response.body().string();
                //解析数据并生成相应的bean类
                if (result != null){
                    musicBean = new Gson().fromJson(result, MusicBean.class);
                    Log.e("tag","musicBean---" + musicBean.getData().getId());

                    //位置很重要啊
                    getNetworkData2(musicBean.getData().getId());

                    leftTv.post(new Runnable() {
                        @Override
                        public void run() {
                            Picasso.with(getContext())
                                .load(musicBean.getData().getCover())
                                .placeholder(R.drawable.bg04)
                                .into(headImage);

                            //播放器布局中网络数据的填充
                            Picasso.with(getContext())
                                    .load(musicBean.getData().getAuthor().getWeb_url())
                                    .placeholder(getResources().getDrawable(R.drawable.bg04))
                                    .into(videoImage);

                            videoTv.setText(musicBean.getData().getAuthor().getUser_name());

                            videoTv1.setText(musicBean.getData().getTitle());
                            videoTvDate.setText(musicBean.getData().getMaketime());

                            leftTv.setText(musicBean.getData().getPraisenum()+"");
                            centerTv.setText(musicBean.getData().getCommentnum()+"");
                            rightTv.setText(musicBean.getData().getSharenum()+"");

//                            mainContentTv.setText(musicBean.getData().getStory());
                            //设置主题内容
                            String story = musicBean.getData().getStory();

//                            story = story.replaceAll("&", "");
                            story = story.replaceAll("quot;", "\"");
//                            story = story.replaceAll("lt;", "<");
//                            story = story.replaceAll("gt;", ">");

                            mainContentTv.loadDataWithBaseURL(null, story, "text/html", "utf-8", null);

                        }
                    });
                }

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);


        //设置监听事件
        videoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到另一个activity中
                Intent intent = new Intent(getContext(),SingerDetailActivity.class);
                intent.putExtra("data",musicBean);
                startActivity(intent);
            }
        });

    }


    private void initViews(View view) {
        leftTv = (TextView) view.findViewById(R.id.tv_left);
        centerTv = (TextView) view.findViewById(R.id.tv_center);
        rightTv = (TextView) view.findViewById(R.id.tv_right);

        headImage = ((ImageView) view.findViewById(R.id.image_fg_music_header));
        mainContentTv = ((WebView) view.findViewById(R.id.main_content_fg));

        videoImage = ((ImageView) view.findViewById(R.id.video_iv));
        videoTv = ((TextView) view.findViewById(R.id.video_tv));
        videoTv1 = ((TextView) view.findViewById(R.id.video_tv1));
        videoTvDate = ((TextView) view.findViewById(R.id.video_tv_date));
        ivVideoPlayer = ((ImageView) view.findViewById(R.id.iv_video_player));

        listView = ((ListView) view.findViewById(R.id.lv_fg_music_comment));

    }


}
