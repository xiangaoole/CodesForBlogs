package com.mindle.androidtest;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mindle.androidtest.databinding.ActivityNewsBinding;
import com.mindle.androidtest.databinding.ListItemNewsBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNewsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_news);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new NewsAdapter(new NewsFactory().getNewsList()));
    }

    private class NewsHolder extends RecyclerView.ViewHolder {
        private ListItemNewsBinding mBinding;

        public NewsHolder(ListItemNewsBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setViewModel(new NewsViewModel(MainActivity.this));
        }

        public void bind(News news) {
            mBinding.getViewModel().setNews(news);
            mBinding.executePendingBindings();
        }
    }

    private class NewsAdapter extends RecyclerView.Adapter {

        private List<News> mNewsList;

        public NewsAdapter(List<News> newsList) {
            mNewsList = newsList;
        }

        @Override
        public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            ListItemNewsBinding binding = DataBindingUtil
                    .inflate(inflater, R.layout.list_item_news, parent, false);
            return new NewsHolder(binding);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            News news = mNewsList.get(position);
            ((NewsHolder) holder).bind(news);

        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }


}
