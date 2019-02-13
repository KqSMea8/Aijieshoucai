package com.example.rongjiaying.aijieshoucai.my.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSONObject;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseAdapter;
import com.example.rongjiaying.aijieshoucai.callback.ProgressBarStringCallback;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.my.adapter.WithdrawalsrecordAdapter;
import com.example.rongjiaying.aijieshoucai.my.bean.WithdrawalsrecordBean;
import com.example.rongjiaying.aijieshoucai.my.bean.WithdrawalsrecordDetailListBean;
import com.example.rongjiaying.aijieshoucai.my.dialogfragment.SelectYearMonthDialogFragment;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;

import java.util.List;

/**
 * 提现记录
 */
public class WithdrawalsrecordActivity extends BaseActivity implements View.OnClickListener {
    LoginBean loginBean = null;
    WithdrawalsrecordAdapter withdrawalsrecordAdapter;
    ProgressBar progressBar;
    AppCompatTextView tvAllwithdrawalsrecord;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag("getPresentListByMonthList");
        OkGo.getInstance().cancelTag("initWithdrawalsrecord");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawalsrecord);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvAllwithdrawalsrecord = findViewById(R.id.tv_allwithdrawalsrecord);
        tvTitle.setText(getString(R.string.string_withdrawalsrecord));
        String message = getSharedFileUtils().getString(MyConstant.loginuser);
        loginBean = JSONObject.parseObject(message, LoginBean.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        withdrawalsrecordAdapter = new WithdrawalsrecordAdapter(getActivity());
        recyclerView.setAdapter(withdrawalsrecordAdapter);
        progressBar = findViewById(R.id.progressbar);
        initWithdrawalsrecord("");

//        withdrawalsrecordAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                if (position != -1) {
//                    withdrawalsrecordDetail(withdrawalsrecordAdapter.getDatas().get(position));
//                }
//            }
//        });
        findViewById(R.id.iv_date).setOnClickListener(this);//选择日期
    }

//    /**
//     * 提现详情
//     *
//     * @param withdrawalsrecordBean
//     */
//    private void withdrawalsrecordDetail(final WithdrawalsrecordBean withdrawalsrecordBean) {
//        HttpUtil.getPresentListByMonthList(loginBean.getUserId(),
//                getTime(withdrawalsrecordBean.getCreateTime()), new ProgressBarStringCallback(progressBar) {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        Log.i("asdf", "reason" + response.body());
//                        try {
//                            org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());
//
//                            int code = jsonObject.getInt("code");
//                            if (code == 0) {
//                                List<WithdrawalsrecordDetailListBean> withdrawalsrecordDetailListBeans = JSONObject.parseArray(jsonObject.getString("msg"), WithdrawalsrecordDetailListBean.class);
//                                if (withdrawalsrecordDetailListBeans != null && withdrawalsrecordDetailListBeans.size() > 0) {
//                                    withdrawalsrecordAdapter.setWdtailList(withdrawalsrecordBean, withdrawalsrecordDetailListBeans);
//                                }
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//    }

    private String getTime(String createTime) {
        if (Judge.getBoolean_isNull(createTime))
        {
            return "";
        }else {
            if (createTime.length() == 7) {
                return createTime;
            } else {
                if (createTime.length() < 7) {
                    return createTime;
                } else {
                    return createTime.substring(0, 7);
                }
            }
        }


    }

    private void initWithdrawalsrecord(final String month) {
        HttpUtil.initWithdrawalsrecord(loginBean.getUserId(),
                month, new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());

                            int code = jsonObject.getInt("code");
                            if (code == 0) {
                                Log.i("asdf", "" + jsonObject.getString("msg"));

                                List<WithdrawalsrecordBean> withdrawalsrecordBeans = JSONObject.parseArray(jsonObject.getString("msg"), WithdrawalsrecordBean.class);
                                if (withdrawalsrecordBeans != null && withdrawalsrecordBeans.size() > 0) {
                                    withdrawalsrecordAdapter.refreshData(withdrawalsrecordBeans);
                                    tvAllwithdrawalsrecord.setText("总提现 :" + withdrawalsrecordAdapter.getAllSchievement() + "元");
                                } else {
                                    if (!Judge.getBoolean_isNull(month)) {
                                        withdrawalsrecordAdapter.getDatas().clear();
                                        withdrawalsrecordAdapter.notifyDataSetChanged();

                                     //   tvAllwithdrawalsrecord.setText("总提现 :0元");
                                    }

                                }


                            }

                        } catch (Exception e) {
                        }
                    }
                });
    }

    @Override
    public AppCompatActivity getActivity() {
        return WithdrawalsrecordActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            //选择日期
            case R.id.iv_date:
                SelectYearMonthDialogFragment selectYearMonthDialogFragment = SelectYearMonthDialogFragment.newInstance();
                selectYearMonthDialogFragment.show(getSupportFragmentManager(), selectYearMonthDialogFragment.getClass().getName());
                selectYearMonthDialogFragment.setOnSelectYearMonthListener(new SelectYearMonthDialogFragment.OnSelectYearMonthListener() {
                    @Override
                    public void onSelectYearMonthItem(String date) {
                        Log.i("asdf", "date " + date);
                        initWithdrawalsrecord(date);
                    }
                });
                break;
        }
    }
}
