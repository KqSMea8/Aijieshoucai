/*
 *  Copyright (C) 2017 Bilibili
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.rongjiaying.aijieshoucai.imageloader;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bilibili.boxing.loader.IBoxingCallback;
import com.bilibili.boxing.loader.IBoxingMediaLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.rongjiaying.aijieshoucai.R;


/**
 * use https://github.com/bumptech/glide as media loader.
 * can <b>not</b> be used in Production Environment.
 *
 * @author ChenSL
 */
public class BoxingGlideLoader implements IBoxingMediaLoader {

    @Override
    public void displayThumbnail(@NonNull ImageView img, @NonNull String absPath, int width, int height) {
        String path = "file://" + absPath;
        try {
            // https://github.com/bumptech/glide/issues/1531
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .override(width, height)
                    .placeholder(R.drawable.ic_boxing_default_image)
                    .error(R.drawable.ic_boxing_default_image);
            Glide.with(img.getContext()).load(path)
                    .apply(options).into(img);
        } catch (IllegalArgumentException ignore) {
        }

    }

    @Override
    public void displayRaw(@NonNull final ImageView img, @NonNull String absPath, int width, int height, final IBoxingCallback callback) {
        String path = "file://" + absPath;
        RequestOptions options = new RequestOptions();
        if (width > 0 && height > 0) {
            options.override(width, height);
        }
        options.centerCrop();
        options.error(R.drawable.ic_boxing_default_image);
        options.placeholder(R.drawable.ic_boxing_default_image);
        Glide.with(img.getContext()).load(path)
                .apply(options).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                if (callback != null) {
                    callback.onFail(e);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (resource != null && callback != null) {
                    img.setImageDrawable(resource);
                    callback.onSuccess();
                    return true;
                }
                return false;
            }
        }).into(img);

    }

}
