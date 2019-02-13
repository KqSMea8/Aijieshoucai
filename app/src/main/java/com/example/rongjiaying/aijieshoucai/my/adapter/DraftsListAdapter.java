package com.example.rongjiaying.aijieshoucai.my.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;

import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseViewHolder;
import com.example.rongjiaying.aijieshoucai.baseadapter.SimpleAdapter;
import com.example.rongjiaying.aijieshoucai.order.bean.OrderListBean;
import com.example.rongjiaying.aijieshoucai.util.Judge;

/**
 * 草稿箱列表adapter
 */
public class DraftsListAdapter extends SimpleAdapter<OrderListBean> {
    public DraftsListAdapter(Context context) {
        super(context, R.layout.item_draftslistlayout);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, OrderListBean item) {
        AppCompatTextView tvOrderId = viewHoder.getAppCompatTextView(R.id.tv_orderid);
        AppCompatTextView tvUsername = viewHoder.getAppCompatTextView(R.id.tv_username);
        AppCompatTextView tvProducename = viewHoder.getAppCompatTextView(R.id.tv_producename);
        AppCompatTextView tvPhone = viewHoder.getAppCompatTextView(R.id.tv_phone);
        if (!Judge.getBoolean_isNull(item.getOrderCode()))
        {
            tvOrderId.setText(item.getOrderCode());
        }else {
            tvOrderId.setText("");
        }


        if (!Judge.getBoolean_isNull(item.getBorrowerName()))
        {
            tvUsername.setText(item.getBorrowerName());
        }else {
            tvUsername.setText("");
        }

        if (!Judge.getBoolean_isNull(item.getProduceName()))
        {
            tvProducename.setText(item.getProduceName());
        }else {
            tvProducename.setText("");
        }

        if (!Judge.getBoolean_isNull(item.getBorrowerPhone()))
        {
            tvPhone.setText(item.getBorrowerPhone());
        }else {
            tvPhone.setText("");
        }

    }
}
