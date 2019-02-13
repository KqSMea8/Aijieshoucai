package com.example.rongjiaying.aijieshoucai.home.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseViewHolder;
import com.example.rongjiaying.aijieshoucai.baseadapter.SimpleAdapter;
import com.example.rongjiaying.aijieshoucai.constant.InternetConstant;
import com.example.rongjiaying.aijieshoucai.home.bean.BrokerBean;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.example.rongjiaying.aijieshoucai.widget.CustomShapeImageView;

/**
 * 经纪人 homepager adapter
 */
public class BrokerHomePagerAdapter extends SimpleAdapter<BrokerBean> {
    public BrokerHomePagerAdapter(Context context) {
        super(context, R.layout.item_brokerhhomepagerlayout);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, BrokerBean item) {
        CustomShapeImageView ivIcon = (CustomShapeImageView) viewHoder.getView(R.id.iv_icon);
        AppCompatTextView tvMessage = viewHoder.getAppCompatTextView(R.id.tv_message);
        AppCompatTextView tv_mypoint = viewHoder.getAppCompatTextView(R.id.tv_mypoint);

        RequestOptions options2 = new RequestOptions()
                .fitCenter()
                .error(R.drawable.ic_boxing_default_image)
                .placeholder(R.drawable.ic_boxing_default_image);
        Glide.with(ivIcon.getContext())
                .load(InternetConstant.imageurl + item.getIcon())
                .apply(options2)
                .into(ivIcon);

        if (!Judge.getBoolean_isNull(item.getUsername())) {
            tvMessage.setText(item.getUsername());
        }
        if (!Judge.getBoolean_isNull(item.getMyIntegral())) {
            tv_mypoint.setText("财力值:" + item.getMyIntegral());
        }
    }
}
