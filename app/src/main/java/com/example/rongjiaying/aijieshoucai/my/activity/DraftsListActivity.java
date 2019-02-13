package com.example.rongjiaying.aijieshoucai.my.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseAdapter;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.my.adapter.DraftsListAdapter;
import com.example.rongjiaying.aijieshoucai.my.eventbus.DraftsEventbus;
import com.example.rongjiaying.aijieshoucai.order.bean.OrderListBean;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.IntentUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;

import java.util.List;

/**
 * 草稿列表
 */
public class DraftsListActivity extends BaseActivity implements View.OnClickListener {
    LoginBean loginBean;
    DraftsListAdapter draftsListAdapter;
    int checkposition = 0;
    int page = 0;
    int limit = 10;

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_drafts_list);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_drafts));
        String message = getSharedFileUtils().getString(MyConstant.loginuser);
        loginBean = JSONObject.parseObject(message, LoginBean.class);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        SmartRefreshLayout refreshLayout = findViewById(R.id.refresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        draftsListAdapter = new DraftsListAdapter(getActivity());

        recyclerView.setAdapter(draftsListAdapter);

        initData();

        draftsListAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position != -1) {
                    checkposition = position;
                    IntentUtil.Intent_DraftsActivity(getActivity()
                            , draftsListAdapter.getDatas().get(position));

                }
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 0;
                initData();
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                page = page + 1 + limit;
                initData();
                refreshLayout.finishLoadMore(200);
            }
        });
    }

    private void initData() {
        HttpUtil.caogaolist(loginBean.getUserId(),
                page + "", limit + "", new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());

                            int code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            if (code == 0) {
                                Log.i("asdf", "" + msg);
                                List<OrderListBean> orderListBeans = JSONObject.parseArray(msg, OrderListBean.class);
                                if (page == 0) {
                                    draftsListAdapter.refreshData(orderListBeans);
                                } else {
                                    draftsListAdapter.loadMoreData(orderListBeans);
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    @Override
    public AppCompatActivity getActivity() {
        return DraftsListActivity.this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag("caogaolist");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DraftsEventbus eventbus) {
        if (eventbus != null) {
            if (eventbus.getDeleorEdit() == 2) {
                if (draftsListAdapter != null && draftsListAdapter.getDatas().size() > 0) {
                    if (checkposition != -1) {
                        draftsListAdapter.getDatas().set(checkposition, eventbus.getOrderListBean());
                        draftsListAdapter.notifyDataSetChanged();
                    }
                }
            } else {
                if (draftsListAdapter != null && draftsListAdapter.getDatas().size() > 0) {
                    if (checkposition != -1) {

                        draftsListAdapter.removeItem(draftsListAdapter.getDatas().get(checkposition));


                    }
                }
            }

        }
    }

    ;
}
