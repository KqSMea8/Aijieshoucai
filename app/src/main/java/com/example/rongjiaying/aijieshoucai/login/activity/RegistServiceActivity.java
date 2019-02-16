package com.example.rongjiaying.aijieshoucai.login.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.util.AssetsUtil;

/**
 * 服务协议activity
 */
public class RegistServiceActivity extends BaseActivity {

    @Override
    public AppCompatActivity getActivity() {
        return RegistServiceActivity.this;
    }

    int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_service);
        TextView tv = findViewById(R.id.tv);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            String message = AssetsUtil.readAssertResource(getActivity(), "registservice1.txt");
            tv.setText(Html.fromHtml(message));
        } else if (type == 2) {
            String message = AssetsUtil.readAssertResource(getActivity(), "registservice2.txt");
            tv.setText(Html.fromHtml(message));
        }
    }
}
