package com.example.oneapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.oneapplication.R;
import com.example.oneapplication.bean.MovieList;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/10/31 0031.
 */

public class MovieListAdapter extends BaseAdapter {

    private Context context;
    private List<MovieList.DataBean> list;
    private LayoutInflater inflater;

    public MovieListAdapter(Context context,List<MovieList.DataBean> list){
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.movie_list_item,null);
            holder=new ViewHolder();
            holder.imageView= (ImageView) convertView.findViewById(R.id.list_image);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        if(position%5==0){
            holder.imageView.setImageResource(R.drawable.movie_placeholder_0);
        }else if(position%5==1){
            holder.imageView.setImageResource(R.drawable.movie_placeholder_1);
        }else if(position%5==2){
            holder.imageView.setImageResource(R.drawable.movie_placeholder_2);
        }else if(position%5==3){
            holder.imageView.setImageResource(R.drawable.movie_placeholder_3);
        }else if(position%5==4){
            holder.imageView.setImageResource(R.drawable.movie_placeholder_4);
        }
        MovieList.DataBean bean=list.get(position);
        String cover = bean.getCover();
        Picasso.with(context).load(cover).into(holder.imageView);
        return convertView;
    }
    class ViewHolder{
        public ImageView imageView;
    }

}
