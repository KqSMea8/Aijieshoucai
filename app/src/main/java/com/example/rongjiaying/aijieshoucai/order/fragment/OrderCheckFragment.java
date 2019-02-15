package com.example.rongjiaying.aijieshoucai.order.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSONObject;
import com.example.rongjiaying.aijieshoucai.BaseFragment;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseAdapter;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.interfac.OnOrderItemListener;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.order.adapter.OrderListAdapter;
import com.example.rongjiaying.aijieshoucai.order.bean.OrderListBean;
import com.example.rongjiaying.aijieshoucai.order.eventbus.OrderListItemDeleEventbus;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.IntentUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 */
public class OrderCheckFragment extends BaseFragment {
    public OrderCheckFragment() {
        // Required empty public constructor
    }

    LoginBean loginBean;
    int type = 0;
    int checkposition = -1;

    /**
     */
    // TODO: Rename and change types and number of parameters
    public static OrderCheckFragment newInstance(int type) {
        OrderCheckFragment fragment = new OrderCheckFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt("type", 0);
        }
    }

    OrderListAdapter orderListAdapter;
    ProgressBar progressBar;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void orderdeleItem(OrderListItemDeleEventbus messageEvent) {
        if (messageEvent.getOrderListBean() != null) {
            if (checkposition != -1) {
                orderListAdapter.removeItem(orderListAdapter.getDatas().get(checkposition));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_check, container, false);
        EventBus.getDefault().register(this);
        String message = getSharedFileUtils(getActivity()).getString(MyConstant.loginuser);
        loginBean = JSONObject.parseObject(message, LoginBean.class);
        progressBar = view.findViewById(R.id.progressbar);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        SmartRefreshLayout refreshLayout = view.findViewById(R.id.refresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderListAdapter = new OrderListAdapter(getActivity(), type);
        recyclerView.setAdapter(orderListAdapter);
        if (loginBean!=null&&!Judge.getBoolean_isNull(loginBean.getUserId()))
        {
            initOrderType();
        }


        orderListAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                IntentUtil.Intent_OrderDetailActivity(getActivity()
                        , orderListAdapter.getDatas().get(position), type);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (loginBean!=null&&!Judge.getBoolean_isNull(loginBean.getUserId()))
                {
                    page = 0;
                    initOrderType();
                    refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                }

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (loginBean!=null&&!Judge.getBoolean_isNull(loginBean.getUserId()))
                {
                    page = page + 1 + limit;
                    initOrderType();
                    refreshLayout.finishLoadMore(200);
                }

            }
        });

        orderListAdapter.setOnOrderItemListener(new OnOrderItemListener() {
            //初审补资料
            @Override
            public void checkMessageFirstTral(OrderListBean orderListBean) {
                int osition = orderListAdapter.getDatas().indexOf(orderListBean);
                if (osition != -1) {
                    checkposition = osition;
                    IntentUtil.Intent_OrderCompletionActivity(getActivity(),
                            orderListBean);
                }

            }

            //终审补资料
            @Override
            public void checkMessageLastInstance(OrderListBean orderListBean) {
                int osition = orderListAdapter.getDatas().indexOf(orderListBean);
                if (osition != -1) {
                    checkposition = osition;
                    IntentUtil.Intent_OrderLastCompletionActivity(getActivity(),
                            orderListBean);
                }
            }

            //拒批
            @Override
            public void checkMessageRefusal(OrderListBean orderListBean) {
                IntentUtil.Intent_OrderRefusalDetailActivity(getActivity(),
                        orderListBean);
            }

            //签约中
            @Override
            public void checkMessageSigning(OrderListBean orderListBean) {
                IntentUtil.Intent_OrderSigningActivity(getActivity(),
                        orderListBean);
            }
        });
        return view;
    }

    int page = 0;
    int limit = 10;

    /**
     *
     */
    private void initOrderType() {
        String status = "";
        switch (type) {
            case 0:
                status = "a";
                break;
            case 1:
                status = "b";
                break;
            case 2:
                status = "c";
                break;
        }

        HttpUtil.orderList(loginBean.getUserId(),
                status, page + "",
                limit + "", new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {

                            org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());

                            int code = jsonObject.getInt("code");
                            Log.i("asdf","obj "+jsonObject.getString("msg"));
                            if (code == 0 && page == 0) {
                                List<OrderListBean> orderListBeans = com.alibaba.fastjson.JSONObject.parseArray(jsonObject.getString("msg"), OrderListBean.class);
                                if (page == 0) {
                                    orderListAdapter.refreshData(orderListBeans);
                                } else {
                                    orderListAdapter.loadMoreData(orderListBeans);
                                }

                            } else {

                            }


                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

}
