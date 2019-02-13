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
 * 业务列表  在首页de
 */
public class ProductListHomePagerAdapter extends SimpleAdapter<ProductListBean> {
    public ProductListHomePagerAdapter(Context context) {
        super(context, R.layout.item_productlisthomepagerlayout);
    }


    @Override
    protected void convert(BaseViewHolder viewHoder, final ProductListBean item) {
        AppCompatImageView ivIcon = viewHoder.getAppCompatImageView(R.id.iv_icon);
        AppCompatTextView tvMessage = viewHoder.getAppCompatTextView(R.id.tv_message);
        AppCompatTextView expectMonthInterest = viewHoder.getAppCompatTextView(R.id.tv_expectmonthinterest);
        AppCompatTextView tvmaxLoan = viewHoder.getAppCompatTextView(R.id.tv_maxloan);



        RequestOptions options2 = new RequestOptions()
                .centerCrop()
                .error(R.drawable.ic_boxing_default_image)
                .placeholder(R.drawable.ic_boxing_default_image);
        Glide.with(ivIcon.getContext())
                .load(InternetConstant.imageurl + item.getPath())
                .apply(options2)
                .into(ivIcon);

        if (!Judge.getBoolean_isNull(item.getName())) {
            tvMessage.setText(item.getName());
        }


        if (!Judge.getBoolean_isNull(item.getExpectMonthInterest())) {
            expectMonthInterest.setText(item.getExpectMonthInterest());
        }

        if (!Judge.getBoolean_isNull(item.getMaxLoan())) {
            tvmaxLoan.setText(item.getMaxLoan());
        }



    }
}
