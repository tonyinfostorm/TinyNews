package com.tonyinfostorm.tinynews.Network;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by higer on 2016/7/19.
 */
public class ImageLoader {
    public static void load(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }
}
