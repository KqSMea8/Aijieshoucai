package com.example.rongjiaying.aijieshoucai.my.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.callback.ProgressBarStringCallback;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.my.eventbus.UpdateAccountManagementEventbus;
import com.example.rongjiaying.aijieshoucai.my.eventbus.UpdateUserMessageEventbus;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

/**
 * 修改昵称
 */
public class EditNickNameActivity extends BaseActivity implements View.OnClickListener {
    AppCompatEditText etName;
    LoginBean loginBean;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nick_name);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_editnickname));
        etName = findViewById(R.id.et_name);
        progressBar = findViewById(R.id.progressbar);
        String message = getSharedFileUtils().getString(MyConstant.loginuser);
        loginBean = JSONObject.parseObject(message, LoginBean.class);
        findViewById(R.id.tv_commit).setOnClickListener(this);

    }

    @Override
    public AppCompatActivity getActivity() {
        return EditNickNameActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_commit:
                if (Judge.getBoolean_isNull(etName.getText().toString())) {
                    Toast.makeText(getActivity(), "请输入昵称", Toast.LENGTH_SHORT).show();
                } else {
                    HttpUtil.editNickname(
                            loginBean.getUserId(),
                            etName.getText().toString(),
                            new ProgressBarStringCallback(progressBar) {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    try {
                                        org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());

                                        int code = jsonObject.getInt("code");
                                        if (code == 0) {
                                            loginBean.setUsername(etName.getText().toString());
                                            EventBus.getDefault().post(new UpdateUserMessageEventbus(loginBean));
                                            EventBus.getDefault().post(new UpdateAccountManagementEventbus(loginBean));
                                            getSharedFileUtils().putString(MyConstant.loginuser,JSONObject.toJSONString(loginBean));
                                            Toast.makeText(getActivity(), "设置成功", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(getActivity(), "提交失败", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        Toast.makeText(getActivity(), "提交失败", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                    Toast.makeText(getActivity(), "提交失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
                }
                break;
        }
    }
}
