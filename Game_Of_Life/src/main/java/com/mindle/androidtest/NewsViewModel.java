package com.mindle.androidtest;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.widget.Toast;

public class NewsViewModel extends BaseObservable{
    private News mNews;
    private Context context;

    public NewsViewModel(Context context) {
        this.context = context;
    }

    @Bindable
    public String getTitle() {
        return  mNews.getTitle();
    }

    @Bindable
    public String getDescribe() {
        return mNews.getDescribe();
    }

    public void setNews(News news) {
        mNews = news;
        notifyChange();
    }

    public void onLayoutClicked() {
        Toast.makeText(context, "您点击的消息是："+ getTitle(), Toast.LENGTH_SHORT).show();
    }
}
