package com.example.rongjiaying.aijieshoucai.my.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;

/**
 * 提现成功
 */
public class WithdrawSuccessActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_success);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.tv_commit).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("提现成功");
    }

    @Override
    public AppCompatActivity getActivity() {
        return WithdrawSuccessActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_commit:
                finish();
                break;
        }
    }
}
