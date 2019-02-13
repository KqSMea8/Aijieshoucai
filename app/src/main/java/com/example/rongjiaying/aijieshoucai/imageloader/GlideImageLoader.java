package com.example.rongjiaying.aijieshoucai.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rongjiaying.aijieshoucai.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by rongjiaying on 2018/4/1.
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        RequestOptions options2 = new RequestOptions()
                .error(R.drawable.ic_boxing_default_image)
                .placeholder(R.drawable.ic_boxing_default_image);
        Glide.with(context)
                .load(path)
              .apply(options2)
                .into(imageView);
    }


}