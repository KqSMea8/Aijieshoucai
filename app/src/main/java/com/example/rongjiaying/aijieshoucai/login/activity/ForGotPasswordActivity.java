package com.example.rongjiaying.aijieshoucai.login.activity;

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
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.json.JSONException;

import java.util.Date;

/**
 * 忘记密码
 */
public class ForGotPasswordActivity extends BaseActivity implements View.OnClickListener {
    AppCompatEditText etPhone, etCode;
    AppCompatTextView tvSendCode;
    ProgressBar progressBar;
    AppCompatEditText etNewPassword, etCommitNewPassword;
    /**
     * 倒计时控件
     */
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_got_password);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_forgotpasswordone));
        findViewById(R.id.tv_sendcode).setOnClickListener(this);
        findViewById(R.id.tv_commit).setOnClickListener(this);
        etPhone = findViewById(R.id.et_phone);
        etCode = findViewById(R.id.et_code);
        tvSendCode = findViewById(R.id.tv_sendcode);
        progressBar = findViewById(R.id.progressbar);
        etNewPassword = findViewById(R.id.et_newpassword);
        etCommitNewPassword = findViewById(R.id.et_commitnewpassword);
    }

    @Override
    public AppCompatActivity getActivity() {
        return ForGotPasswordActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
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
                    } else if (new Date().getTime() - time > 600 * 1000) {
                        Toast.makeText(getActivity(), "超过60秒 请重新发送", Toast.LENGTH_SHORT).show();
                    }
                    if (Judge.getBoolean_isNull(etNewPassword.getText().toString())) {
                        Toast.makeText(getActivity(), "请填写新的密码", Toast.LENGTH_SHORT).show();
                    } else if (Judge.getBoolean_isNull(etCommitNewPassword.getText().toString())) {
                        Toast.makeText(getActivity(), "请再次填写新的密码", Toast.LENGTH_SHORT).show();
                    } else if (!etNewPassword.getText().toString().equals(etCommitNewPassword.getText().toString())) {
                        Toast.makeText(getActivity(), "两次密码不一致", Toast.LENGTH_SHORT).show();
                    } else {
                        HttpUtil.forgotPassword(etPhone.getText().toString(),
                                etNewPassword.getText().toString(),
                                new ProgressBarStringCallback(progressBar) {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        try {
                                            org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());

                                            int code = jsonObject.getInt("code");
                                            if (code == 0) {
                                                Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                                            }

                                        } catch (JSONException e) {
                                            Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onError(Response<String> response) {
                                        super.onError(response);
                                        Toast.makeText(getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag("sendCode");
        OkGo.getInstance().cancelTag("forgotPassword");
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        getSharedFileUtils().putString(MyConstant.sendcode, "");
    }
}
