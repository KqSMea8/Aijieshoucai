package com.example.rongjiaying.aijieshoucai.login.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.ScreenUtils;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.bean.SendCodeBean;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.login.dialogfragment.SelectGroupDialogFragment;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.IntentUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

public class RegistActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 倒计时控件
     */
    private CountDownTimer timer;
    AppCompatTextView tvSendCode;
    AppCompatEditText etPhone, etCode, etPassword, etCommitPassword, etInvitationcode;
    ProgressBar progressBar;
    AppCompatTextView tvCbText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_regist));
        AppCompatImageView ivLoginBg = findViewById(R.id.iv_loginbg);
        int width = ScreenUtils.getScreenWidth();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, width * 200 / 375);
        ivLoginBg.setLayoutParams(params);


        //发送验证码
        tvSendCode = findViewById(R.id.tv_sendcode);
        etPhone = findViewById(R.id.et_phone);
        etCode = findViewById(R.id.et_code);
        tvSendCode.setOnClickListener(this);
        progressBar = findViewById(R.id.progressbar);
        //发送验证码
        findViewById(R.id.tv_regist).setOnClickListener(this);
        //etPassword,etCommitPassword,etInvitationcode
        etPassword = findViewById(R.id.et_password);
        etCommitPassword = findViewById(R.id.et_commitpassword);
        etInvitationcode = findViewById(R.id.et_invitationcode);

        tvCbText = findViewById(R.id.tv_cbtext);
        String string = getResources().getString(R.string.string_registcbmessage);
        String string1 = getResources().getString(R.string.string_registcbmessage1);
        String string2 = getResources().getString(R.string.string_registcbmessage2);


        SpannableString spannableString=new SpannableString(string);
        ForegroundColorSpan blueSpan1 = new ForegroundColorSpan(Color.BLUE);
        ForegroundColorSpan blueSpan2 = new ForegroundColorSpan(Color.BLUE);
        spannableString.setSpan(blueSpan1, string.indexOf(string1), string.indexOf(string1) + string1.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(blueSpan2, string.indexOf(string2), string.indexOf(string2) + string2.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);


        //设置部分文字点击事件
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                IntentUtil.Intent_RegistServiceActivity(getActivity(),1);
            }
        };
        spannableString.setSpan(clickableSpan1, string.indexOf(string1), string.indexOf(string1) + string1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置部分文字点击事件
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                IntentUtil.Intent_RegistServiceActivity(getActivity(),2);
            }
        };
        spannableString.setSpan(clickableSpan2, string.indexOf(string2), string.indexOf(string2) + string2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        tvCbText.setText(spannableString);
        tvCbText.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public AppCompatActivity getActivity() {
        return RegistActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            //发送验证码
            case R.id.tv_sendcode:

                if (Judge.getBoolean_isNull(etPhone.getText().toString().trim())) {
                    Toast.makeText(getActivity(), getString(R.string.string_hint_phone), Toast.LENGTH_SHORT).show();
                } else {
                    HttpUtil.sendCode(etPhone.getText().toString(), new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.i("asdf", "" + response.body());
                            try {
                                JSONObject jsonObject = new JSONObject(response.body());
                                int code = jsonObject.getInt("code");
                                if (code != 0) {
                                    Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                                } else {
                                    SendCodeBean sendCodeBean = new SendCodeBean();
                                    sendCodeBean.setCode(jsonObject.getString("msg"));
                                    sendCodeBean.setPhone(etPhone.getText().toString());
                                    sendCodeBean.setTime(new Date().getTime());
                                    getSharedFileUtils().putString(MyConstant.sendcode, com.alibaba.fastjson.JSONObject.toJSONString(sendCodeBean));
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
            //注册
            case R.id.tv_regist:
                //判断验证码
                String sendcode = getSharedFileUtils().getString(MyConstant.sendcode);
                if (Judge.getBoolean_isNull(etPhone.getText().toString().trim())) {
                    Toast.makeText(getActivity(), getString(R.string.string_hint_phone), Toast.LENGTH_SHORT).show();
                } else if (Judge.getBoolean_isNull(etCode.getText().toString().trim())) {
                    Toast.makeText(getActivity(), getString(R.string.string_hint_code), Toast.LENGTH_SHORT).show();
                } else {
                    SendCodeBean sendCodeBean = com.alibaba.fastjson.JSONObject.parseObject(sendcode, SendCodeBean.class);
                    String code = sendCodeBean.getCode();
                    String phone = sendCodeBean.getPhone();
                    long time = sendCodeBean.getTime();
                    if (!phone.equals(etPhone.getText().toString())) {
                        Toast.makeText(getActivity(), "与验证的手机不匹配", Toast.LENGTH_SHORT).show();
                    } else if (!code.equals(etCode.getText().toString())) {
                        Toast.makeText(getActivity(), "验证码不一致", Toast.LENGTH_SHORT).show();
                    } else if (new Date().getTime() - time > 600 * 1000) {
                        Toast.makeText(getActivity(), "超过600秒 请重新发送", Toast.LENGTH_SHORT).show();
                    } else if (Judge.getBoolean_isNull(etPassword.getText().toString().trim())) {
                        Toast.makeText(getActivity(), getString(R.string.string_hint_password), Toast.LENGTH_SHORT).show();
                    } else if (Judge.getBoolean_isNull(etCommitPassword.getText().toString().trim())) {
                        Toast.makeText(getActivity(), getString(R.string.string_hint_commitpassword), Toast.LENGTH_SHORT).show();
                    } else if (!etPassword.getText().toString().equals(etCommitPassword.getText().toString())) {
                        Toast.makeText(getActivity(), getString(R.string.string_passworderr), Toast.LENGTH_SHORT).show();
                    } else {
                        groupid = "";
                        if (!Judge.getBoolean_isNull(etInvitationcode.getText().toString())) {
                            HttpUtil.registUser(etPhone.getText().toString(),
                                    etPassword.getText().toString(),
                                    etCode.getText().toString(),
                                    etInvitationcode.getText().toString(),
                                    new StringCallback() {
                                        @Override
                                        public void onSuccess(Response<String> response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response.body());
                                                Log.i("asdf", "" + response.body());
                                                int code = jsonObject.getInt("code");
                                                final String msg = jsonObject.getString("msg");
                                                if (code != 0) {
                                                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                                                } else {

                                                    final LoginBean loginBean = com.alibaba.fastjson.JSONObject.parseObject(msg, LoginBean.class);
                                                    if (loginBean != null && !Judge.getBoolean_isNull(loginBean.getUserId())) {
                                                        //邀请群
                                                        JMessageClient.register(loginBean.getUserId() + loginBean.getInviteCode(), "123456", new BasicCallback() {
                                                            @Override
                                                            public void gotResult(int i, String s) {
                                                                if (i == 0) {
                                                                    HttpUtil.comegroup(loginBean.getUserId() + loginBean.getInviteCode(),
                                                                            loginBean.getDirectUserId(),
                                                                            new StringCallback() {
                                                                                @Override
                                                                                public void onSuccess(Response<String> response) {
                                                                                    try {
                                                                                        JSONObject j = new JSONObject(response.body());

                                                                                        int c = j.getInt("code");
                                                                                        if (c != 0) {
                                                                                            Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_SHORT).show();
                                                                                        } else {
                                                                                            Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
                                                                                            finish();
                                                                                        }

                                                                                    } catch (JSONException e) {
                                                                                        Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }

                                                                                @Override
                                                                                public void onError(Response<String> response) {
                                                                                    super.onError(response);
                                                                                    Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                } else {
                                                                    Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });


                                                    } else {
                                                        Log.i("asdf", "注册失败");
                                                        Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_SHORT).show();
                                                    }


                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Toast.makeText(getActivity(), getString(R.string.string_registerr), Toast.LENGTH_SHORT).show();
                                            }
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

                                        @Override
                                        public void onError(Response<String> response) {
                                            super.onError(response);
                                            Toast.makeText(getActivity(), getString(R.string.string_registerr), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else//选择群组
                        {
                            SelectGroupDialogFragment selectGroupDialogFragment = SelectGroupDialogFragment.newInstance();
                            selectGroupDialogFragment.show(getSupportFragmentManager(), selectGroupDialogFragment.getClass().getName());
                            selectGroupDialogFragment.setOnSelectGroupListener(new SelectGroupDialogFragment.OnSelectGroupListener() {
                                @Override
                                public void onSelectGroupItem(final long groupid) {
                                    HttpUtil.registUser(etPhone.getText().toString(),
                                            etPassword.getText().toString(),
                                            etCode.getText().toString(),
                                            etInvitationcode.getText().toString(),
                                            new StringCallback() {
                                                @Override
                                                public void onSuccess(Response<String> response) {
                                                    try {
                                                        JSONObject jsonObject = new JSONObject(response.body());
                                                        Log.i("asdf", "" + response.body());
                                                        int code = jsonObject.getInt("code");
                                                        final String msg = jsonObject.getString("msg");
                                                        if (code != 0) {
                                                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                                                        } else {

                                                            final LoginBean loginBean = com.alibaba.fastjson.JSONObject.parseObject(msg, LoginBean.class);
                                                            if (loginBean != null && !Judge.getBoolean_isNull(loginBean.getUserId())) {
                                                                //邀请群
                                                                JMessageClient.register(loginBean.getUserId() + loginBean.getInviteCode(), "123456", new BasicCallback() {
                                                                    @Override
                                                                    public void gotResult(int i, String s) {
                                                                        if (i == 0) {

                                                                            //选择群组
                                                                            HttpUtil.sankeAddGroup(loginBean.getUserId() + loginBean.getInviteCode(),
                                                                                    groupid, new StringCallback() {
                                                                                        @Override
                                                                                        public void onSuccess(Response<String> response) {
                                                                                            try {
                                                                                                JSONObject j = new JSONObject(response.body());

                                                                                                int c = j.getInt("code");
                                                                                                if (c != 0) {
                                                                                                    Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_SHORT).show();
                                                                                                } else {
                                                                                                    Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
                                                                                                    finish();
                                                                                                }

                                                                                            } catch (JSONException e) {
                                                                                                Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        }

                                                                                        @Override
                                                                                        public void onError(Response<String> response) {
                                                                                            super.onError(response);
                                                                                            Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_SHORT).show();
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


                                                                        } else {
                                                                            Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });


                                                            } else {
                                                                Log.i("asdf", "注册失败");
                                                                Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_SHORT).show();
                                                            }


                                                        }

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                        Toast.makeText(getActivity(), getString(R.string.string_registerr), Toast.LENGTH_SHORT).show();
                                                    }
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

                                                @Override
                                                public void onError(Response<String> response) {
                                                    super.onError(response);
                                                    Toast.makeText(getActivity(), getString(R.string.string_registerr), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                        }

                    }

                }
                break;
        }
    }

    String groupid = "";


    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag("sendCode");
        OkGo.getInstance().cancelTag("registUser");
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
