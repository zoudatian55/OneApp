package com.example.oneapplication.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oneapplication.R;
import com.example.oneapplication.bean.ReadingIndexBean;

import java.util.List;

/**
 * Created by 轩 on 2016/11/7.
 */

public class ReadingIndexAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ReadingIndexBean.DataBean dataBean;
    private List<ReadingIndexBean.DataBean.EssayBean> essayBeanList;
    private List<ReadingIndexBean.DataBean.SerialBean> serialBeanList;
    private List<ReadingIndexBean.DataBean.QuestionBean> questionBeanList;

    public ReadingIndexAdapter(Context context,ReadingIndexBean.DataBean dataBean){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.dataBean = dataBean;
        essayBeanList = dataBean.getEssay();
        serialBeanList = dataBean.getSerial();
        questionBeanList = dataBean.getQuestion();
    }

    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (position == 0){
            View view = inflater.inflate(R.layout.right_refresh,null);
            container.addView(view);
            return view;
        }else if (position == 11){
            View view = inflater.inflate(R.layout.left_skip,null);
            container.addView(view);
            return view;
        }else {
            View view = inflater.inflate(R.layout.reading_pager_layout,null);
            ReadingIndexBean.DataBean.EssayBean essayBean = essayBeanList.get(position-1);
            ReadingIndexBean.DataBean.SerialBean serialBean = serialBeanList.get(position-1);
            ReadingIndexBean.DataBean.QuestionBean questionBean = questionBeanList.get(position-1);

            TextView essayTitle = (TextView) view.findViewById(R.id.title_essay_reading_pager);
            essayTitle.setText(essayBean.getHp_title());
            TextView essayAuthor = (TextView) view.findViewById(R.id.author_essay_reading_pager);
            essayAuthor.setText(essayBean.getAuthor().get(0).getUser_name());
            TextView essayGuideWord = (TextView) view.findViewById(R.id.guideword_essay_reading_pager);
            essayGuideWord.setText(essayBean.getGuide_word());

            TextView serialTitle = (TextView) view.findViewById(R.id.title_serial_reading_pager);
            serialTitle.setText(serialBean.getTitle());
            TextView serialAuthor = (TextView) view.findViewById(R.id.author_serial_reading_pager);
            serialAuthor.setText(serialBean.getAuthor().getUser_name());
            TextView serialGuideWord = (TextView) view.findViewById(R.id.guideword_serial_reading_pager);
            serialGuideWord.setText(serialBean.getExcerpt());

            TextView questionTitle = (TextView) view.findViewById(R.id.title_question_reading_pager);
            questionTitle.setText(questionBean.getQuestion_title());
            TextView questionAuthor = (TextView) view.findViewById(R.id.author_question_reading_pager);
            questionAuthor.setText(questionBean.getAnswer_title());
            TextView questionGuideWord = (TextView) view.findViewById(R.id.guideword_question_reading_pager);
            questionGuideWord.setText(questionBean.getAnswer_content());

            container.addView(view);

            return view;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
