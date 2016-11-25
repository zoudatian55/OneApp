package com.example.oneapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oneapplication.R;

import java.util.List;

/**
 * Created by 轩 on 2016/11/1.
 */

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {

    public void setItemClickCallback(ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public interface ItemClickCallback{
        public void onItemClicked(int position);
    }

    private ItemClickCallback itemClickCallback;

    private Context context;
    private LayoutInflater inflater;
    private List<String> list;

    public RvAdapter(Context context,List<String> list){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = inflater.inflate(R.layout.home_list_layout,null);
        return new MyViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String date = list.get(position);
        holder.textView.setText(date);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_list);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickCallback != null){
                        itemClickCallback.onItemClicked(getLayoutPosition());
                    }
                }
            });
        }
    }
}
