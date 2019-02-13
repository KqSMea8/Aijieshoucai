package com.example.rongjiaying.aijieshoucai.my.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.interfac.OnMyBankItemListener;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.my.adapter.MyBankAdapter;
import com.example.rongjiaying.aijieshoucai.my.bean.MyBankListBean;
import com.example.rongjiaying.aijieshoucai.my.eventbus.AddBankSuccessEventbus;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.IntentUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;

import java.util.List;

/**
 * 我的银行卡
 */
public class MyBankActivity extends BaseActivity implements View.OnClickListener {
    LoginBean loginBean;
    ProgressBar progressBar;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag("myBank");
        OkGo.getInstance().cancelTag("deleBank");
        EventBus.getDefault().unregister(this);
    }

    MyBankAdapter myBankAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bank);
        EventBus.getDefault().register(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_mybank));
        String message = getSharedFileUtils().getString(MyConstant.loginuser);
        loginBean = JSONObject.parseObject(message, LoginBean.class);
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.btn_addbank).setOnClickListener(this);
        progressBar = findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myBankAdapter = new MyBankAdapter(getActivity());
        recyclerView.setAdapter(myBankAdapter);
        initMyBank();

        myBankAdapter.setOnMyBankItemListener(new OnMyBankItemListener() {
            @Override
            public void onDefaultItem(MyBankListBean myBankListBean) {
                defaultBank(myBankListBean);
            }

            @Override
            public void onDelItem(MyBankListBean myBankListBean) {
                deleBank(myBankListBean);
            }
        });
    }

    /**
     * 设置默认
     *
     * @param myBankListBean
     */
    private void defaultBank(MyBankListBean myBankListBean) {
        String defaultid = myBankAdapter.getDefaultId();
        HttpUtil.updateBankDefault(myBankListBean.getId(), defaultid
                , new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());
                            int code = jsonObject.getInt("code");
                            if (code == 0) {
                               initMyBank();
                            } else {
                                Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Toast.makeText(getActivity(), "设置失败", Toast.LENGTH_SHORT).show();
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

    /**
     * 删除银行卡
     *
     * @param myBankListBean
     */
    private void deleBank(final MyBankListBean myBankListBean) {
        HttpUtil.deleBank(myBankListBean.getId(),
                new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());
                            int code = jsonObject.getInt("code");
                            Log.i("asdf", "mybank" + jsonObject.getString("msg"));
                            if (code == 0) {
                                myBankAdapter.removeItem(myBankListBean);
                            } else {
                                Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 我的银行卡
     */
    private void initMyBank() {
        HttpUtil.myBank(loginBean.getUserId(),
                new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());
                            int code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            Log.i("asdf","mybank"+msg);
                            if (code == 0) {
                                List<MyBankListBean> listBeans = JSONObject.parseArray(msg, MyBankListBean.class);
                                myBankAdapter.refreshData(listBeans);
                            }


                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUserMessage(AddBankSuccessEventbus messageEvent) {
        if (messageEvent != null) {
            initMyBank();
        }
    }

    @Override
    public AppCompatActivity getActivity() {
        return MyBankActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            //添加银行卡
            case R.id.btn_addbank:
                IntentUtil.Intent_AddBankActivity(getActivity());
                break;
        }
    }


}
