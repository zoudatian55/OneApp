package com.example.oneapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.oneapplication.R;
import com.example.oneapplication.bean.MovieComment;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/11/1 0001.
 */

public class MovieCommentAdapter extends BaseAdapter {
    private Context context;
    private List<MovieComment.DataBean.DataBeans> list;
    private LayoutInflater inflater;
    public MovieCommentAdapter(Context context, List<MovieComment.DataBean.DataBeans> list){
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
            convertView=inflater.inflate(R.layout.comment_item,null);
            holder=new ViewHolder();
            holder.leftImage= (ImageView) convertView.findViewById(R.id.comment_circularImage);
            holder.uernametext= (TextView) convertView.findViewById(R.id.comment_username);
            holder.datetext= (TextView) convertView.findViewById(R.id.comment_date);
            holder.counttext= (TextView) convertView.findViewById(R.id.comment_count);
            holder.contenttext= (TextView) convertView.findViewById(R.id.comment_dic);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        MovieComment.DataBean.DataBeans comment=list.get(position);
        if(comment.getUser().getWeb_url()!=null){
            Picasso.with(context).load(comment.getUser().getWeb_url()).into(holder.leftImage);
        }
        holder.uernametext.setText(comment.getUser().getUser_name());
        holder.counttext.setText(comment.getPraisenum()+"");
        holder.contenttext.setText(comment.getContent());
        holder.datetext.setText(comment.getInput_date());
        return convertView;
    }
    class ViewHolder{
        public ImageView leftImage;
        public TextView uernametext;
        public TextView datetext;
        public TextView contenttext;
        public TextView counttext;
    }
}
