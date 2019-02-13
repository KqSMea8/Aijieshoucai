package com.example.rongjiaying.aijieshoucai.home.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.home.fragment.BorrowFragment;
import com.example.rongjiaying.aijieshoucai.home.fragment.HomePagerFragment;
import com.example.rongjiaying.aijieshoucai.home.fragment.MessageFragment;
import com.example.rongjiaying.aijieshoucai.home.fragment.MyFragment;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.IntentUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.update.DownloadFileListener;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.update.javabean.AppBean;

import java.util.ArrayList;
import java.util.List;

import jiguang.chat.activity.fragment.ConversationListFragment;
import jiguang.chat.application.JGApplication;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    List<Fragment> fragments;
    AppCompatImageView ivHomepager, ivBorrow, ivMessage, ivMy;
    AppCompatTextView tvHomepager, tvBorrow, tvMessage, tvMy;
    ViewPager viewPager;


    /**
     * 第一次点击后退键时间戳
     */
    private long mExitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            // 只存在一个Fragment的时候就提醒将要退出
            if (SystemClock.uptimeMillis() - mExitTime > 2000) {
                // ToastUtlis.ToastShow(this, "再按一次退出程序");
                Toast.makeText(getActivity(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = SystemClock.uptimeMillis();
            } else {
                getActivity().finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PgyCrashManager.unregister();
    }
    LoginBean loginBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String message = getSharedFileUtils().getString(MyConstant.loginuser);
        loginBean = JSONObject.parseObject(message, LoginBean.class);

        viewPager = findViewById(R.id.viewpager);

        fragments = new ArrayList<>();
        fragments.add(HomePagerFragment.newInstance());
        fragments.add(BorrowFragment.newInstance());
       // fragments.add(MessageFragment.newInstance());
        fragments.add(new ConversationListFragment());
        fragments.add(MyFragment.newInstance());

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));


        findViewById(R.id.fab).setOnClickListener(this);
        findViewById(R.id.ll_homepager).setOnClickListener(this);
        findViewById(R.id.ll_borrow).setOnClickListener(this);
        findViewById(R.id.ll_message).setOnClickListener(this);
        findViewById(R.id.ll_my).setOnClickListener(this);

        ivHomepager = findViewById(R.id.iv_homepager);
        ivBorrow = findViewById(R.id.iv_borrow);
        ivMessage = findViewById(R.id.iv_message);
        ivMy = findViewById(R.id.iv_my);

        tvHomepager = findViewById(R.id.tv_homepager);
        tvBorrow = findViewById(R.id.tv_borrow);
        tvMessage = findViewById(R.id.tv_message);
        tvMy = findViewById(R.id.tv_my);


        tvHomepager.setTextColor(getResources().getColor(R.color.color_e92c2a));
        ivHomepager.setImageResource(R.drawable.icon_homepager_select);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        tvHomepager.setTextColor(getResources().getColor(R.color.color_e92c2a));
                        ivHomepager.setImageResource(R.drawable.icon_homepager_select);

                        tvBorrow.setTextColor(getResources().getColor(R.color.color_bababa));
                        ivBorrow.setImageResource(R.drawable.icon_borrow);

                        tvMessage.setTextColor(getResources().getColor(R.color.color_bababa));
                        ivMessage.setImageResource(R.drawable.icon_information);

                        tvMy.setTextColor(getResources().getColor(R.color.color_bababa));
                        ivMy.setImageResource(R.drawable.icon_my);
                        break;

                    case 1:
                        tvHomepager.setTextColor(getResources().getColor(R.color.color_bababa));
                        ivHomepager.setImageResource(R.drawable.icon_homepager);

                        tvBorrow.setTextColor(getResources().getColor(R.color.color_e92c2a));
                        ivBorrow.setImageResource(R.drawable.icon_borrow_select);

                        tvMessage.setTextColor(getResources().getColor(R.color.color_bababa));
                        ivMessage.setImageResource(R.drawable.icon_information);

                        tvMy.setTextColor(getResources().getColor(R.color.color_bababa));
                        ivMy.setImageResource(R.drawable.icon_my);
                        break;

                    case 2:
                        tvHomepager.setTextColor(getResources().getColor(R.color.color_bababa));
                        ivHomepager.setImageResource(R.drawable.icon_homepager);

                        tvBorrow.setTextColor(getResources().getColor(R.color.color_bababa));
                        ivBorrow.setImageResource(R.drawable.icon_borrow);

                        tvMessage.setTextColor(getResources().getColor(R.color.color_e92c2a));
                        ivMessage.setImageResource(R.drawable.icon_information_on);

                        tvMy.setTextColor(getResources().getColor(R.color.color_bababa));
                        ivMy.setImageResource(R.drawable.icon_my);
                        break;

                    case 3:
                        tvHomepager.setTextColor(getResources().getColor(R.color.color_bababa));
                        ivHomepager.setImageResource(R.drawable.icon_homepager);

                        tvBorrow.setTextColor(getResources().getColor(R.color.color_bababa));
                        ivBorrow.setImageResource(R.drawable.icon_borrow);

                        tvMessage.setTextColor(getResources().getColor(R.color.color_bababa));
                        ivMessage.setImageResource(R.drawable.icon_information);

                        tvMy.setTextColor(getResources().getColor(R.color.color_e92c2a));
                        ivMy.setImageResource(R.drawable.icon_my_select);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        PgyCrashManager.register(this);
        // initWritePermission();
        //本地更新
        initLocalUpdate();
    }

    /**
     *
     */
    private void initLocalUpdate() {
        HttpUtil.getAppData(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

            }
        });
    }

    /**
     * 蒲公英
     */
    private void initWritePermission() {
        Acp.getInstance(getActivity()).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                                , Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        /*以下为自定义提示语、按钮文字
                        .setDeniedMessage()
                        .setDeniedCloseBtn()
                        .setDeniedSettingBtn()
                        .setRationalMessage()
                        .setRationalBtn()*/
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {

                        /** 新版本 **/
                        new PgyUpdateManager.Builder()
                                .setForced(false)                //设置是否强制提示更新,非自定义回调更新接口此方法有用
                                .setUserCanRetry(false)         //失败后是否提示重新下载，非自定义下载 apk 回调此方法有用
                                .setDeleteHistroyApk(false)     // 检查更新前是否删除本地历史 Apk， 默认为true
                                .setUpdateManagerListener(new UpdateManagerListener() {
                                    @Override
                                    public void onNoUpdateAvailable() {
                                        //没有更新是回调此方法
                                        Log.d("pgyer", "there is no new version");
                                    }

                                    @Override
                                    public void onUpdateAvailable(final AppBean appBean) {
                                        //有更新回调此方法
                                        Log.d("pgyer", "there is new version can update"
                                                + "new versionCode is " + appBean.getVersionCode());
                                        //调用以下方法，DownloadFileListener 才有效；
                                        //如果完全使用自己的下载方法，不需要设置DownloadFileListener
                                        //    PgyUpdateManager.downLoadApk(appBean.getDownloadURL());

                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                        builder.setTitle("是否更新");
                                        builder.setMessage("更新内容 " + appBean.getReleaseNote());
                                        builder.setNegativeButton("取消", null);
                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                PgyUpdateManager.downLoadApk(appBean.getDownloadURL());
                                            }
                                        });
                                        builder.show();
                                    }

                                    @Override
                                    public void checkUpdateFailed(Exception e) {
                                        //更新检测失败回调
                                        //更新拒绝（应用被下架，过期，不在安装有效期，下载次数用尽）以及无网络情况会调用此接口
                                        Log.e("pgyer", "check update failed ", e);
                                    }
                                })
                                //注意 ：
                                //下载方法调用 PgyUpdateManager.downLoadApk(appBean.getDownloadURL()); 此回调才有效
                                //此方法是方便用户自己实现下载进度和状态的 UI 提供的回调
                                //想要使用蒲公英的默认下载进度的UI则不设置此方法

                                .setDownloadFileListener(new DownloadFileListener() {
                                    @Override
                                    public void downloadFailed() {
                                        //下载失败
                                        Log.e("pgyer", "download apk failed");
                                    }

                                    @Override
                                    public void downloadSuccessful(Uri uri) {
                                        Log.e("pgyer", "download apk failed");
                                        // 使用蒲公英提供的安装方法提示用户 安装apk
                                        PgyUpdateManager.installApk(uri);
                                    }

                                    @Override
                                    public void onProgressUpdate(Integer... integers) {
                                        Log.e("pgyer", "update download apk progress" + integers);
                                    }
                                })
                                .register();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        Toast.makeText(getActivity(), "请开启相关权限", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public AppCompatActivity getActivity() {
        return HomeActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (loginBean!=null&&!Judge.getBoolean_isNull(loginBean.getUserId()))
                {
                    IntentUtil.Intent_EntryInformationActivity(getActivity());
                }else {
                    Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
                    JGApplication app = (JGApplication) getApplication();
                    List<AppCompatActivity> activities = app.activities;
                    for (Activity act : activities) {
                        act.finish();//显式结束
                    }
                    getSharedFileUtils().putString(MyConstant.loginuser,"");
                    IntentUtil.Intent_LoginActivity(getActivity(),false);
                }

                break;

            case R.id.ll_homepager:
                viewPager.setCurrentItem(0);
                tvHomepager.setTextColor(getResources().getColor(R.color.color_e92c2a));
                ivHomepager.setImageResource(R.drawable.icon_homepager_select);

                tvBorrow.setTextColor(getResources().getColor(R.color.color_bababa));
                ivBorrow.setImageResource(R.drawable.icon_borrow);

                tvMessage.setTextColor(getResources().getColor(R.color.color_bababa));
                ivMessage.setImageResource(R.drawable.icon_information);

                tvMy.setTextColor(getResources().getColor(R.color.color_bababa));
                ivMy.setImageResource(R.drawable.icon_my);
                break;
            case R.id.ll_borrow:

                if (loginBean!=null&&!Judge.getBoolean_isNull(loginBean.getUserId()))
                {
                    viewPager.setCurrentItem(1);
                    tvHomepager.setTextColor(getResources().getColor(R.color.color_bababa));
                    ivHomepager.setImageResource(R.drawable.icon_homepager);

                    tvBorrow.setTextColor(getResources().getColor(R.color.color_e92c2a));
                    ivBorrow.setImageResource(R.drawable.icon_borrow_select);

                    tvMessage.setTextColor(getResources().getColor(R.color.color_bababa));
                    ivMessage.setImageResource(R.drawable.icon_information);

                    tvMy.setTextColor(getResources().getColor(R.color.color_bababa));
                    ivMy.setImageResource(R.drawable.icon_my);
                }else {
                    Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
                    JGApplication app = (JGApplication) getApplication();
                    List<AppCompatActivity> activities = app.activities;
                    for (Activity act : activities) {
                        act.finish();//显式结束
                    }
                    getSharedFileUtils().putString(MyConstant.loginuser,"");
                    IntentUtil.Intent_LoginActivity(getActivity(),false);
                }


                break;
            case R.id.ll_message:


                if (loginBean!=null&&!Judge.getBoolean_isNull(loginBean.getUserId()))
                {
                    viewPager.setCurrentItem(2);
                    tvHomepager.setTextColor(getResources().getColor(R.color.color_bababa));
                    ivHomepager.setImageResource(R.drawable.icon_homepager);

                    tvBorrow.setTextColor(getResources().getColor(R.color.color_bababa));
                    ivBorrow.setImageResource(R.drawable.icon_borrow);

                    tvMessage.setTextColor(getResources().getColor(R.color.color_e92c2a));
                    ivMessage.setImageResource(R.drawable.icon_information_on);

                    tvMy.setTextColor(getResources().getColor(R.color.color_bababa));
                    ivMy.setImageResource(R.drawable.icon_my);
                }else {
                    Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
                    JGApplication app = (JGApplication) getApplication();
                    List<AppCompatActivity> activities = app.activities;
                    for (Activity act : activities) {
                        act.finish();//显式结束
                    }
                    getSharedFileUtils().putString(MyConstant.loginuser,"");
                    IntentUtil.Intent_LoginActivity(getActivity(),false);
                }


                break;
            case R.id.ll_my:




                if (loginBean!=null&&!Judge.getBoolean_isNull(loginBean.getUserId()))
                {
                    viewPager.setCurrentItem(3);
                    tvHomepager.setTextColor(getResources().getColor(R.color.color_bababa));
                    ivHomepager.setImageResource(R.drawable.icon_homepager);

                    tvBorrow.setTextColor(getResources().getColor(R.color.color_bababa));
                    ivBorrow.setImageResource(R.drawable.icon_borrow);

                    tvMessage.setTextColor(getResources().getColor(R.color.color_bababa));
                    ivMessage.setImageResource(R.drawable.icon_information);

                    tvMy.setTextColor(getResources().getColor(R.color.color_e92c2a));
                    ivMy.setImageResource(R.drawable.icon_my_select);
                }else {
                    Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
                    JGApplication app = (JGApplication) getApplication();
                    List<AppCompatActivity> activities = app.activities;
                    for (Activity act : activities) {
                        act.finish();//显式结束
                    }
                    getSharedFileUtils().putString(MyConstant.loginuser,"");
                    IntentUtil.Intent_LoginActivity(getActivity(),false);
                }
                break;
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }


}
