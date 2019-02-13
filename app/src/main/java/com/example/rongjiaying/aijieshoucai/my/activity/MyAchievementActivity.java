package com.example.rongjiaying.aijieshoucai.my.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.my.bean.MyAchievementBean;
import com.example.rongjiaying.aijieshoucai.my.eventbus.AddAchievementEventbus;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.IntentUtil;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 我的业绩
 */
public class MyAchievementActivity extends BaseActivity implements View.OnClickListener {
    LoginBean loginBean;
    AppCompatTextView tvInto, tvExamineapprove, tvLoan;//进件   审批  放款
    AppCompatTextView tvAllprice, tvG, tvM;
    AppCompatTextView tvWithdrawal;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUserMessage(AddAchievementEventbus messageEvent) {
        tvWithdrawal.setFocusable(false);
        if (messageEvent != null) {
            initMyachievements();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_achievement);
        EventBus.getDefault().register(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        tvInto = findViewById(R.id.tv_into);
        tvExamineapprove = findViewById(R.id.tv_examineapprove);
        tvLoan = findViewById(R.id.tv_loan);
        tvG = findViewById(R.id.tv_g);
        tvM = findViewById(R.id.tv_m);
        tvAllprice = findViewById(R.id.tv_allprice);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_myachievement));
        String message = getSharedFileUtils().getString(MyConstant.loginuser);
        loginBean = JSONObject.parseObject(message, LoginBean.class);
        findViewById(R.id.ll_performancestatistics).setOnClickListener(this);//业绩统计
        findViewById(R.id.ll_withdrawalsrecord).setOnClickListener(this);//提现记录
        findViewById(R.id.ll_cashrule).setOnClickListener(this); //提现规则
        tvWithdrawal = findViewById(R.id.tv_withdrawal);
        tvWithdrawal.setOnClickListener(this);
        initMyachievements();
    }

    MyAchievementBean myAchievementBean;//我的业绩 bean

    /**
     * 我的业绩
     */
    private void initMyachievements() {
        HttpUtil.myachievements(
                loginBean.getUserId(),
                new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());

                            int code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            if (code == 0) {
                                tvWithdrawal.setFocusable(true);
                                myAchievementBean = JSONObject.parseObject(msg, MyAchievementBean.class);
                                if (myAchievementBean != null) {
                                    tvInto.setText(myAchievementBean.getMy_incom() + "件");
                                    tvExamineapprove.setText(myAchievementBean.getMy_approve() + "件");
                                    tvLoan.setText(myAchievementBean.getMy_loan() + "件");
                                    tvG.setText("G币: " + myAchievementBean.getMoneyG() + "");
                                    tvM.setText("M币: " + myAchievementBean.getMoneyM());
                                    tvAllprice.setText(getAllPrice(Double.valueOf(myAchievementBean.getMoneyG()), Double.valueOf(myAchievementBean.getMoneyM()), myAchievementBean) + "");

                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    private double getAllPrice(double moneyG, double moneyM
            , MyAchievementBean myAchievementBean) {
        if (moneyG < 100) //g币小于100
        {
            return 0;
        } else {
            double d = 0;
            double mg = (moneyG * myAchievementBean.getMToGPersent());
            if (mg > moneyM) {
                d = moneyG + (moneyM * myAchievementBean.getM_to_g_rate());
            } else {
                d = moneyG + mg;
            }
            return d;
        }
    }

    @Override
    public AppCompatActivity getActivity() {
        return MyAchievementActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            //业绩统计
            case R.id.ll_performancestatistics:
                IntentUtil.Intent_PerformanceStatisticsActivity(getActivity());
                break;
//提现记录
            case R.id.ll_withdrawalsrecord:
                IntentUtil.Intent_WithdrawalsrecordActivity(getActivity());
                break;

            //提现
            case R.id.tv_withdrawal:
                if (myAchievementBean != null) {
                    IntentUtil.Intent_WithdrawActivity(getActivity(), myAchievementBean);
                }

                break;
            //提现规则
            case R.id.ll_cashrule:
                IntentUtil.Intent_CashRuleActivity(getActivity());
                break;

        }
    }
}
