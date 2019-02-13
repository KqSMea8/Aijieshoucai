package com.example.rongjiaying.aijieshoucai.my.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseViewHolder;
import com.example.rongjiaying.aijieshoucai.baseadapter.SimpleAdapter;
import com.example.rongjiaying.aijieshoucai.my.bean.MyFragmentItemBean;

public class MyFragmentItemAdapter extends SimpleAdapter<MyFragmentItemBean> {
    public MyFragmentItemAdapter(Context context) {
        super(context, R.layout.item_myfragmentitemlayout);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, MyFragmentItemBean item) {
        AppCompatImageView ivicon=viewHoder.getAppCompatImageView(R.id.iv_icon);
        AppCompatTextView tvTitle=viewHoder.getAppCompatTextView(R.id.tv_title);
        ivicon.setImageResource(item.getDrawableRes());
        tvTitle.setText(item.getTitle());
    }
}
