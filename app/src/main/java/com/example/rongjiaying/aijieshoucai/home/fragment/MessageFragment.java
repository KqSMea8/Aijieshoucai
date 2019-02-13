package com.example.rongjiaying.aijieshoucai.home.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONObject;
import com.example.rongjiaying.aijieshoucai.BaseFragment;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.callback.ProgressBarStringCallback;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.IntentUtil;
import com.example.rongjiaying.aijieshoucai.util.SharepercentUtils;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import jiguang.chat.activity.fragment.ConversationListFragment;
import jiguang.chat.view.ConversationListView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends ConversationListFragment implements View.OnClickListener {

    protected SharepercentUtils sharepercentUtils;
    /**
     * 获取文件操作工具
     *
     * @return body
     */
    public SharepercentUtils getSharedFileUtils(Activity activity) {
        if (sharepercentUtils == null) {
            sharepercentUtils = new SharepercentUtils(activity);
        }
        return sharepercentUtils;
    }
    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
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
    LoginBean loginBean;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        view.findViewById(R.id.ll_exservice).setOnClickListener(this);
        view.findViewById(R.id.ll_contact).setOnClickListener(this);//联系人列表
        view.findViewById(R.id.ll_systemmessage).setOnClickListener(this);//系统通知

        String message = getSharedFileUtils(getActivity()).getString(MyConstant.loginuser);
        loginBean = JSONObject.parseObject(message, LoginBean.class);
        initPersonData();
        return view;
    }
    private void initPersonData() {
        HttpUtil.myPersonal(loginBean.getUserId(),
                new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i("asdf","message 搜财 err");
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i("asdf","message 搜财"+response.body());
                    }
                });
                }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exservice:
                IntentUtil.Intent_ExserviceActivity(getActivity());
                break;

            //联系人列表
            case R.id.ll_contact:
                IntentUtil.Intent_ContactListActivity(getActivity());
                break;
            //系统消息
            case R.id.ll_systemmessage:
                IntentUtil.Intent_MyMessageActivity(getActivity());
                break;
        }
    }
}
