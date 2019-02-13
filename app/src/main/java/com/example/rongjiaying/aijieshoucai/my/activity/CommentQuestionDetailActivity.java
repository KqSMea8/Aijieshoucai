package com.example.rongjiaying.aijieshoucai.my.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.constant.InternetConstant;
import com.example.rongjiaying.aijieshoucai.my.bean.CommentQuestionListBean;

/**
 * 常见问题详情
 */
public class CommentQuestionDetailActivity extends BaseActivity implements View.OnClickListener {
    CommentQuestionListBean commentQuestionListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_question_detail);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_commentquestiontdetail));
        AppCompatImageView ivIcon = findViewById(R.id.iv_icon);
        commentQuestionListBean = getIntent().getParcelableExtra("commentQuestionListBean");
        RequestOptions options2 = new RequestOptions()
                .centerCrop()
                .error(R.drawable.ic_boxing_default_image)
                .placeholder(R.drawable.ic_boxing_default_image);
        Glide.with(ivIcon.getContext())
                .load(InternetConstant.imageurl+commentQuestionListBean.getAnswer())
                .apply(options2)
                .into(ivIcon);
    }

    @Override
    public AppCompatActivity getActivity() {
        return CommentQuestionDetailActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
