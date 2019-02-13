package com.example.rongjiaying.aijieshoucai.my.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.callback.ProgressBarStringCallback;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.my.bean.MyAchievementBean;
import com.example.rongjiaying.aijieshoucai.my.eventbus.AddAchievementEventbus;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.IntentUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 提现
 */
public class WithdrawActivity extends BaseActivity implements View.OnClickListener {
    MyAchievementBean myAchievementBean;
    AppCompatEditText etG, etM;//editext g币 M币
    AppCompatTextView tvWithdrawmoney, tvG, tvM;//本次提现   元
    AppCompatTextView tvErrGMax;
    LoginBean loginBean;
    ProgressBar progressBar;
    AppCompatTextView tvCheckG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        String message = getSharedFileUtils().getString(MyConstant.loginuser);
        loginBean = JSONObject.parseObject(message, LoginBean.class);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_withdraw));
        tvCheckG = findViewById(R.id.tv_checkg);
        AppCompatImageView ivRighticon = findViewById(R.id.iv_righticon);
        ivRighticon.setVisibility(View.VISIBLE);
        ivRighticon.setImageResource(R.drawable.icon_withdrawalsrecord);
        ivRighticon.setOnClickListener(this);
        myAchievementBean = getIntent().getParcelableExtra("item");
        etG = findViewById(R.id.et_g);
        etM = findViewById(R.id.et_m);
        tvG = findViewById(R.id.tv_g);
        tvM = findViewById(R.id.tv_m);
        progressBar = findViewById(R.id.progressbar);
        tvErrGMax = findViewById(R.id.tv_gerrmax);
        tvWithdrawmoney = findViewById(R.id.tv_withdrawmoney);
        findViewById(R.id.btn_commit).setOnClickListener(this);
        if (!Judge.getBoolean_isNull(myAchievementBean.getMoneyG())) {
            tvG.setText("剩余G币数量：" + myAchievementBean.getMoneyG() + "个");

        }
        if (!Judge.getBoolean_isNull(myAchievementBean.getMoneyM())) {
            tvM.setText("剩余M币数量：" + myAchievementBean.getMoneyM() + "个");
        }
        setEditextListener();

    }

    private void setEditextListener() {
        etG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().contains(".") && s.toString().indexOf(".") != 1) {
                    Toast.makeText(getActivity(), "请正确输入参数", Toast.LENGTH_SHORT).show();
                    tvWithdrawmoney.setText("");
                    tvErrGMax.setVisibility(View.GONE);
                    return;
                }
                if (myAchievementBean != null) {
                    try {
                        if (Judge.getBoolean_isNull(etG.getText().toString())) {
                            tvWithdrawmoney.setText("0");
                            if (!Judge.getBoolean_isNull(myAchievementBean.getMoneyG())) {
                                tvG.setText("剩余G币数量：" + myAchievementBean.getMoneyG() + "个");

                            }
                            return;
                        }
                        if (Double.valueOf(myAchievementBean.getMoneyG()) < 100) {
                            Toast.makeText(getActivity(), "G币少于100无法提现", Toast.LENGTH_SHORT).show();
                            tvErrGMax.setVisibility(View.GONE);
                        } else {
                            if (Double.valueOf(myAchievementBean.getMoneyG()) < Double.valueOf(s.toString())) {
                                Toast.makeText(getActivity(), "输入的G币少于剩余G币", Toast.LENGTH_SHORT).show();
                                tvErrGMax.setVisibility(View.VISIBLE);
                                tvWithdrawmoney.setText("");
                            } else if (Double.valueOf(s.toString()) < 100) {
                                tvWithdrawmoney.setText("0");
                            } else {
                                double d = 0;
                                double mg = (Double.valueOf(etG.getText().toString()) * myAchievementBean.getMToGPersent());
                                if (!Judge.getBoolean_isNull(etM.getText().toString())) {
                                    if (mg > Double.valueOf(etM.getText().toString())) {
                                        if (Judge.getBoolean_isNull(etM.getText().toString())) {
                                            d = Double.valueOf(etG.getText().toString());
                                        } else {
                                            d = Double.valueOf(etG.getText().toString()) + (Double.valueOf(etM.getText().toString()) * myAchievementBean.getM_to_g_rate());
                                        }

                                    } else {
                                        d = Double.valueOf(etG.getText().toString()) + mg;
                                    }
                                } else {
                                    d = Double.valueOf(etG.getText().toString());
                                }
                                tvCheckG.setText("您本次可以搭配" + (Double.valueOf(etG.getText().toString()) * myAchievementBean.getMToGPersent()) + "个M币一同提现");
                                tvWithdrawmoney.setText(d + "");
                                tvErrGMax.setVisibility(View.GONE);
                                if (!Judge.getBoolean_isNull(etG.getText().toString()) && !Judge.getBoolean_isNull(myAchievementBean.getMoneyG())) {
                                    tvG.setText("剩余G币数量：" + (Double.valueOf(myAchievementBean.getMoneyG()) - Double.valueOf(etG.getText().toString())) + "个");

                                } else {
                                    tvG.setText("剩余G币数量：" + myAchievementBean.getMoneyG() + "个");
                                }

                                if (!Judge.getBoolean_isNull(etM.getText().toString()) && !Judge.getBoolean_isNull(myAchievementBean.getMoneyM())) {
                                    tvM.setText("剩余M币数量：" + (Double.valueOf(myAchievementBean.getMoneyM()) - Double.valueOf(etM.getText().toString())) + "个");

                                } else {
                                    tvM.setText("剩余M币数量：" + myAchievementBean.getMoneyM() + "个");
                                }
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "输入有误", Toast.LENGTH_SHORT).show();
                        tvErrGMax.setVisibility(View.GONE);
                    }
                } else {
                    tvErrGMax.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "输入有误", Toast.LENGTH_SHORT).show();
                }
            }
        });

        etM.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().contains(".") && s.toString().indexOf(".") != 1) {
                    Toast.makeText(getActivity(), "请正确输入参数", Toast.LENGTH_SHORT).show();
                    tvWithdrawmoney.setText("");
                    // etG.setText("");
                    return;
                }
                if (myAchievementBean != null) {
                    try {
                        if (Judge.getBoolean_isNull(etM.getText().toString())) {
                            if (!Judge.getBoolean_isNull(myAchievementBean.getMoneyM())) {
                                tvM.setText("剩余G币数量：" + myAchievementBean.getMoneyM() + "个");

                            }
                            return;
                        }
                        if (Double.valueOf(myAchievementBean.getMoneyM()) < Double.valueOf(s.toString())) {
                            Toast.makeText(getActivity(), "输入的M币少于剩余M币", Toast.LENGTH_SHORT).show();
                        } else {
                            if (Judge.getBoolean_isNull(etG.getText().toString())) {
                                Toast.makeText(getActivity(), "请输入G币", Toast.LENGTH_SHORT).show();
                            } else {
                                double d = 0;
                                double mg = (Double.valueOf(etG.getText().toString()) * myAchievementBean.getMToGPersent());
                                if (mg > Double.valueOf(etM.getText().toString())) {
                                    d = Double.valueOf(etG.getText().toString()) + (Double.valueOf(etM.getText().toString()) * myAchievementBean.getM_to_g_rate());
                                } else {
                                    d = Double.valueOf(etG.getText().toString()) + mg;
                                }
                                tvWithdrawmoney.setText(d + "");
                                if (!Judge.getBoolean_isNull(etM.getText().toString()) && !Judge.getBoolean_isNull(myAchievementBean.getMoneyM())) {
                                    tvM.setText("剩余M币数量：" + (Double.valueOf(myAchievementBean.getMoneyM()) - Double.valueOf(etM.getText().toString())) + "个");

                                } else {
                                    tvM.setText("剩余M币数量：" + myAchievementBean.getMoneyM() + "个");
                                }
                                if (!Judge.getBoolean_isNull(etG.getText().toString()) && !Judge.getBoolean_isNull(myAchievementBean.getMoneyG())) {
                                    tvG.setText("剩余G币数量：" + (Double.valueOf(myAchievementBean.getMoneyG()) - Double.valueOf(etG.getText().toString())) + "个");

                                } else {
                                    tvG.setText("剩余G币数量：" + myAchievementBean.getMoneyG() + "个");
                                }
                            }

                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "输入有误", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "输入有误", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public AppCompatActivity getActivity() {
        return WithdrawActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            //提现
            case R.id.btn_commit:
                try {
                    if (Judge.getBoolean_isNull(myAchievementBean.getMoneyG())) {
                        Toast.makeText(getActivity(), "无法提现", Toast.LENGTH_SHORT).show();
                    } else if (Double.valueOf(myAchievementBean.getMoneyG()) < 100) {
                        Toast.makeText(getActivity(), "G币少于100无法提现", Toast.LENGTH_SHORT).show();
                    } else if (Judge.getBoolean_isNull(etG.getText().toString())) {
                        Toast.makeText(getActivity(), "请输入提现G币数量", Toast.LENGTH_SHORT).show();
                    } else if (Double.valueOf(etG.getText().toString()) < 100) {
                        Toast.makeText(getActivity(), "输入G币少于100无法提现", Toast.LENGTH_SHORT).show();
                    } else if (Double.valueOf(etG.getText().toString()) > Double.valueOf(myAchievementBean.getMoneyG())) {
                        Toast.makeText(getActivity(), "输入G币不能大于剩余G币数量", Toast.LENGTH_SHORT).show();
                    } else if (!Judge.getBoolean_isNull(etM.getText().toString()) && Double.valueOf(etM.getText().toString()) > Double.valueOf(myAchievementBean.getMoneyM())) {
                        Toast.makeText(getActivity(), "输入M币不能大于剩余M币数量", Toast.LENGTH_SHORT).show();
                    } else if (!Judge.getBoolean_isNull(etM.getText().toString()) && Double.valueOf(etM.getText().toString()) > (Double.valueOf(etG.getText().toString()) * myAchievementBean.getMToGPersent())) {
                        Toast.makeText(getActivity(), "输入M币不能大于剩余G币数量的百分之" + (Double.valueOf(myAchievementBean.getMToGPersent()) * 100), Toast.LENGTH_SHORT).show();
                    } else {

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss");
                        String mm = "";
                        double d = Double.valueOf(etG.getText().toString()) * myAchievementBean.getMToGPersent();
                        double m = Double.valueOf(etM.getText().toString());
                        if (d > m) {
                            mm = m + "";
                        } else {
                            mm = d + "";
                        }
                        HttpUtil.savepresent(loginBean.getUserId(),
                                mm,
                                etG.getText().toString(),
                                simpleDateFormat.format(new Date()),
                                new ProgressBarStringCallback(progressBar) {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        try {
                                            org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());

                                            int code = jsonObject.getInt("code");
                                            if (code != 0) {
                                                Toast.makeText(getActivity(), "提现失败", Toast.LENGTH_SHORT).show();
                                                IntentUtil.Intent_WithdrawErrActivity(getActivity());
                                            } else {
                                                Toast.makeText(getActivity(), "提现成功", Toast.LENGTH_SHORT).show();
                                                EventBus.getDefault().post(new AddAchievementEventbus());
                                                IntentUtil.Intent_WithdrawSuccessActivity(getActivity());
                                                finish();
                                            }

                                        } catch (JSONException e) {
                                            Toast.makeText(getActivity(), "提现失败", Toast.LENGTH_SHORT).show();
                                            IntentUtil.Intent_WithdrawErrActivity(getActivity());
                                        }

                                    }

                                    @Override
                                    public void onError(Response<String> response) {
                                        super.onError(response);
                                        Toast.makeText(getActivity(), "提现失败", Toast.LENGTH_SHORT).show();
                                        IntentUtil.Intent_WithdrawErrActivity(getActivity());
                                    }
                                });


                    }
                } catch (Exception e) {
                    IntentUtil.Intent_WithdrawErrActivity(getActivity());
                }
                break;

            case R.id.iv_righticon:
                IntentUtil.Intent_WithdrawalsrecordActivity(getActivity());
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag("savepresent");
    }
}
