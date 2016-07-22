package com.tonyinfostorm.tinynews.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tonyinfostorm.tinynews.Network.NetworkWrapper;
import com.tonyinfostorm.tinynews.Network.URL;
import com.tonyinfostorm.tinynews.R;
import com.tonyinfostorm.tinynews.adapter.NewsListAdapter;
import com.tonyinfostorm.tinynews.beans.NewsData;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by higer on 2016/7/18.
 */
@SuppressLint("ValidFragment")
public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.listView)
    RecyclerView listView;

    NewsListAdapter newsListAdapter = new NewsListAdapter();
    Observer<NewsData> observer = new Observer<NewsData>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), R.string.loading_failed, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(NewsData newsData) {
            swipeRefreshLayout.setRefreshing(false);
            newsListAdapter.setNews(newsData);
        }
    };
    Subscription subscription;


    String agency;

    @SuppressLint("ValidFragment")
    public NewsFragment(String agency) {
        this.agency = agency;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);

        listView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        listView.setAdapter(newsListAdapter);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setOnRefreshListener(this);
        loadNews();
        return view;
    }

    public void loadNews() {
        unsubscribe();
        newsListAdapter.setNews(null);
        swipeRefreshLayout.setRefreshing(true);
        subscription = NetworkWrapper.getNewsApi()
                .articles(agency, URL.apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    @Override
    public void onRefresh() {
        loadNews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unsubscribe();
    }

    private void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
