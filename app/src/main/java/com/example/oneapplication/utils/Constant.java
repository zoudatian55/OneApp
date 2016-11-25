package com.example.oneapplication.utils;

/**
 * Created by 轩 on 2016/10/31.
 */

public class Constant {
    //首页接口
    public final static String HOME_MONTH_URL = "api/hp/bymonth/2016-10-01%2000:00:00";

    //阅读界面的图片地址
    public final static String READING_IMAGE_URL = "http://v3.wufazhuce.com:8000/api/reading/carousel/?platform=android&version=3.5.0";

    //阅读界面的index地址
    public final static String READING_INDEX_URL = "http://v3.wufazhuce.com:8000/api/reading/index/?platform=android&version=3.5.0";

    //基本网址
    public static final String BASE_URL="http://v3.wufazhuce.com:8000/";
    //电影版本号
    public static final String VERSON="3.5.0";
    public static final String UESR_ID="7432720";
    public static final String  PLATFORM="android";
    public static final String LIST_BASE_URL="api/movie/list/";
    public static final String LIST_BASE_URL_LL="api/movie/";

    public static final String MOVIE_DETAIL="api/movie/detail/";
    public static final String MOVIE_DETAIL_LIST="?user_id=7432720&version=3.5.0&platform=android";
    public static final String MOVIE_DETAIL_LIST_STORY="/story/1/0?user_id=7432720&version=3.5.0&platform=android";
    public static final String MOVIE_DETAIL_COMMENT="api/comment/praiseandtime/movie/";
    public static final String MOVIE_DETAIL_COMMENT_LAST="?user_id=7432720&version=3.5.0&platform=android";

    //域名
    public static  final String REQUEST_MUSIC_BASE_URL =
            "http://v3.wufazhuce.com:8000/api/music/detail/";
    public static  final String REQUEST_MUSIC_DETAIL_URL =
            "?version=3.5.0&platform=android";

    public static final String BYMONTH_COMMON_HEAD =
            "http://v3.wufazhuce.com:8000/api/music/bymonth/2016-";
    public static final String BYMONTH_COMMON_FOOT =
            "-01%2000:00:00?version=3.5.0&platform=android";

    public static final String COMMENT_URL_HEAD =
            "http://v3.wufazhuce.com:8000/api/comment/praiseandtime/music/";
    public static final String COMMENT_URL_FOOT =
            "/0?version=3.5.0&platform=android";
}
