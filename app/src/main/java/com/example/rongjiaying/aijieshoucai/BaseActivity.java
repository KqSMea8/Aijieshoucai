package com.example.rongjiaying.aijieshoucai;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.rongjiaying.aijieshoucai.util.SharepercentUtils;

import java.util.List;

import jiguang.chat.application.JGApplication;

public abstract class BaseActivity extends AppCompatActivity {
    public abstract AppCompatActivity getActivity();

    protected SharepercentUtils sharepercentUtils;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JGApplication app = (JGApplication) getApplication();//获取应用程序全局的实例引用
        app.activities.remove(this); //把当前Activity从集合中移除
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 设置横屏代码：setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
         *
         * 设置竖屏代码：setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
         */
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        JGApplication app = (JGApplication) getApplication();//获取应用程序全局的实例引用
        app.activities.add(this);    //把当前Activity放入集合中
    }

    /**
     * 获取文件操作工具
     *
     * @return body
     */
    public SharepercentUtils getSharedFileUtils() {
        if (sharepercentUtils == null) {
            sharepercentUtils = new SharepercentUtils(BaseActivity.this);
        }
        return sharepercentUtils;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("BaseActivity  onActivityResult");
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getFragments().size(); i++) {
            Fragment fragment = fragmentManager.getFragments().get(i);
            if (fragment == null) {
            } else {
                handleResult(fragment, requestCode, resultCode, data);
            }
        }
    }

    private void handleResult(Fragment fragment, int requestCode, int resultCode, Intent data) {
        fragment.onActivityResult(requestCode, resultCode, data);//调用每个Fragment的onActivityResult
        List<Fragment> childFragment = fragment.getChildFragmentManager().getFragments(); //找到第二层Fragment
        if (childFragment != null)
            for (Fragment f : childFragment)
                if (f != null) {
                    handleResult(f, requestCode, resultCode, data);
                }
        if (childFragment == null) {

        }
    }


}
