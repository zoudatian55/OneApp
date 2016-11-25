package com.example.oneapplication.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oneapplication.R;
import com.example.oneapplication.bean.HomeBean;
import com.squareup.picasso.Picasso;

public class GridActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView titleText;
    private TextView authorText;
    private TextView contentText;
    private TextView dateText;
    private TextView laudText;
    private ImageView backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        Intent intent =getIntent();
        HomeBean.DataBean dataBean = (HomeBean.DataBean) intent.getSerializableExtra("dataBean");
        String date = intent.getStringExtra("date");
        imageView = (ImageView) findViewById(R.id.image_home_grid);
        Picasso.with(imageView.getContext()).load(dataBean.getHp_img_url()).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(GridActivity.this);
                ImageView image = new ImageView(GridActivity.this);
                image.setImageDrawable(imageView.getDrawable());
                image.setAdjustViewBounds(true);
                dialog.setView(image);
                dialog.show();
            }
        });
        titleText = (TextView) findViewById(R.id.title_home_grid);
        titleText.setText(dataBean.getHp_title());
        authorText = (TextView) findViewById(R.id.author_home_grid);
        authorText.setText(dataBean.getHp_author());
        contentText = (TextView) findViewById(R.id.content_home_grid);
        contentText.setText(dataBean.getHp_content());
        dateText = (TextView) findViewById(R.id.date_home_grid);
        dateText.setText(date);
        laudText = (TextView) findViewById(R.id.grid_laud);
        laudText.setText(dataBean.getSharenum()+"");
        backImage = (ImageView) findViewById(R.id.back_grid);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
