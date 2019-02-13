package com.example.rongjiaying.aijieshoucai.home.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseViewHolder;
import com.example.rongjiaying.aijieshoucai.baseadapter.SimpleAdapter;
import com.example.rongjiaying.aijieshoucai.constant.InternetConstant;
import com.example.rongjiaying.aijieshoucai.home.bean.BrokerBean;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.example.rongjiaying.aijieshoucai.widget.CustomShapeImageView;

public class BrokerListAdapter extends SimpleAdapter<BrokerBean> {
    public BrokerListAdapter(Context context) {
        super(context, R.layout.item_brokerlistlayout);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, BrokerBean item) {
        AppCompatImageView ivLevel = viewHoder.getAppCompatImageView(R.id.iv_level);
        AppCompatTextView tvLevel = viewHoder.getAppCompatTextView(R.id.tv_level);
        CustomShapeImageView ivIcon = (CustomShapeImageView) viewHoder.getView(R.id.iv_icon);
        AppCompatTextView tvName = viewHoder.getAppCompatTextView(R.id.tv_name);
        AppCompatTextView tvMoney = viewHoder.getAppCompatTextView(R.id.tv_money);
        if (!Judge.getBoolean_isNull(item.getUsername())) {
            tvName.setText(item.getUsername());
        } else {
            tvName.setText("");
        }

        RequestOptions options2 = new RequestOptions()
                .fitCenter()
                .error(R.drawable.ic_boxing_default_image)
                .placeholder(R.drawable.ic_boxing_default_image);
        Glide.with(ivIcon.getContext())
                .load(InternetConstant.imageurl + item.getIcon())
                .apply(options2)
                .into(ivIcon);

        tvMoney.setText(item.getSum() + "");
        switch (position) {
            case 0:
                ivLevel.setVisibility(View.VISIBLE);
                tvLevel.setVisibility(View.GONE);
                ivLevel.setImageResource(R.drawable.icon_gold);
                break;
            case 1:
                ivLevel.setVisibility(View.VISIBLE);
                tvLevel.setVisibility(View.GONE);
                ivLevel.setImageResource(R.drawable.icon_silver);
                break;
            case 2:
                ivLevel.setVisibility(View.VISIBLE);
                tvLevel.setVisibility(View.GONE);
                ivLevel.setImageResource(R.drawable.icon_bronze);
                break;
            default:
                ivLevel.setVisibility(View.GONE);
                tvLevel.setVisibility(View.VISIBLE);
                tvLevel.setText(getLevel(position+1));
                break;
        }
    }

    private String getLevel(int position) {
        if (position < 10) {
            return "0" + position;
        } else {
            return position + "";
        }
    }
}
