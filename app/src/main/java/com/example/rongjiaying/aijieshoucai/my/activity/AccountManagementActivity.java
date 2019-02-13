package com.example.rongjiaying.aijieshoucai.my.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.constant.InternetConstant;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.my.eventbus.UpdateAccountManagementEventbus;
import com.example.rongjiaying.aijieshoucai.my.eventbus.UpdateUserMessageEventbus;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.IntentUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;

import java.io.File;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import jiguang.chat.application.JGApplication;

/**
 * 账号管理
 */
public class AccountManagementActivity extends BaseActivity implements View.OnClickListener {
    LoginBean loginBean;
    AppCompatImageView ivMyicon;
    ProgressBar progressBar;
    AppCompatTextView tvPhone, tvNickname;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUserMessage(UpdateAccountManagementEventbus messageEvent) {
        if (messageEvent != null && messageEvent.getLoginBean() != null) {
            updateUserMessage(messageEvent.getLoginBean());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);
        EventBus.getDefault().register(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_accountmanagement));

        ivMyicon = findViewById(R.id.iv_myicon);
        tvPhone = findViewById(R.id.tv_phone);
        tvPhone = findViewById(R.id.tv_phone);
        tvNickname = findViewById(R.id.tv_nickname);
        progressBar = findViewById(R.id.progressbar);

        findViewById(R.id.ll_mybank).setOnClickListener(this);
        findViewById(R.id.ll_editnickname).setOnClickListener(this); //修改昵称
        findViewById(R.id.ll_editpassword).setOnClickListener(this);//修改密码
        String message = getSharedFileUtils().getString(MyConstant.loginuser);
        loginBean = JSONObject.parseObject(message, LoginBean.class);
        findViewById(R.id.ll_phone).setOnClickListener(this);
        findViewById(R.id.btn_loginout).setOnClickListener(this); //退出登录
        if (loginBean!=null)
        {
            updateUserMessage(loginBean);
        }

    }

    private void updateUserMessage(LoginBean loginBean) {
        if (!Judge.getBoolean_isNull(loginBean.getIcon()))
        {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.ic_launcher)                //加载成功之前占位图
                    .error(R.mipmap.ic_launcher)                    //加载错误之后的错误图
                    .override(400, 400)                                //指定图片的尺寸
                    //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
                    .fitCenter()
                    //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                    .centerCrop()
                    .circleCrop()//指定图片的缩放类型为centerCrop （圆形）
                    .skipMemoryCache(true)                            //跳过内存缓存
                    .diskCacheStrategy(DiskCacheStrategy.ALL)        //缓存所有版本的图像
                    .diskCacheStrategy(DiskCacheStrategy.NONE)        //跳过磁盘缓存
                    .diskCacheStrategy(DiskCacheStrategy.DATA)        //只缓存原来分辨率的图片
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);  //只缓存最终的图片
            String imageurl = "";
            if (new File(loginBean.getIcon()).exists()) {
                imageurl = loginBean.getIcon();
            } else {
                imageurl = InternetConstant.imageurl + loginBean.getIcon();
            }
            Glide.with(ivMyicon.getContext())
                    .load(imageurl)
                    .apply(options)
                    .into(ivMyicon);
        }

        ivMyicon.setOnClickListener(this);

        //手机

        if (!Judge.getBoolean_isNull(loginBean.getPhone())) {
            tvPhone.setText(loginBean.getPhone());
        }
        //手机

        //手机

        if (!Judge.getBoolean_isNull(loginBean.getUsername())) {
            tvNickname.setText(loginBean.getUsername());
        }
        //手机
    }

    @Override
    public AppCompatActivity getActivity() {
        return AccountManagementActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_myicon:
                Acp.getInstance(getActivity()).request(new AcpOptions.Builder()
                                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                                        , Manifest.permission.READ_EXTERNAL_STORAGE
                                        , Manifest.permission.CAMERA
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
                                BoxingConfig singleImgConfig = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG).withMediaPlaceHolderRes(R.drawable.ic_boxing_default_image).withMaxCount(1)
                                        .needCamera(R.drawable.icon_addphoto);
                                Boxing.of(singleImgConfig).withIntent(getActivity(), BoxingActivity.class).start(getActivity(), 100);

                            }

                            @Override
                            public void onDenied(List<String> permissions) {
                                Toast.makeText(getActivity(), "请开启相关权限", Toast.LENGTH_LONG).show();
                            }
                        });
                break;
            //我的银行卡
            case R.id.ll_mybank:
                IntentUtil.Intent_MyBankActivity(getActivity());
                break;
            //修改昵称
            case R.id.ll_editnickname:
                IntentUtil.Intent_EditNickNameActivity(getActivity());
                break;
            //修改密码
            case R.id.ll_editpassword:
                IntentUtil.Intent_EditPasswordActivity(getActivity());
                break;
            //修改手机
            case R.id.ll_phone:
                IntentUtil.Intent_EditPhoneActivity(getActivity());
                break;


            case R.id.btn_loginout:
                JGApplication app = (JGApplication) getApplication();
                List<AppCompatActivity> activities = app.activities;
                for (Activity act : activities) {
                    act.finish();//显式结束
                }
                getSharedFileUtils().putString(MyConstant.loginuser, "");
                IntentUtil.Intent_LoginActivity(getActivity(), true);
                break;
        }
    }

    /**
     * 接收图片
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            List<BaseMedia> list = Boxing.getResult(data);
            if (list != null && list.size() > 0) {
                uploadImage(list.get(0));
            }
        }
    }

    /**
     * 上传图片
     *
     * @param baseMedia
     */
    private void uploadImage(final BaseMedia baseMedia) {
        JMessageClient.updateUserAvatar(new File(baseMedia.getPath()), new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    HttpUtil.upMyImage(loginBean.getUserId(),
                            baseMedia.getPath(), new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.i("asdf", "uploadImage" + response.body());

                                    try {
                                        org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());

                                        int code = jsonObject.getInt("code");

                                        if (code == 0) {

                                            RequestOptions options = new RequestOptions()
                                                    .placeholder(R.mipmap.ic_launcher)                //加载成功之前占位图
                                                    .error(R.mipmap.ic_launcher)                    //加载错误之后的错误图
                                                    .override(400, 400)                                //指定图片的尺寸
                                                    //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
                                                    .fitCenter()
                                                    //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                                                    .centerCrop()
                                                    .circleCrop()//指定图片的缩放类型为centerCrop （圆形）
                                                    .skipMemoryCache(true)                            //跳过内存缓存
                                                    .diskCacheStrategy(DiskCacheStrategy.ALL)        //缓存所有版本的图像
                                                    .diskCacheStrategy(DiskCacheStrategy.NONE)        //跳过磁盘缓存
                                                    .diskCacheStrategy(DiskCacheStrategy.DATA)        //只缓存原来分辨率的图片
                                                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);  //只缓存最终的图片
                                            Glide.with(ivMyicon.getContext())
                                                    .load(baseMedia.getPath())
                                                    .apply(options)
                                                    .into(ivMyicon);
                                            loginBean.setIcon(baseMedia.getPath());
                                            EventBus.getDefault().post(new UpdateUserMessageEventbus(loginBean));
                                            getSharedFileUtils().putString(MyConstant.loginuser, JSONObject.toJSONString(loginBean));


                                        } else {
                                            Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
                                    }


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

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                    Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
