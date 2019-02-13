package com.example.rongjiaying.aijieshoucai.my.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSONObject;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.my.bean.PerformanceStatisticsBean;
import com.example.rongjiaying.aijieshoucai.my.dialogfragment.SelectYearMonthDialogFragment;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 业绩统计
 */
public class PerformanceStatisticsActivity extends BaseActivity implements View.OnClickListener {
    LoginBean loginBean;
    ProgressBar progressBar;
    AppCompatTextView tvMonth;

    AppCompatTextView tvAllinto, tvAllexamineapprove, tvAllloan;//累计
    AppCompatTextView tvinto, tvexamineapprove, tvloan;//这个月

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_statistics);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_performancestatistics));
        AppCompatImageView ivRightIcon = findViewById(R.id.iv_righticon);
        ivRightIcon.setImageResource(R.drawable.icon_date);
        progressBar = findViewById(R.id.progressbar);
        ivRightIcon.setVisibility(View.VISIBLE);
        ivRightIcon.setOnClickListener(this);
        tvMonth = findViewById(R.id.tv_month);//月份
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        datetime = simpleDateFormat.format(new Date());
        tvMonth.setText(datetime);
        tvMonth.setOnClickListener(this);
        //累计
        tvAllinto = findViewById(R.id.tv_allinto);
        tvAllexamineapprove = findViewById(R.id.tv_allexamineapprove);
        tvAllloan = findViewById(R.id.tv_allloan);
        //累计

        //这个月
        tvinto = findViewById(R.id.tv_into);
        tvexamineapprove = findViewById(R.id.tv_examineapprove);
        tvloan = findViewById(R.id.tv_loan);
        //这个月
        initPerformanceStatisticsData();
    }

    String datetime = "";

    private void initPerformanceStatisticsData() {

        String message = getSharedFileUtils().getString(MyConstant.loginuser);
        loginBean = JSONObject.parseObject(message, LoginBean.class);
        HttpUtil.achievementsStatistical(
                loginBean.getUserId(),
                datetime,
                new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());
                            int code = jsonObject.getInt("code");
                            if (code == 0) {
                                Log.i("asdf", "" + jsonObject.getString("msg"));

                                PerformanceStatisticsBean performanceStatisticsBean =
                                        JSONObject.parseObject(jsonObject.getString("msg"), PerformanceStatisticsBean.class);
                                if (performanceStatisticsBean != null) {
                                    tvAllinto.setText(performanceStatisticsBean.getMy_incom() + "件");
                                    tvAllexamineapprove.setText(performanceStatisticsBean.getMy_approve() + "件");
                                    tvAllloan.setText(performanceStatisticsBean.getMy_loan() + "件");

                                    tvinto.setText(performanceStatisticsBean.getThis_month_incom() + "件");
                                    tvexamineapprove.setText(performanceStatisticsBean.getThis_month_approve() + "件");
                                    tvloan.setText(performanceStatisticsBean.getThis_month_loan() + "件");
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

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
                }
        );
    }

    @Override
    public AppCompatActivity getActivity() {
        return PerformanceStatisticsActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.iv_righticon:
                SelectYearMonthDialogFragment selectYearMonthDialogFragment =
                        SelectYearMonthDialogFragment.newInstance();

                selectYearMonthDialogFragment.show(getSupportFragmentManager(), selectYearMonthDialogFragment.getClass().getName());

                selectYearMonthDialogFragment.setOnSelectYearMonthListener(new SelectYearMonthDialogFragment.OnSelectYearMonthListener() {
                    @Override
                    public void onSelectYearMonthItem(String date) {
                        datetime=date;
                        initPerformanceStatisticsData();
                    }
                });
                break;
        }
    }
}
