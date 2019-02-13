package com.example.rongjiaying.aijieshoucai.home.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseViewHolder;
import com.example.rongjiaying.aijieshoucai.baseadapter.SimpleAdapter;
import com.example.rongjiaying.aijieshoucai.constant.InternetConstant;
import com.example.rongjiaying.aijieshoucai.home.bean.ProductListBean;
import com.example.rongjiaying.aijieshoucai.interfac.OnProductApplyListener;
import com.example.rongjiaying.aijieshoucai.util.Judge;

/**
 * 业务  列表  adapter
 */
public class ProductListAdapter extends SimpleAdapter<ProductListBean> {
    public ProductListAdapter(Context context) {
        super(context, R.layout.item_productlistlayout);
    }
    public void setOnProductApplyListener(OnProductApplyListener onProductApplyListener) {
        this.onProductApplyListener = onProductApplyListener;
    }

    OnProductApplyListener onProductApplyListener;
    @Override
    protected void convert(BaseViewHolder viewHoder, final ProductListBean item) {
        AppCompatImageView ivIcon=viewHoder.getAppCompatImageView(R.id.iv_icon);
        AppCompatTextView tvTitle=viewHoder.getAppCompatTextView(R.id.tv_title);
        AppCompatTextView tvMessage=viewHoder.getAppCompatTextView(R.id.tv_message);

        AppCompatTextView tvApply = viewHoder.getAppCompatTextView(R.id.tv_apply);

        tvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onProductApplyListener != null) {
                    onProductApplyListener.onProductApply(item);
                }
            }
        });


        RequestOptions options2 = new RequestOptions()
                .centerCrop()
                .error(R.drawable.ic_boxing_default_image)
                .placeholder(R.drawable.ic_boxing_default_image);
        Glide.with(ivIcon.getContext())
                .load(InternetConstant.imageurl + item.getPath())
                .apply(options2)
                .into(ivIcon);

        if (!Judge.getBoolean_isNull(item.getName())) {
            tvTitle.setText(item.getName());
        }
        if (!Judge.getBoolean_isNull(item.getMemo())) {
            tvMessage.setText(item.getMemo());
        }
    }
}
