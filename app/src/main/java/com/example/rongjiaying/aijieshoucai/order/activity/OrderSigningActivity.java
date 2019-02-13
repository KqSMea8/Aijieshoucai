package com.example.rongjiaying.aijieshoucai.order.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.order.adapter.OrderSigningAdapter;
import com.example.rongjiaying.aijieshoucai.order.bean.OrderListBean;
import com.example.rongjiaying.aijieshoucai.order.bean.OrderSingningBean;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;

import java.util.List;

/**
 * 签约进度
 */
public class OrderSigningActivity extends BaseActivity implements View.OnClickListener {
    OrderSigningAdapter orderSigningAdapter;

    OrderListBean orderListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_signing);
        orderListBean = getIntent().getParcelableExtra("item");
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_contractprogress));

        AppCompatTextView tvUsername = findViewById(R.id.tv_username);//usernmae
        tvUsername.setText(orderListBean.getBorrowerName());
        AppCompatTextView tvPhone = findViewById(R.id.tv_phone);
        tvPhone.setText(orderListBean.getBorrowerPhone());

        AppCompatTextView tvProducename = findViewById(R.id.tv_producename);
        tvProducename.setText(orderListBean.getProduceName());


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderSigningAdapter = new OrderSigningAdapter(getActivity());
        recyclerView.setAdapter(orderSigningAdapter);
        initData();
    }

    private void initData() {
        HttpUtil.ordersingningDetail(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {


                    org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());
                    int code = jsonObject.getInt("code");

                    if (code == 0) {

                        JSONArray jsonArray = new JSONArray(jsonObject.getString("msg"));
                        String message = jsonArray.getString(2);
                        Log.i("asdf",""+message);
                      List<OrderSingningBean> orderSingningBeans=JSONObject.parseArray(message,OrderSingningBean.class);

                      orderSigningAdapter.refreshData(orderSingningBeans);

                      orderSigningAdapter.setStatusCheck(orderListBean.getStatus());
                    }


                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    public AppCompatActivity getActivity() {
        return OrderSigningActivity.this;
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
