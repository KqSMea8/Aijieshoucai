package com.example.rongjiaying.aijieshoucai.home.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rongjiaying.aijieshoucai.BaseFragment;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseAdapter;
import com.example.rongjiaying.aijieshoucai.constant.InternetConstant;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.constant.UrlConstant;
import com.example.rongjiaying.aijieshoucai.home.adapter.BrokerHomePagerAdapter;
import com.example.rongjiaying.aijieshoucai.home.adapter.ProductListHomePagerAdapter;
import com.example.rongjiaying.aijieshoucai.home.bean.BannerBean;
import com.example.rongjiaying.aijieshoucai.home.bean.BrokerBean;
import com.example.rongjiaying.aijieshoucai.home.bean.ProductListBean;
import com.example.rongjiaying.aijieshoucai.home.bean.RollBean;
import com.example.rongjiaying.aijieshoucai.imageloader.GlideImageLoader;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.IntentUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.example.rongjiaying.aijieshoucai.widget.ZouMaDengTextView;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.youth.banner.Banner;

import org.json.JSONException;

import java.util.Arrays;
import java.util.List;

import jiguang.chat.application.JGApplication;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePagerFragment extends BaseFragment implements View.OnClickListener {
    LoginBean loginBean;

    public HomePagerFragment() {
        // Required empty public constructor
    }

    /**
     */
    // TODO: Rename and change types and number of parameters
    public static HomePagerFragment newInstance() {
        HomePagerFragment fragment = new HomePagerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    Banner banner;
    ZouMaDengTextView verticalScrollTextView;//滚动
    LinearLayout llBusinesslayout, llBrokerlayout;//业务layout
    ProductListHomePagerAdapter productListHomePagerAdapter;//业务adapter
    BrokerHomePagerAdapter brokerHomePagerAdapter;//经纪人adapter
    AppCompatImageView ivAdvertising;//广告图片

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_pager, container, false);
        banner = view.findViewById(R.id.banner);
        String message = getSharedFileUtils(getActivity()).getString(MyConstant.loginuser);
        loginBean = JSONObject.parseObject(message, LoginBean.class);
        //  banner.setLayoutParams(layoutParams);
      verticalScrollTextView = view.findViewById(R.id.v_text_view);
        //banner
        initBannerData();
//滚动消息
        initRollData();
        //广告图片
        ivAdvertising = view.findViewById(R.id.iv_advertising);

        RequestOptions options2 = new RequestOptions()
                .fitCenter()
                .error(R.drawable.ic_boxing_default_image)
                .placeholder(R.drawable.ic_boxing_default_image);
        Glide.with(ivAdvertising.getContext())
                .load(InternetConstant.imageurl + MyConstant.ivAdvertising)
                .apply(options2)
                .into(ivAdvertising);
        //广告图片
        //业务
        view.findViewById(R.id.tv_checkallproduct).setOnClickListener(this);
        llBusinesslayout = view.findViewById(R.id.ll_businesslayout);
        RecyclerView rvBusiness = view.findViewById(R.id.rv_business);
        rvBusiness.setLayoutManager(new LinearLayoutManager(getActivity()));
        productListHomePagerAdapter = new ProductListHomePagerAdapter(getActivity());
        rvBusiness.setAdapter(productListHomePagerAdapter);
        initBusinessData();

        productListHomePagerAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position != -1) {

                    if (loginBean!=null&&!Judge.getBoolean_isNull(loginBean.getUserId()))
                    {
                        IntentUtil.Intent_ProductDetailActivity(getActivity(),
                                productListHomePagerAdapter.getDatas().get(position));
                    }else {
                        Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
                        JGApplication app = (JGApplication) getActivity().getApplication();
                        List<AppCompatActivity> activities = app.activities;
                        for (Activity act : activities) {
                            act.finish();//显式结束
                        }
                        getSharedFileUtils(getActivity()).putString(MyConstant.loginuser,"");
                        IntentUtil.Intent_LoginActivity(getActivity(),false);
                    }


                }
            }
        });
        //业务

        //按提现分的明星经纪人
        llBrokerlayout = view.findViewById(R.id.ll_brokerlayout);
        view.findViewById(R.id.tv_checkallrankinglist).setOnClickListener(this);
        RecyclerView rvBroker = view.findViewById(R.id.rv_broker);
        rvBroker.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        brokerHomePagerAdapter = new BrokerHomePagerAdapter(getActivity());
        rvBroker.setAdapter(brokerHomePagerAdapter);
        initBrokerData();

        brokerHomePagerAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position != -1) {



                    if (loginBean!=null&&!Judge.getBoolean_isNull(loginBean.getUserId()))
                    {
                        IntentUtil.Intent_BrokderRankingDetailActivity(getActivity(),
                                brokerHomePagerAdapter.getDatas().get(position).getUserId());
                    }else {
                        Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
                        JGApplication app = (JGApplication) getActivity().getApplication();
                        List<AppCompatActivity> activities = app.activities;
                        for (Activity act : activities) {
                            act.finish();//显式结束
                        }
                        getSharedFileUtils(getActivity()).putString(MyConstant.loginuser,"");
                        IntentUtil.Intent_LoginActivity(getActivity(),false);
                    }
                }
            }
        });
        //按提现分的明星经纪人
        return view;
    }

    /**
     * 获取经纪人 提现排列
     */
    private void initBrokerData() {
        HttpUtil.getRankingPresent(UrlConstant.getRankingPresent, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());
                    int code = jsonObject.getInt("code");
                    if (code != 0) {
                        llBrokerlayout.setVisibility(View.GONE);
                    } else {
                        try {
                            List<BrokerBean> brokerBeans = JSONObject.parseArray(jsonObject.getString("msg"), BrokerBean.class);
                            if (brokerBeans != null && brokerBeans.size() > 0) {
                                if (brokerBeans.size() > 8) {
                                    brokerHomePagerAdapter.refreshData(brokerBeans.subList(0, 8));
                                } else {
                                    brokerHomePagerAdapter.refreshData(brokerBeans);
                                }
                            } else {
                                llBrokerlayout.setVisibility(View.GONE);
                            }

                        } catch (Exception e) {
                            llBrokerlayout.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    llBrokerlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                llBrokerlayout.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 业务
     */
    private void initBusinessData() {
        HttpUtil.productList(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());

                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        Log.i("asdf",jsonObject.getString("msg"));
                        List<ProductListBean> productListBeans = JSONObject.parseArray(jsonObject.getString("msg"), ProductListBean.class);
                        if (productListBeans != null && productListBeans.size() > 0) {
                            llBusinesslayout.setVisibility(View.VISIBLE);

                                productListHomePagerAdapter.refreshData(productListBeans);


                        } else {
                            llBusinesslayout.setVisibility(View.GONE);
                        }
                    } else {
                        llBusinesslayout.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    llBusinesslayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                llBusinesslayout.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 滚动消息
     */
    private void initRollData() {
        //http://114.116.100.233:8080/x_springboot/app/getRolling
        HttpUtil.rollData(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());
                    int code = jsonObject.getInt("code");
                    String msg = jsonObject.getString("msg");
                    if (code == 0) {
                        final List<RollBean> rollBeans = JSONObject.parseArray(msg, RollBean.class);
                        if (rollBeans != null && rollBeans.size() > 0) {
                          StringBuffer sb=new StringBuffer();
                          sb.append("[爱杰搜财]");
                            for (int i = 0; i < rollBeans.size(); i++) {
                                sb.append(rollBeans.get(i).getData());
                            }

                            verticalScrollTextView.setText(sb.toString());


                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 轮播图
     */
    private void initBannerData() {
        HttpUtil.banner(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {

                    List<BannerBean> bannerBeans = JSONObject.parseArray(response.body(), BannerBean.class);

                    if (bannerBeans != null && bannerBeans.size() > 0) {
                        String[] images = new String[bannerBeans.size()];
                        for (int i = 0; i < bannerBeans.size(); i++) {
                            images[i] = InternetConstant.imageurl + bannerBeans.get(i).getUrl();
                        }
                        if (images.length > 0) {
                            List list = Arrays.asList(images);
                            try {
                                //设置图片加载器
                                banner.setImageLoader(new GlideImageLoader());
                                //设置图片集合
                                banner.setImages(list);
                                banner.setDelayTime(3000);
                                //banner设置方法全部调用完毕时最后调用
                                banner.start();

                            } catch (Exception e) {

                            }
                        }

                    }

                } catch (Exception e) {

                }

            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_checkallproduct:



                if (loginBean!=null&&!Judge.getBoolean_isNull(loginBean.getUserId()))
                {
                    IntentUtil.Intent_ProductListActivity(getActivity());
                }else {
                    Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
                    JGApplication app = (JGApplication) getActivity().getApplication();
                    List<AppCompatActivity> activities = app.activities;
                    for (Activity act : activities) {
                        act.finish();//显式结束
                    }
                    getSharedFileUtils(getActivity()).putString(MyConstant.loginuser,"");
                    IntentUtil.Intent_LoginActivity(getActivity(),false);
                }
                break;

            case R.id.tv_checkallrankinglist:


                if (loginBean!=null&&!Judge.getBoolean_isNull(loginBean.getUserId()))
                {
                    IntentUtil.Intent_BrokderRankinglistActivity(getActivity());
                }else {
                    Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
                    JGApplication app = (JGApplication) getActivity().getApplication();
                    List<AppCompatActivity> activities = app.activities;
                    for (Activity act : activities) {
                        act.finish();//显式结束
                    }
                    getSharedFileUtils(getActivity()).putString(MyConstant.loginuser,"");
                    IntentUtil.Intent_LoginActivity(getActivity(),false);
                }
                break;
        }
    }
}
