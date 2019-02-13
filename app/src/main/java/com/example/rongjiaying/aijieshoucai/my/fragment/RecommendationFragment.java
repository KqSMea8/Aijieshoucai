package com.example.rongjiaying.aijieshoucai.my.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.rongjiaying.aijieshoucai.callback.ProgressBarStringCallback;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.constant.UrlConstant;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.my.adapter.RecommendationListAdapter;
import com.example.rongjiaying.aijieshoucai.my.bean.RecommendationBean;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.IntentUtil;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecommendationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecommendationFragment extends BaseFragment {


    public RecommendationFragment() {
        // Required empty public constructor
    }

    int type = 0;
    ProgressBar progressBar;

    // TODO: Rename and change types and number of parameters
    public static RecommendationFragment newInstance(int type) {
        RecommendationFragment fragment = new RecommendationFragment();
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

    LoginBean loginBean;
    RecommendationListAdapter recommendationListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommendation, container, false);
        progressBar = view.findViewById(R.id.progressbar);
        String message = getSharedFileUtils(getActivity()).getString(MyConstant.loginuser);
        loginBean = JSONObject.parseObject(message, LoginBean.class);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recommendationListAdapter = new RecommendationListAdapter(getActivity());
        recyclerView.setAdapter(recommendationListAdapter);
        initData();

        recommendationListAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position != -1) {
                    IntentUtil.Intent_BrokderRankingDetailActivity(getActivity(),
                            recommendationListAdapter.getDatas().get(position).getUserId());
                }
            }
        });
        return view;
    }

    private void initData() {
        String url = "";
        switch (type) {
            case 0:
                url = UrlConstant.getDirectUser;
                break;
            case 1:
                url = UrlConstant.getInDirectUser;
                break;
            default:
                url = UrlConstant.getDirectUser;
                break;
        }

        HttpUtil.myRecommendation(url, loginBean.getUserId(), new ProgressBarStringCallback(progressBar) {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());

                    int code = jsonObject.getInt("code");
                    String msg = jsonObject.getString("msg");

                    if (code == 0) {
                        Log.i("asdf", jsonObject.getString("msg"));
                        List<RecommendationBean> recommendationBeans = JSONObject.parseArray(msg, RecommendationBean.class);
                        if (recommendationBeans != null && recommendationBeans.size() > 0) {
                            recommendationListAdapter.refreshData(recommendationBeans);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
