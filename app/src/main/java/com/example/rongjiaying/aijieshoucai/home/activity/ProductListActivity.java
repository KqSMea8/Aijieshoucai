package com.example.rongjiaying.aijieshoucai.home.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSONObject;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseAdapter;
import com.example.rongjiaying.aijieshoucai.callback.ProgressBarStringCallback;
import com.example.rongjiaying.aijieshoucai.home.adapter.ProductListAdapter;
import com.example.rongjiaying.aijieshoucai.home.bean.ProductListBean;
import com.example.rongjiaying.aijieshoucai.interfac.OnProductApplyListener;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.IntentUtil;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * 业务  列表
 */
public class ProductListActivity extends BaseActivity implements View.OnClickListener {
    ProgressBar progressBar;
    ProductListAdapter productListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_product));
        progressBar = findViewById(R.id.progressbar);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        productListAdapter = new ProductListAdapter(getActivity());
        recyclerView.setAdapter(productListAdapter);
        initBusinessData();

        productListAdapter.setOnProductApplyListener(new OnProductApplyListener() {
            @Override
            public void onProductApply(ProductListBean productListBean) {
                if (productListAdapter.getDatas().indexOf(productListBean)!=-1)
                {
                    IntentUtil.Intent_EntryInformationActivity(getActivity()
                    ,productListBean);
                }
            }
        });
        productListAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position!=-1)
                {
                    IntentUtil.Intent_ProductDetailActivity(getActivity()
                            ,productListAdapter.getDatas().get(position));
                }
            }
        });
    }

    /**
     * 业务
     */
    private void initBusinessData() {
        HttpUtil.productList(new ProgressBarStringCallback(progressBar) {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());

                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        List<ProductListBean> productListBeans = JSONObject.parseArray(jsonObject.getString("msg"), ProductListBean.class);
                        if (productListBeans != null && productListBeans.size() > 0) {
                            productListAdapter.refreshData(productListBeans);
                        }
                    }

                } catch (Exception e) {

                }
            }
        });
    }

    @Override
    public AppCompatActivity getActivity() {
        return ProductListActivity.this;
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
