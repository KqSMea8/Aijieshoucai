package com.example.rongjiaying.aijieshoucai.my.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.my.adapter.MyMessageListAdapter;
import com.example.rongjiaying.aijieshoucai.my.bean.MyMessageBean;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * 我的消息
 */
public class MyMessageActivity extends BaseActivity implements View.OnClickListener {
    LoginBean loginBean = null;
    MyMessageListAdapter myMessageListAdapter;
    int page = 0;
    int limit = 10;
AppCompatEditText etSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_mymessage));
        String message = getSharedFileUtils().getString(MyConstant.loginuser);
        loginBean = JSONObject.parseObject(message, LoginBean.class);
        etSearch=findViewById(R.id.et_search);
        findViewById(R.id.iv_search).setOnClickListener(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        SmartRefreshLayout refreshLayout = findViewById(R.id.refresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myMessageListAdapter = new MyMessageListAdapter(getActivity());
        recyclerView.setAdapter(myMessageListAdapter);
        initMyMessageData();

Log.i("asdf","getUserId"+loginBean.getUserId());
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 0;
                initMyMessageData();
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                page = page + 1 + limit;
                initMyMessageData();
                refreshLayout.finishLoadMore(200);
            }
        });
    }

    String search = "";

    private void initMyMessageData() {
        HttpUtil.myMessageData(loginBean.getUserId(),
                page + "", limit + "", search, new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());

                            int code = jsonObject.getInt("code");
                            if (code == 0) {
                                String msg = jsonObject.getString("msg");
                                Log.i("asdf", "" + msg);
                                List<MyMessageBean> myMessageBeans = JSONObject.parseArray(msg, MyMessageBean.class);
                                if (page == 0) {
                                    myMessageListAdapter.refreshData(myMessageBeans);
                                } else {
                                    myMessageListAdapter.loadMoreData(myMessageBeans);
                                }

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public AppCompatActivity getActivity() {
        return MyMessageActivity.this;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.iv_search:
                page=0;
                search=etSearch.getText().toString();
                initMyMessageData();

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag("myMessageData");
    }
}
