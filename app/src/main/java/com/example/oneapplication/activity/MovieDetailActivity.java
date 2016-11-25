package com.example.oneapplication.activity;

import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.net.Uri;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.oneapplication.R;
import com.example.oneapplication.adapter.MovieCommentAdapter;
import com.example.oneapplication.bean.MovieComment;
import com.example.oneapplication.bean.MovieDatail;
import com.example.oneapplication.bean.MovieDetailStory;
import com.example.oneapplication.fragment.ListFragmentContent;
import com.example.oneapplication.fragment.MoiveDicFragment;
import com.example.oneapplication.fragment.PhotoFragment;
import com.example.oneapplication.utils.Constant;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener{
    //新的网址
    public static String Deatil_first;
    public static String Deatil_STORY;
    public static String Deatil_COMMENT;
    private MediaPlayer mediaPlayer;

    //声明媒体播放器的屏幕
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private String detailcover;
    private String video;
    private ImageView imageView;
    private ImageView buyImageView;
    private ImageView shareImageView;
    private ImageView scoreImageview;
    private ImageView circilarImageView;
    private TextView username;
    private String user_name;
    private String web_url;
    private TextView date;
    private String input_date;
    private TextView count;
    private ImageView favorite;
    private int praisenum;
    private WebView webview;
    private String content;
    private String title;
    private TextView storytitle;
    private TextView toolbar;
    private ImageView backImageView;
    private ImageView grossImage;
    private ImageView stillImage;
    private ImageView plotImage;
    private TextView textdic;
    private String keywords;
    private String info;
    private List<String> photos;
    private ListView commentlistview;
    private MovieCommentAdapter adapter;
    private List<MovieComment.DataBean.DataBeans> datas;
    private String id;
    private boolean isLast;
    private String ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initView();
        toolbar = ((TextView) findViewById(R.id.tv_toobar));

        id = getIntent().getStringExtra("id");
        //传入的标题
        String titles = getIntent().getStringExtra("title");
        toolbar.setText(titles);
        //通过网络请求发送数据
        Deatil_first = Constant.BASE_URL + Constant.MOVIE_DETAIL + id + Constant.MOVIE_DETAIL_LIST;
        /**
         * 视频的网络请求
         */
        getNetWorkData(Deatil_first);

        //评分那里有进度条
        //分享那里有动画
        //故事详情的网络请求调用
        Deatil_STORY = Constant.BASE_URL + Constant.LIST_BASE_URL_LL + id + Constant.MOVIE_DETAIL_LIST_STORY;
        getNetWorkDataStory(Deatil_STORY);

        //评论的详情的网络请求
        Deatil_COMMENT= Constant.BASE_URL+ Constant.MOVIE_DETAIL_COMMENT+ id +"/0"+ Constant.MOVIE_DETAIL_COMMENT_LAST;
        getNetWorkDataComment(Deatil_COMMENT);

        //初始化SurfaceHolder
        surfaceHolder = surfaceView.getHolder();
        mediaPlayer = new MediaPlayer();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                holder.setFixedSize(getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight());

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
//                if (mediaPlayer != null) {
//                    mediaPlayer.stop();
//                    //释放资源
//                    mediaPlayer.release();


            }
        });

        //初始化播放器


        /**
         * 设置缓冲监听器
         * 设置媒体播放器的准备监听，当调用MediaPlayer.prepare方法是，MediaPlayer会进行准本工作
         * 当准备工作完成后，会回调此监听器的方法，在此方法中需要启动播放
         */
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //说明已经缓冲完毕，进行播放操作
                mediaPlayer.start();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动另一个Activity加载视频文件，把网址传入
//                Intent intent = new Intent();
//                    intent.putExtra("video", video);
//                    intent.setClass(MovieDetailActivity.this, VideoActivity.class);
//                    startActivity(intent);
                    try {
                    imageView.setVisibility(View.INVISIBLE);
                    surfaceView.setVisibility(View.VISIBLE);

                    //设置播放器的屏幕
                    mediaPlayer.setDisplay(surfaceHolder);

                    //设置播放器需要播放的视频源
                    mediaPlayer.setDataSource(MovieDetailActivity.this, Uri.parse(video));

                    //启动播放器缓冲操作
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mediaPlayer != null) {
                            if (mediaPlayer.isPlaying()) {
                                mediaPlayer.pause();
                            } else {
                                mediaPlayer.start();
                            }
                        }
                        break;
                }
                return true;
            }
        });


    }

    private void getNetWorkDataComment(String deatil_comment) {
        Request request=new Request.Builder().get().url(deatil_comment).build();
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(12,TimeUnit.SECONDS)
                .writeTimeout(8,TimeUnit.SECONDS)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MovieDetailActivity.this,"正在加载.....",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                MovieComment movieComment = new Gson().fromJson(result, MovieComment.class);
                datas = movieComment.getData().getData();
                //通过每一页的最后一个元素的id作为下一页的网址部分
                ID =  datas.get(datas.size() - 1).getId();
                //获取到最后一个数据的id，

                commentlistview.setOnScrollListener(new AbsListView.OnScrollListener() {
                   @Override
                   public void onScrollStateChanged(AbsListView view, int scrollState) {
                       if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && isLast){
                           getNetWorkDataComment(Constant.BASE_URL+ Constant.MOVIE_DETAIL_COMMENT+ id +"/"+ID+ Constant.MOVIE_DETAIL_COMMENT_LAST);
                           adapter.notifyDataSetChanged();
                       }

                   }

                   @Override
                   public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                     isLast=(firstVisibleItem+visibleItemCount==totalItemCount);
                   }
               });



                adapter = new MovieCommentAdapter(MovieDetailActivity.this, datas);

                commentlistview.post(new Runnable() {
                    @Override
                    public void run() {
                       commentlistview.setAdapter(adapter);
                        setListViewHeightBasedOnChildren(commentlistview);

                    }
                });

            }
        });
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 故事详情
     * @param deatil_story
     */
    private void getNetWorkDataStory(String deatil_story){
        Request request = new Request.Builder().get().url(deatil_story).build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(8, TimeUnit.SECONDS)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {



            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                MovieDetailStory detailStory = new Gson().fromJson(result, MovieDetailStory.class);
                List<MovieDetailStory.DataBean.DataBeanDetail> dataBeanDetails = detailStory.getData().getData();
                web_url = dataBeanDetails.get(0).getUser().getWeb_url();
                user_name = dataBeanDetails.get(0).getUser().getUser_name();
                input_date = dataBeanDetails.get(0).getInput_date();
                praisenum = dataBeanDetails.get(0).getPraisenum();
                content = dataBeanDetails.get(0).getContent();
                title = dataBeanDetails.get(0).getTitle();

                circilarImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        Picasso.with(MovieDetailActivity.this).load(web_url).into(circilarImageView);
                    }
                });
                username.post(new Runnable() {
                    @Override
                    public void run() {
                        username.setText(user_name);


                    }
                });
                date.post(new Runnable() {
                    @Override
                    public void run() {
                        date.setText(input_date);
                    }
                });
                count.post(new Runnable() {
                    @Override
                    public void run() {
                        count.setText(praisenum+"");
                    }
                });
                storytitle.post(new Runnable() {
                    @Override
                    public void run() {
                        storytitle.setText(title);
                    }
                });

                webview.post(new Runnable() {
                    @Override
                    public void run() {
                        webview.loadDataWithBaseURL(null,content,"text/html",  "utf-8", null);

                    }
                });

            }
        });



    }

    private void initView() {

        imageView = ((ImageView) findViewById(R.id.sfv_image));
        buyImageView = ((ImageView) findViewById(R.id.image_buy));
        shareImageView = ((ImageView) findViewById(R.id.image_buy));
        scoreImageview = ((ImageView) findViewById(R.id.sroce));
        circilarImageView = (ImageView) findViewById(R.id.circularImage);
        username = ((TextView) findViewById(R.id.username));
        date = ((TextView) findViewById(R.id.date));
        surfaceView= ((SurfaceView) findViewById(R.id.sfv));

        favorite = ((ImageView) findViewById(R.id.image_favorite));
        //获取图片的点击状态或者是现在是哪张图，然后设置相应的分数在加一
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favorite.isClickable()){
                    favorite.setImageResource(R.drawable.laud_pressed);

                }
                else{
                    favorite.setImageResource(R.drawable.laud_selected);
                }
            }
        });
        count = ((TextView) findViewById(R.id.count));
        webview = ((WebView) findViewById(R.id.webview_dic));
        storytitle = ((TextView) findViewById(R.id.story_title));
        backImageView = ((ImageView) findViewById(R.id.back_image));
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        grossImage = ((ImageView) findViewById(R.id.gross_image));
        stillImage = ((ImageView) findViewById(R.id.still_image));
        plotImage = ((ImageView) findViewById(R.id.plot_image));
        textdic = ((TextView) findViewById(R.id.text_dic));

        grossImage.setOnClickListener(this);
        stillImage.setOnClickListener(this);
        plotImage.setOnClickListener(this);
        commentlistview = ((ListView) findViewById(R.id.comment_listview));


    }







    /**
     * 头部视频部分的网络请求
     * @param deatil_first
     */
    private void getNetWorkData(String deatil_first) {

        Request request = new Request.Builder().get().url(deatil_first).build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(8, TimeUnit.SECONDS)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                MovieDatail datail = new Gson().fromJson(result, MovieDatail.class);
                detailcover = datail.getData().getDetailcover();
                video = datail.getData().getVideo();
                keywords = datail.getData().getKeywords();
                info = datail.getData().getInfo();
                photos = datail.getData().getPhoto();
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        Picasso.with(MovieDetailActivity.this).load(detailcover).into(imageView);
                    }
                });


                //处理surfaceview的加载
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gross_image:
                showListFragment();
            break;
            case R.id.still_image:
                showPhotoFragment();
            break;
            case R.id.plot_image:
                showMovieDic();
            break;
            default:showListFragment();
                break;

        }
    }

    private void showMovieDic() {
        stillImage.setImageResource(R.drawable.still_default);
        plotImage.setImageResource(R.drawable.plot_selected);
        grossImage.setImageResource(R.drawable.gross_default);
        textdic.setText(R.string.movie_dic_moviedic);
        FragmentManager fm = getSupportFragmentManager();
        //3 启动碎片事务
        FragmentTransaction ft = fm.beginTransaction();
        MoiveDicFragment mdf=new MoiveDicFragment();
        //构造一个Bundler对象，并put相关数据
        Bundle bundle = new Bundle();
        bundle.putString("info",info);
        mdf.setArguments(bundle);
        ft.replace(R.id.fragment, mdf);
        //5 提交事务
        ft.commit();


    }

    private void showPhotoFragment() {
        stillImage.setImageResource(R.drawable.still_selected);
        plotImage.setImageResource(R.drawable.plot_default);
        grossImage.setImageResource(R.drawable.gross_default);
        textdic.setText(R.string.movie_dic_moviephoto);
        FragmentManager fm = getSupportFragmentManager();
        //3 启动碎片事务
        FragmentTransaction ft = fm.beginTransaction();
        PhotoFragment photo=new PhotoFragment();

        //构造一个Bundler对象，并put相关数据
        Bundle bundle = new Bundle();
        ArrayList<String> list=new ArrayList<>();
        list.addAll(photos);
         bundle.putStringArrayList("photo",list);
        photo.setArguments(bundle);
        ft.replace(R.id.fragment, photo);
        //5 提交事务
        ft.commit();

    }

    private void showListFragment() {
        grossImage.setImageResource(R.drawable.gross_selected);
        stillImage.setImageResource(R.drawable.still_default);
        plotImage.setImageResource(R.drawable.plot_default);
        textdic.setText(R.string.movie_dic_movielist);
        FragmentManager fm = getSupportFragmentManager();
        //3 启动碎片事务
        FragmentTransaction ft = fm.beginTransaction();
        ListFragmentContent fc = new ListFragmentContent();

        //构造一个Bundler对象，并put相关数据
        Bundle bundle = new Bundle();
        bundle.putString("keywords",keywords);
        fc.setArguments(bundle);
        ft.replace(R.id.fragment, fc);
        //5 提交事务
        ft.commit();

    }
}
