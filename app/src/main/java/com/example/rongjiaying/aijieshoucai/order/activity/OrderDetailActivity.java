package com.example.rongjiaying.aijieshoucai.order.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.order.bean.OrderListBean;

/**
 * 订单详情
 */
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {
    OrderListBean orderListBean;
    int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        orderListBean = getIntent().getParcelableExtra("item");
        type = getIntent().getIntExtra("type", 0);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_orderdetail));

        AppCompatTextView tvUsername = findViewById(R.id.tv_name);//usernmae
        tvUsername.setText(orderListBean.getBorrowerName());
        AppCompatTextView tvPhone = findViewById(R.id.tv_phone);
        tvPhone.setText(orderListBean.getBorrowerPhone());
        AppCompatTextView tvProducename = findViewById(R.id.tv_producename);
        tvProducename.setText(orderListBean.getProduceName());


        AppCompatTextView tvStatus = findViewById(R.id.tv_status);
        if (type == 0) {
            switch (orderListBean.getStatus()) {
                case 2:
                case 3:
                    tvStatus.setText("初审中");
                    tvStatus.setTextColor(getResources().getColor(R.color.color_178C68));
                    break;
                case 4:
                    tvStatus.setText("终审中");
                    tvStatus.setTextColor(getResources().getColor(R.color.color_178C68));
                    break;
                case 5:
                case 6:
                    if (orderListBean.getBackStatus() == 1) {
                        tvStatus.setText("请补全资料");
                        tvStatus.setTextColor(getResources().getColor(R.color.color_4C5CF7));
                    } else {
                        tvStatus.setText("已拒批");
                        tvStatus.setTextColor(getResources().getColor(R.color.color_e92c2a));
                    }

                    break;
            }
        } else if (type == 1) {
            tvStatus.setText("签约中");
            tvStatus.setTextColor(getResources().getColor(R.color.color_178C68));
        } else if (type == 2) {
            tvStatus.setText("已放款");
            tvStatus.setTextColor(getResources().getColor(R.color.color_178C68));
        }


    }

    @Override
    public AppCompatActivity getActivity() {
        return OrderDetailActivity.this;
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
