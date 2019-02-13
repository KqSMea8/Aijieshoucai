package com.example.rongjiaying.aijieshoucai.home.fragment;


import android.os.Bundle;
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
import com.example.rongjiaying.aijieshoucai.constant.UrlConstant;
import com.example.rongjiaying.aijieshoucai.home.adapter.BrokerListAdapter;
import com.example.rongjiaying.aijieshoucai.home.bean.BrokerBean;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.IntentUtil;
import com.lzy.okgo.model.Response;

import org.json.JSONException;

import java.util.List;

/**
 */
public class BrokderFragment extends BaseFragment {

    int position = 0;

    public BrokderFragment() {
        // Required empty public constructor
    }

    /**
     */
    // TODO: Rename and change types and number of parameters
    public static BrokderFragment newInstance(int position) {
        BrokderFragment fragment = new BrokderFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("position", 0);
        }
    }

    ProgressBar progressBar;
    BrokerListAdapter brokerListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brokder, container, false);
        progressBar = view.findViewById(R.id.progressbar);
        RecyclerView recyclerView=view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        brokerListAdapter=new BrokerListAdapter(getActivity());
        recyclerView.setAdapter(brokerListAdapter);
        initData();

        brokerListAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position!=-1)
                {
                    IntentUtil.Intent_BrokderRankingDetailActivity(getActivity(),
                            brokerListAdapter.getDatas().get(position).getUserId());
                }
            }
        });
        return view;
    }

    private void initData() {
        String url = "";
        switch (position) {
            case 0:
                url = UrlConstant.getRankingPresent;
                break;
            case 1:
                url = UrlConstant.getRankingInCom;
                break;
            case 2:
                url = UrlConstant.getRankingApprove;
                break;
            case 3:
                url = UrlConstant.getRankingLevel;
                break;
            case 4:
                url = UrlConstant.getRankingSize;
                break;
            default:
                url = UrlConstant.achievementsStatistical;
                break;


        }
        HttpUtil.getRankingPresent(url, new ProgressBarStringCallback(progressBar) {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        List<BrokerBean> brokerBeans = JSONObject.parseArray(jsonObject.getString("msg"), BrokerBean.class);
                        Log.i("asdf",""+jsonObject.getString("msg"));
                        if (brokerBeans != null && brokerBeans.size() > 0) {
                            brokerListAdapter.refreshData(brokerBeans);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

}
