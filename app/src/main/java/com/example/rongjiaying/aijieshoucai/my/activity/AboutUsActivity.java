package com.example.rongjiaying.aijieshoucai.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.constant.InternetConstant;

/**
 * 关于我们
 */
public class AboutUsActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public AppCompatActivity getActivity() {
        return AboutUsActivity.this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("关于我们");
        AppCompatImageView ivIcon=findViewById(R.id.iv_icon);

        RequestOptions options2 = new RequestOptions()
                .fitCenter()
                .error(R.drawable.ic_boxing_default_image)
                .placeholder(R.drawable.ic_boxing_default_image);
        Glide.with(ivIcon.getContext())
                .load(InternetConstant.imageurl + "about.png")
                .apply(options2)
                .into(ivIcon);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
