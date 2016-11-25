package com.example.oneapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.oneapplication.R;
import com.example.oneapplication.bean.BymonthBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/10/31.
 */
public class BymonthAdapter extends BaseAdapter{

    private Context context;
    private BymonthBean bymonthBean;
    private LayoutInflater inflater;
    private List<BymonthBean.DataBean> list;

    public BymonthAdapter(Context context, BymonthBean bymonthBean) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.bymonthBean = bymonthBean;
        list = bymonthBean.getData();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.bymonth_song_item_list,null);
            holder = new ViewHolder();

            holder.singerImg = (ImageView) convertView.findViewById(R.id.iv_singer_img);
            holder.songName = (TextView) convertView.findViewById(R.id.tv_song_name);
            holder.singerName = (TextView) convertView.findViewById(R.id.tv_singer_name);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //加载图片到ImageView控件上
        Picasso.with(context)
                .load(list.get(position).getAuthor().getWeb_url())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.singerImg);

        holder.songName.setText(list.get(position).getTitle());
        holder.singerName.setText(list.get(position).getAuthor().getUser_name());

        return convertView;
    }

    class ViewHolder{
        public ImageView singerImg;
        public TextView songName;
        public TextView singerName;
    }
}
