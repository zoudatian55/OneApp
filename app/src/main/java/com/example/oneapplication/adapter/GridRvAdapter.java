package com.example.oneapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oneapplication.R;
import com.example.oneapplication.bean.HomeBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by 轩 on 2016/11/1.
 */

public class GridRvAdapter extends RecyclerView.Adapter<GridRvAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<HomeBean.DataBean> list;
    private String date;

    public void setCallback(GridItemClickedCallback callback) {
        this.callback = callback;
    }

    public interface GridItemClickedCallback{
        public void GridItemClicked(int position);
    }

    private GridItemClickedCallback callback;

    public GridRvAdapter(Context context,List<HomeBean.DataBean> list,String date){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.date = date;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.grid_layout,null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HomeBean.DataBean dataBean = list.get(position);
        Picasso.with(holder.imageView.getContext()).load(dataBean.getHp_img_url()).into(holder.imageView);
        holder.titleText.setText(dataBean.getHp_title());
        holder.dateText.setText((list.size()-position)+" "+date);
        holder.contentText.setText(dataBean.getHp_content());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView titleText;
        public TextView dateText;
        public TextView contentText;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_grid);
            titleText = (TextView) itemView.findViewById(R.id.title_grid);
            dateText = (TextView) itemView.findViewById(R.id.date_grid);
            contentText = (TextView) itemView.findViewById(R.id.content_grid);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null){
                        callback.GridItemClicked(getLayoutPosition());
                    }
                }
            });
        }
    }
}
