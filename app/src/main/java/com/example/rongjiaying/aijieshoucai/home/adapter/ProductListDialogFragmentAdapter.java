package com.example.rongjiaying.aijieshoucai.home.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseViewHolder;
import com.example.rongjiaying.aijieshoucai.baseadapter.SimpleAdapter;
import com.example.rongjiaying.aijieshoucai.constant.InternetConstant;
import com.example.rongjiaying.aijieshoucai.home.bean.ProductListBean;
import com.example.rongjiaying.aijieshoucai.util.Judge;

/**
 * 产品适配器
 */
public class ProductListDialogFragmentAdapter extends SimpleAdapter<ProductListBean> {
    public ProductListDialogFragmentAdapter(Context context) {
        super(context, R.layout.item_productlistdialogfragmentlayout);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, ProductListBean item) {
        AppCompatImageView ivIcon = viewHoder.getAppCompatImageView(R.id.iv_icon);
        AppCompatTextView tvMessage = viewHoder.getAppCompatTextView(R.id.tv_message);


        RequestOptions options2 = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher);
        Glide.with(ivIcon.getContext())
                .load(InternetConstant.imageurl + item.getPath())
                .apply(options2)
                .into(ivIcon);

        if (!Judge.getBoolean_isNull(item.getName())) {
            tvMessage.setText(item.getName());
        }
    }
}
