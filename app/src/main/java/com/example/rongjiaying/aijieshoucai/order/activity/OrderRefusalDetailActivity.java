package com.example.rongjiaying.aijieshoucai.order.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.order.bean.OrderListBean;

/**
 * 拒批详情
 */
public class OrderRefusalDetailActivity extends BaseActivity implements View.OnClickListener {
    OrderListBean orderListBean;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_refusal_detail);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_orderrefusaldetail));
        orderListBean = getIntent().getParcelableExtra("item");

        AppCompatTextView tvUsername = findViewById(R.id.tv_name);//usernmae
        tvUsername.setText(orderListBean.getBorrowerName());
        AppCompatTextView tvPhone = findViewById(R.id.tv_phone);
        tvPhone.setText(orderListBean.getBorrowerPhone());

        AppCompatTextView tvReason=findViewById(R.id.tv_reason);
        tvReason.setText(orderListBean.getBackReason());
        AppCompatTextView tvProducename=findViewById(R.id.tv_producename);
        tvProducename.setText(orderListBean.getProduceName());
    }

    @Override
    public AppCompatActivity getActivity() {
        return OrderRefusalDetailActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
