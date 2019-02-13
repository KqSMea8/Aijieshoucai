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
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.lzy.okgo.model.Response;

import org.json.JSONException;

/**
 * 修改密码
 */
public class EditPasswordActivity extends BaseActivity implements View.OnClickListener {
    LoginBean loginBean;
    ProgressBar progressBar;
    AppCompatEditText etOldpassword, etNewPassword, etCommitNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_editpassword));
        progressBar = findViewById(R.id.progressbar);
        String message = getSharedFileUtils().getString(MyConstant.loginuser);
        loginBean = JSONObject.parseObject(message, LoginBean.class);

        etOldpassword = findViewById(R.id.et_oldpassword);
        etNewPassword = findViewById(R.id.et_newpassword);
        etCommitNewPassword = findViewById(R.id.et_commitnewpassword);

        findViewById(R.id.tv_commit).setOnClickListener(this);
    }

    @Override
    public AppCompatActivity getActivity() {
        return EditPasswordActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_commit:
                if (Judge.getBoolean_isNull(etOldpassword.getText().toString())) {
                    Toast.makeText(getActivity(), "请输入旧密码", Toast.LENGTH_SHORT).show();
                } else if (Judge.getBoolean_isNull(etNewPassword.getText().toString())) {
                    Toast.makeText(getActivity(), "请输入新密码", Toast.LENGTH_SHORT).show();
                } else if (Judge.getBoolean_isNull(etCommitNewPassword.getText().toString())) {
                    Toast.makeText(getActivity(), "请再次输入新密码", Toast.LENGTH_SHORT).show();
                } else if (!etNewPassword.getText().toString().equals(etCommitNewPassword.getText().toString())) {
                    Toast.makeText(getActivity(), "两次密码不一致", Toast.LENGTH_SHORT).show();
                } else {
HttpUtil.editPassword(loginBean.getUserId(),
        etNewPassword.getText().toString(),
        new ProgressBarStringCallback(progressBar) {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    org.json.JSONObject jsonObject=new org.json.JSONObject(response.body());

                    int code=jsonObject.getInt("code");
                    if (code==0)
                    {
                        loginBean.setPassword(etNewPassword.getText().toString());
                        getSharedFileUtils().putString(MyConstant.loginuser,JSONObject.toJSONString(loginBean));
                        Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
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

                break;
        }
    }
}
