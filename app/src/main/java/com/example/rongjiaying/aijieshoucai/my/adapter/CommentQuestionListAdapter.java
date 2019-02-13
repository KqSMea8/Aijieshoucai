package com.example.rongjiaying.aijieshoucai.my.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;

import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseViewHolder;
import com.example.rongjiaying.aijieshoucai.baseadapter.SimpleAdapter;
import com.example.rongjiaying.aijieshoucai.my.bean.CommentQuestionListBean;

public class CommentQuestionListAdapter extends SimpleAdapter<CommentQuestionListBean> {
    public CommentQuestionListAdapter(Context context) {
        super(context, R.layout.item_commentquestionlayout);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, CommentQuestionListBean item) {
        AppCompatTextView tvTitle = viewHoder.getAppCompatTextView(R.id.tv_title);
        tvTitle.setText(item.getProblem());
    }
}
