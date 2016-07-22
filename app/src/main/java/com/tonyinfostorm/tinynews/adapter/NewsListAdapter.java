package com.tonyinfostorm.tinynews.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tonyinfostorm.tinynews.Network.ImageLoader;
import com.tonyinfostorm.tinynews.Network.URL;
import com.tonyinfostorm.tinynews.NewsDetailActivity;
import com.tonyinfostorm.tinynews.R;
import com.tonyinfostorm.tinynews.beans.NewsData;
import com.tonyinfostorm.tinynews.utils.TimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by higer on 2016/7/18.
 */
public class NewsListAdapter extends RecyclerView.Adapter {
    NewsData newsData;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,
                parent, false);
        // Following way is also ok.
//        View view = View.inflate(parent.getContext(), R.layout.news_item, null);
        return new DeCodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final DeCodeViewHolder deCodeViewHolder = (DeCodeViewHolder) holder;
        ImageLoader.load(holder.itemView.getContext(),
                newsData.getArticles().get(position).getUrlToImage(),
                deCodeViewHolder.pictureIv);
        deCodeViewHolder.titleTv.setText(newsData.getArticles().get(position).getTitle());
        deCodeViewHolder.timeTv.setText(newsData.getArticles().get(position).getPublishedAt());
        String time = newsData.getArticles().get(position).getPublishedAt();
        if ((time == null) || time.isEmpty()) {
            deCodeViewHolder.timeTv.setVisibility(View.GONE);
        } else {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            try {
                Date date = inputFormat.parse(time);
                String convertTime = TimeUtil.convertTimeToFormat(date.getTime());

                // Only display time information when successfully parsing date
                if(convertTime.equals(time) == false) {
                    deCodeViewHolder.timeTv.setText(convertTime);
                } else {
                    deCodeViewHolder.timeTv.setVisibility(View.GONE);
                }
            } catch (ParseException e) {
                e.printStackTrace();

            }
        }

        deCodeViewHolder.desTv.setText(newsData.getArticles().get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), NewsDetailActivity.class);
                String url = newsData.getArticles().get(position).getUrl();
                intent.putExtra(URL.newsUrl, url);
                holder.itemView.getContext().startActivity(intent);
            }
        });
        deCodeViewHolder.expandIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deCodeViewHolder.expandStatus == false) {
                    deCodeViewHolder.desTv.setVisibility(View.VISIBLE);
                    deCodeViewHolder.expandStatus = true;
                    deCodeViewHolder.expandIv.setBackgroundResource(R.drawable.arrow_up);
                } else {
                    deCodeViewHolder.desTv.setVisibility(View.GONE);
                    deCodeViewHolder.expandStatus = false;
                    deCodeViewHolder.expandIv.setBackgroundResource(R.drawable.arrow_down);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsData == null ? 0 : newsData.getArticles().size();
    }

    static class DeCodeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.titleTv)
        TextView titleTv;
        @BindView(R.id.timeTv)
        TextView timeTv;
        @BindView(R.id.descriptionTv)
        TextView desTv;
        @BindView(R.id.pictureIv)
        ImageView pictureIv;
        @BindView(R.id.expandIv)
        ImageView expandIv;

        boolean expandStatus;


        public DeCodeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            expandStatus = false;
        }
    }

    public void setNews(NewsData newsData) {
        this.newsData = newsData;
        notifyDataSetChanged();
    }
}
