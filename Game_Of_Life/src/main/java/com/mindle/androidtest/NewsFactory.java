package com.mindle.androidtest;

import java.util.ArrayList;
import java.util.List;

public class NewsFactory {

    private List<News> mNewsList = new ArrayList<>();

    public NewsFactory() {
        for (int i = 0; i < 100; i++) {
            News news = new News();
            news.setTitle("标题#" + i);
            news.setDescribe("这条消息的内容是" + i);
            mNewsList.add(news);
        }
    }

    public List<News> getNewsList() {
        return mNewsList;
    }
}
