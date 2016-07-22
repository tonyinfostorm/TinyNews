package com.tonyinfostorm.tinynews.Network;

import com.tonyinfostorm.tinynews.beans.NewsData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by higer on 2016/7/19.
 */
public interface NewsApi {
    @GET("articles")
    Observable<NewsData> articles(@Query("source") String agency, @Query("apiKey") String apiKey);
}
