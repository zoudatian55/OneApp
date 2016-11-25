package com.example.oneapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/1 0001.
 */

public class MovieComment {



    private int res;


    private DataBean data;

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int count;


        private List<DataBeans> data;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<DataBeans> getData() {
            return data;
        }

        public void setData(List<DataBeans> data) {
            this.data = data;
        }

        public static class DataBeans {
            private String id;
            private String quote;
            private String content;
            private int praisenum;
            private String device_token;
            private String del_flag;
            private String reviewed;
            private String user_info_id;
            private String input_date;
            private String created_at;
            private String updated_at;

            private UserBean user;
            private Object touser;
            private String score;
            private int type;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getQuote() {
                return quote;
            }

            public void setQuote(String quote) {
                this.quote = quote;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getPraisenum() {
                return praisenum;
            }

            public void setPraisenum(int praisenum) {
                this.praisenum = praisenum;
            }

            public String getDevice_token() {
                return device_token;
            }

            public void setDevice_token(String device_token) {
                this.device_token = device_token;
            }

            public String getDel_flag() {
                return del_flag;
            }

            public void setDel_flag(String del_flag) {
                this.del_flag = del_flag;
            }

            public String getReviewed() {
                return reviewed;
            }

            public void setReviewed(String reviewed) {
                this.reviewed = reviewed;
            }

            public String getUser_info_id() {
                return user_info_id;
            }

            public void setUser_info_id(String user_info_id) {
                this.user_info_id = user_info_id;
            }

            public String getInput_date() {
                return input_date;
            }

            public void setInput_date(String input_date) {
                this.input_date = input_date;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public Object getTouser() {
                return touser;
            }

            public void setTouser(Object touser) {
                this.touser = touser;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public static class UserBean {
                private String user_id;
                private String user_name;
                private String web_url;

                public String getUser_id() {
                    return user_id;
                }

                public void setUser_id(String user_id) {
                    this.user_id = user_id;
                }

                public String getUser_name() {
                    return user_name;
                }

                public void setUser_name(String user_name) {
                    this.user_name = user_name;
                }

                public String getWeb_url() {
                    return web_url;
                }

                public void setWeb_url(String web_url) {
                    this.web_url = web_url;
                }
            }
        }
    }
}
