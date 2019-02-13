package jiguang.chat.utils.imagepicker;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

import com.bumptech.glide.request.RequestOptions;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.constant.InternetConstant;

public class GlideImageLoader implements ImageLoader {

    @Override
    public void displayImages(Activity activity, String path, ImageView imageView, int width, int height) {




        RequestOptions options3 = new RequestOptions()
                .centerCrop()
                .error(R.drawable.ic_boxing_default_image)
                .placeholder(R.drawable.ic_boxing_default_image);
        Glide.with(activity)
                .load(Uri.fromFile(new File(path)))
                .apply(options3)
                .into(imageView);
    }



    @Override
    public void clearMemoryCache() {
    }
}
