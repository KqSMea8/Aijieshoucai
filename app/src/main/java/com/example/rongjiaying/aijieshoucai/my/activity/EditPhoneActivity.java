package com.example.rongjiaying.aijieshoucai.my.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.bean.SendCodeBean;
import com.example.rongjiaying.aijieshoucai.callback.ProgressBarStringCallback;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.my.eventbus.UpdateAccountManagementEventbus;
import com.example.rongjiaying.aijieshoucai.my.eventbus.UpdateUserMessageEventbus;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.util.Date;

public class EditPhoneActivity extends BaseActivity implements View.OnClickListener {
    AppCompatEditText etPhone, etCode;
    AppCompatTextView tvSendCode;
    ProgressBar progressBar;
    /**
     * 倒计时控件
     */
    private CountDownTimer timer;
    LoginBean loginBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phone);
        String message = getSharedFileUtils().getString(MyConstant.loginuser);
        loginBean = JSONObject.parseObject(message, LoginBean.class);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_editphone));
        findViewById(R.id.tv_commit).setOnClickListener(this);//确认
        etPhone = findViewById(R.id.et_phone);
        etCode = findViewById(R.id.et_code);
        tvSendCode = findViewById(R.id.tv_sendcode);
        tvSendCode.setOnClickListener(this);
        progressBar = findViewById(R.id.progressbar);
    }

    @Override
    public AppCompatActivity getActivity() {
        return EditPhoneActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_commit:
                //判断验证码
                String sendcode = getSharedFileUtils().getString(MyConstant.sendcode);
                if (Judge.getBoolean_isNull(etPhone.getText().toString())) {
                    Toast.makeText(getActivity(), R.string.string_hintphone, Toast.LENGTH_SHORT).show();
                } else if (Judge.getBoolean_isNull(sendcode)) {
                    Toast.makeText(getActivity(), "请发送验证码", Toast.LENGTH_SHORT).show();
                } else {
                    SendCodeBean sendCodeBean = JSONObject.parseObject(sendcode, SendCodeBean.class);
                    String code = sendCodeBean.getCode();
                    String phone = sendCodeBean.getPhone();
                    long time = sendCodeBean.getTime();
                    if (!phone.equals(etPhone.getText().toString())) {
                        Toast.makeText(getActivity(), "与验证的手机不匹配", Toast.LENGTH_SHORT).show();
                    } else if (!code.equals(etCode.getText().toString())) {
                        Toast.makeText(getActivity(), "验证码不一致", Toast.LENGTH_SHORT).show();
                    } else if (new Date().getTime() - time > 60 * 1000) {
                        Toast.makeText(getActivity(), "超过60秒 请重新发送", Toast.LENGTH_SHORT).show();
                    }else {
                        HttpUtil.editPhone(loginBean.getUserId(),
                                etPhone.getText().toString(),
                                new ProgressBarStringCallback(progressBar) {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        try {
                                            org.json.JSONObject jsonObject=new org.json.JSONObject(response.body());
                                            int code=jsonObject.getInt("code");
                                            if (code==0)
                                            {
                                                loginBean.setPhone(etPhone.getText().toString());
                                                EventBus.getDefault().post(new UpdateUserMessageEventbus(loginBean));
                                                EventBus.getDefault().post(new UpdateAccountManagementEventbus(loginBean));
                                                getSharedFileUtils().putString(MyConstant.loginuser,JSONObject.toJSONString(loginBean));
                                                Toast.makeText(getActivity(),"提交成功",Toast.LENGTH_SHORT).show();
                                                finish();
                                            }else {
                                                Toast.makeText(getActivity(),"提交失败",Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            Toast.makeText(getActivity(),"提交失败",Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onError(Response<String> response) {
                                        super.onError(response);
                                        Toast.makeText(getActivity(),"提交失败",Toast.LENGTH_SHORT).show();
                                    }



                                });
                    }
                }
                break;
            case R.id.tv_sendcode:
                if (Judge.getBoolean_isNull(etPhone.getText().toString().trim())) {
                    Toast.makeText(getActivity(), getString(R.string.string_hint_phone), Toast.LENGTH_SHORT).show();
                } else {
                    HttpUtil.sendCode(etPhone.getText().toString(), new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            try {
                                org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());
                                int code = jsonObject.getInt("code");
                                if (code != 0) {
                                    Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.i("asdf", "" + jsonObject.getString("msg"));
                                    //    ycode=jsonObject.getString("msg");
                                    SendCodeBean sendCodeBean = new SendCodeBean();
                                    sendCodeBean.setCode(jsonObject.getString("msg"));
                                    sendCodeBean.setPhone(etPhone.getText().toString());
                                    sendCodeBean.setTime(new Date().getTime());
                                    getSharedFileUtils().putString(MyConstant.sendcode, JSONObject.toJSONString(sendCodeBean));
                                    if (timer != null) {
                                        timer.cancel();
                                        timer = null;
                                    }
                                    /** 倒计时60秒，一次1秒 */
                                    timer = new CountDownTimer(60 * 1000, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            if (getActivity() != null) {
                                                // TODO Auto-generated method stub
                                                tvSendCode.setText("还剩" + millisUntilFinished / 1000 + "秒");
                                                tvSendCode.setClickable(false);
                                            }

                                        }

                                        @Override
                                        public void onFinish() {
                                            tvSendCode.setText("重新获取验证码");
                                            tvSendCode.setClickable(true);
                                        }
                                    }.start();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), getString(R.string.string_sendcodeerr), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            Toast.makeText(getActivity(), getString(R.string.string_sendcodeerr), Toast.LENGTH_SHORT).show();
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
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag("sendCode");
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        getSharedFileUtils().putString(MyConstant.sendcode, "");
    }
}
