package com.example.rongjiaying.aijieshoucai.my.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseViewHolder;
import com.example.rongjiaying.aijieshoucai.baseadapter.SimpleAdapter;
import com.example.rongjiaying.aijieshoucai.constant.InternetConstant;
import com.example.rongjiaying.aijieshoucai.my.bean.RecommendationBean;
import com.example.rongjiaying.aijieshoucai.util.Judge;

public class RecommendationListAdapter extends SimpleAdapter<RecommendationBean> {
    public RecommendationListAdapter(Context context) {
        super(context, R.layout.item_recommendationlayout);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, RecommendationBean item) {
        AppCompatImageView ivIcon = viewHoder.getAppCompatImageView(R.id.iv_icon);

        RequestOptions options2 = new RequestOptions()
                .fitCenter()
                .circleCrop()
                .error(R.drawable.ic_boxing_default_image)
                .placeholder(R.drawable.ic_boxing_default_image);
        Glide.with(ivIcon.getContext())
                .load(InternetConstant.imageurl + item.getIcon())
                .apply(options2)
                .into(ivIcon);


        AppCompatTextView tvName = viewHoder.getAppCompatTextView(R.id.tv_name);
        AppCompatTextView tv_count = viewHoder.getAppCompatTextView(R.id.tv_count);
        AppCompatTextView tv_status = viewHoder.getAppCompatTextView(R.id.tv_status);


        try {

            if (!Judge.getBoolean_isNull(item.getUsername()))
            {
                tvName.setText(item.getUsername());
            }

            int level = Integer.valueOf(item.getLevel());

            if (level > 10) {
                tv_status.setText("已齐架");
                tv_status.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else if (level > 5) {
                tv_status.setText("已架构");
                tv_status.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else {
                tv_status.setText("尚未架构");
                tv_status.setTextColor(context.getResources().getColor(R.color.color_bababa));
            }


            tv_count.setText("二级推荐"+item.getDirectCount()+"人 | 三级推荐"+item.getIndirectCount()+"人");


        } catch (Exception e) {
        }

    }
}
