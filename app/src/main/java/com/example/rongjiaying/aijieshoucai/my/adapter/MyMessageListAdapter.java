package com.example.rongjiaying.aijieshoucai.my.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseViewHolder;
import com.example.rongjiaying.aijieshoucai.baseadapter.SimpleAdapter;
import com.example.rongjiaying.aijieshoucai.my.bean.MyMessageBean;
import com.example.rongjiaying.aijieshoucai.util.Judge;

public class MyMessageListAdapter extends SimpleAdapter<MyMessageBean> {
    public MyMessageListAdapter(Context context) {
        super(context, R.layout.item_mymessagelayout);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, MyMessageBean item) {
        AppCompatTextView tvTitle = viewHoder.getAppCompatTextView(R.id.tv_title);
        AppCompatTextView tvMessage = viewHoder.getAppCompatTextView(R.id.tv_message);
        AppCompatTextView tvTime = viewHoder.getAppCompatTextView(R.id.tv_time);
        if (!Judge.getBoolean_isNull(item.getTitle())) {
            tvTitle.setText(item.getTitle());
        } else {
            tvTitle.setVisibility(View.GONE);
        }

        if (!Judge.getBoolean_isNull(item.getMemo())) {
            tvMessage.setText(item.getMemo());
        } else {
            tvMessage.setVisibility(View.GONE);
        }

        if (!Judge.getBoolean_isNull(item.getCreateTime())) {
            tvTime.setText(item.getCreateTime());
        } else {
            tvTime.setVisibility(View.GONE);
        }

    }
}
