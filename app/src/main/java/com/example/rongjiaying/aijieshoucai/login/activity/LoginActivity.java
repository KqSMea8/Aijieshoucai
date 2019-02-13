package com.example.rongjiaying.aijieshoucai.login.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.ScreenUtils;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.IntentUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import jiguang.chat.application.JGApplication;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    AppCompatImageView ivLoginBg;

    AppCompatEditText etPhone, etPassword;

    ProgressBar progressBar;
    boolean isloginOut = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylogin);
        ivLoginBg = findViewById(R.id.iv_loginbg);
        int width = ScreenUtils.getScreenWidth();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, width * 200 / 375);
        ivLoginBg.setLayoutParams(params);
        findViewById(R.id.tv_regist).setOnClickListener(this);//注册
        findViewById(R.id.tv_forgotpassword).setOnClickListener(this);//忘记密码
        findViewById(R.id.tv_login).setOnClickListener(this);//登录

        progressBar = findViewById(R.id.progressbar);

        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        isloginOut = getIntent().getBooleanExtra("isloginout", false);
        if (isloginOut) {
            findViewById(R.id.tv_cancle).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.tv_cancle).setVisibility(View.GONE);
        }
        findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JGApplication app = (JGApplication) getApplication();
                List<AppCompatActivity> activities = app.activities;
                for (Activity act : activities) {
                    act.finish();//显式结束
                }
                getSharedFileUtils().putString(MyConstant.loginuser, "");
                IntentUtil.Intent_HomeActivity(getActivity());
            }
        });
    }

    @Override
    public AppCompatActivity getActivity() {
        return LoginActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_regist:
                IntentUtil.Intent_RegistActivity(getActivity());
                break;
            case R.id.tv_forgotpassword:
                IntentUtil.Intent_ForGotPasswordActivity(getActivity());
                break;
            case R.id.tv_login:
                //    IntentUtil.Intent_HomeActivity(getActivity());

                if (Judge.getBoolean_isNull(etPhone.getText().toString().trim())) {
                    Toast.makeText(getActivity(), getString(R.string.string_hint_phone), Toast.LENGTH_SHORT).show();
                } else if (Judge.getBoolean_isNull(etPassword.getText().toString().trim())) {
                    Toast.makeText(getActivity(), getString(R.string.string_hint_password), Toast.LENGTH_SHORT).show();
                } else {
                    HttpUtil.login(etPhone.getText().toString(),
                            etPassword.getText().toString(),
                            new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.i("asdf", "login" + response.body());
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body());

                                        int code = jsonObject.getInt("code");
                                        String msg = jsonObject.getString("msg");
                                        if (code != 0) {
                                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                                        } else {
                                            try {
                                                final LoginBean loginBean = com.alibaba.fastjson.JSONObject.parseObject(
                                                        msg, LoginBean.class
                                                );
                                                getSharedFileUtils().putString(MyConstant.loginuser, com.alibaba.fastjson.JSONObject.toJSONString(loginBean));

                                                Toast.makeText(getActivity(), getString(R.string.string_loginsuccess), Toast.LENGTH_SHORT).show();
                                                JMessageClient.login(loginBean.getUserId() + loginBean.getInviteCode(), "123456", new BasicCallback() {
                                                    @Override
                                                    public void gotResult(int i, String s) {
                                                        Log.i("asdf", "im " + i + "  " + s);
                                                        if (i == 801003)//未注册
                                                        {
                                                            JMessageClient.register(loginBean.getUserId() + loginBean.getInviteCode(), "123456", new BasicCallback() {
                                                                @Override
                                                                public void gotResult(int i, String s) {
                                                                    JMessageClient.login(loginBean.getUserId() + loginBean.getInviteCode(), "123456", new BasicCallback() {
                                                                        @Override
                                                                        public void gotResult(int i, String s) {
                                                                            IntentUtil.Intent_HomeActivity(getActivity());
                                                                            finish();
                                                                        }
                                                                    });

                                                                }
                                                            });

                                                        } else {
                                                            IntentUtil.Intent_HomeActivity(getActivity());
                                                            finish();
                                                        }
                                                    }
                                                });
                                            } catch (Exception e) {
                                                Toast.makeText(getActivity(), getString(R.string.string_loginerr), Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getActivity(), getString(R.string.string_loginerr), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                    Toast.makeText(getActivity(), getString(R.string.string_loginerr), Toast.LENGTH_SHORT).show();
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
                            });
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag("login");
    }
}
