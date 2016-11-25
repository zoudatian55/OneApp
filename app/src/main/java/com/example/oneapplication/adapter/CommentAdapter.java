package com.example.oneapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oneapplication.R;
import com.example.oneapplication.bean.CommentBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */
public class CommentAdapter extends BaseAdapter {

    private Context context;
    private CommentBean commentBean;
    private LayoutInflater inflater;
    private List<CommentBean.DataBean.DataBeans> data;

    public CommentAdapter(Context context, CommentBean commentBean) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.commentBean = commentBean;
        data = commentBean.getData().getData();
    }

    @Override
    public int getCount() {
        return data == null?0:data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.comment_list_item_layout,null);
            holder = new ViewHolder();

            holder.imageView = ((ImageView) convertView.findViewById(R.id.iv_comment_item));
            holder.textView = ((TextView) convertView.findViewById(R.id.tv_comment_item));
            holder.textView2 = ((TextView) convertView.findViewById(R.id.tv2_comment_item));

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context)
                .load(data.get(position).getUser().getWeb_url())
                .placeholder(R.drawable.bg04)
                .into(holder.imageView);
        holder.textView.setText(data.get(position).getUser().getUser_name());
        holder.textView2.setText(data.get(position).getContent());

        return convertView;
    }

    class ViewHolder{
        public ImageView imageView;
        public TextView textView;
        public TextView textView2;
    }
}
