package com.example.oneapplication.bean;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/10/29 0029.
 */

public class MovieList {

    private int res;
    private List<DataBean> data;
    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }
    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String title;
        private String verse;
        private String verse_en;
        private Object score;
        private String revisedscore;
        private String releasetime;
        private String scoretime;
        private String cover;
        private int servertime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVerse() {
            return verse;
        }

        public void setVerse(String verse) {
            this.verse = verse;
        }

        public String getVerse_en() {
            return verse_en;
        }

        public void setVerse_en(String verse_en) {
            this.verse_en = verse_en;
        }

        public Object getScore() {
            return score;
        }

        public void setScore(Object score) {
            this.score = score;
        }

        public String getRevisedscore() {
            return revisedscore;
        }

        public void setRevisedscore(String revisedscore) {
            this.revisedscore = revisedscore;
        }

        public String getReleasetime() {
            return releasetime;
        }

        public void setReleasetime(String releasetime) {
            this.releasetime = releasetime;
        }

        public String getScoretime() {
            return scoretime;
        }

        public void setScoretime(String scoretime) {
            this.scoretime = scoretime;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }



        public int getServertime() {
            return servertime;
        }

        public void setServertime(int servertime) {
            this.servertime = servertime;
        }
    }
}
