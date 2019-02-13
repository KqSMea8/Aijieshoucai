package com.example.rongjiaying.aijieshoucai.flash.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSONObject;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.callback.ProgressBarStringCallback;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.flash.bean.FlashBean;
import com.example.rongjiaying.aijieshoucai.flash.fragment.FlashFragment;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.IntentUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * 欢迎页面
 */
public class FlashActivity extends BaseActivity {
    ProgressBar progressBar;

    //   LoginBean loginBean;//登录信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        progressBar = findViewById(R.id.progressbar);
        boolean message = getSharedFileUtils().getBoolean(MyConstant.isFirst);
        String messages = getSharedFileUtils().getString(MyConstant.loginuser);


        if (!message) {
            getSharedFileUtils().putBoolean(MyConstant.isFirst, true);
            initFlashData();

        } else {
            //IntentUtil.Intent_HomeActivity(getActivity());
            if (Judge.getBoolean_isNull(messages))
            {
                IntentUtil.Intent_HomeActivity(getActivity());
                finish();
            }else {
                final LoginBean loginBean = JSONObject.parseObject(messages, LoginBean.class);
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
            }



            //finish();
        }

    }

    private void initFlashData() {
        HttpUtil.welcome(new ProgressBarStringCallback(progressBar) {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());
                    int code = jsonObject.getInt("code");
                    if (code != 0) {
                        IntentUtil.Intent_LoginActivity(getActivity());
                    } else {
                        Log.i("asdf", "" + jsonObject.getString("msg"));
                        ArrayList<FlashBean> flashBeans = (ArrayList<FlashBean>) JSONObject.parseArray(jsonObject.getString("msg"), FlashBean.class);
                        if (flashBeans != null && flashBeans.size() > 0) {
                            ViewPager viewPager = findViewById(R.id.viewpager);
                            viewPager.setAdapter(new FlashViewPager(getSupportFragmentManager(), flashBeans));
                        } else {
                            IntentUtil.Intent_LoginActivity(getActivity());
                        }
                    }
                } catch (Exception e) {
                    IntentUtil.Intent_LoginActivity(getActivity());
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                IntentUtil.Intent_LoginActivity(getActivity());
            }
        });
    }

    @Override
    public AppCompatActivity getActivity() {
        return FlashActivity.this;
    }

    private class FlashViewPager extends FragmentPagerAdapter {
        ArrayList<FlashBean> flashBeans;

        public FlashViewPager(FragmentManager fm, ArrayList<FlashBean> flashBeans) {
            super(fm);
            this.flashBeans = flashBeans;
        }

        @Override
        public Fragment getItem(int i) {
            return FlashFragment.newInstance(flashBeans.get(i), flashBeans);
        }

        @Override
        public int getCount() {
            return flashBeans.size();
        }
    }
}
