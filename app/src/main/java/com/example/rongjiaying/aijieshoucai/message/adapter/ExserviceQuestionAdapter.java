package com.example.rongjiaying.aijieshoucai.message.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;

import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseViewHolder;
import com.example.rongjiaying.aijieshoucai.baseadapter.SimpleAdapter;
import com.example.rongjiaying.aijieshoucai.my.bean.CommentQuestionListBean;

public class ExserviceQuestionAdapter extends SimpleAdapter<CommentQuestionListBean> {
    public ExserviceQuestionAdapter(Context context) {
        super(context, R.layout.item_exservicequestionlayout);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, CommentQuestionListBean item) {
        AppCompatTextView tvTitle=viewHoder.getAppCompatTextView(R.id.tv_title);
        tvTitle.setText(item.getProblem());
    }
}
