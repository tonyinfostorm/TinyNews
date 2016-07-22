package com.tonyinfostorm.tinynews.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tonyinfostorm.tinynews.Network.URL;
import com.tonyinfostorm.tinynews.OperatePagerIntf;
import com.tonyinfostorm.tinynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by higer on 2016/7/21.
 */
public class HeaderListAdapter extends RecyclerView.Adapter {
    private OperatePagerIntf operatePagerIntf;

    public HeaderListAdapter(OperatePagerIntf operatePagerIntf) {
        this.operatePagerIntf = operatePagerIntf;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_list_item,
                parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        headerViewHolder.agency.setText(URL.news_items[position]);
        if(operatePagerIntf.getViewPager().getCurrentItem()!=position) {
            holder.itemView.setBackgroundColor(Color.WHITE);
            headerViewHolder.iconIv.setImageResource(R.drawable.ic_label);
        } else {
            holder.itemView.setBackgroundResource(R.color.colorPrimary);
            headerViewHolder.iconIv.setImageResource(R.drawable.ic_label_black);
        }


        headerViewHolder.agency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.setBackgroundResource(R.color.colorPrimary);
                operatePagerIntf.getViewPager().setCurrentItem(position);
                operatePagerIntf.closeDrawer();
            }
        });
    }

    @Override
    public int getItemCount() {
        return URL.news_items.length;
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.agencyTv)
        TextView agency;
        @BindView(R.id.iconIv)
        ImageView iconIv;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
