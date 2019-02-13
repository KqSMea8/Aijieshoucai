package com.example.rongjiaying.aijieshoucai.my.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.constant.MyWorkConstant;
import com.example.rongjiaying.aijieshoucai.constant.UrlConstant;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.util.QRCodeUtil;
import com.example.rongjiaying.aijieshoucai.work.GetMWork;
import com.example.rongjiaying.aijieshoucai.work.SaveCaoGaoWork;

import java.util.HashMap;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * share activity
 */
public class ShareActivity extends BaseActivity implements View.OnClickListener {
    LoginBean loginBean;
    AppCompatImageView ivShare;
    AppCompatTextView tvCode;

    @Override
    public AppCompatActivity getActivity() {
        return ShareActivity.this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("分享主页");
        String message = getSharedFileUtils().getString(MyConstant.loginuser);
        loginBean = JSONObject.parseObject(message, LoginBean.class);

        ivShare = findViewById(R.id.iv_share);
        tvCode = findViewById(R.id.tv_code);
        Bitmap mBitmap = QRCodeUtil.createQRCodeBitmap(UrlConstant.sharehtml + "?user_id=" + loginBean.getUserId(), 104, 104);
        ivShare.setImageBitmap(mBitmap);
        tvCode.setText("我的邀请码:" + loginBean.getInviteCode());

        findViewById(R.id.ll_qq).setOnClickListener(this);
        findViewById(R.id.ll_weixin).setOnClickListener(this);
        findViewById(R.id.ll_pengyouquan).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.ll_qq:
                share(QQ.NAME);
                break;
            case R.id.ll_weixin:
                share(Wechat.NAME);
                break;
            case R.id.ll_pengyouquan:
                share(WechatMoments.NAME);
                break;
        }
    }

    private void share(String name) {
        Platform.ShareParams params = new Platform.ShareParams();
        if (name.equals(Wechat.NAME) || name.equals(WechatMoments.NAME)) {
            params.setShareType(Platform.SHARE_WEBPAGE);
            params.setText("我的邀请码是" + loginBean.getInviteCode());
            params.setTitle("爱杰搜财");
            params.setUrl(UrlConstant.sharehtml + "?user_id=" + loginBean.getInviteCode());
            Platform platform = ShareSDK.getPlatform(name);
            platform.setPlatformActionListener(new MyShareCallback());
            platform.share(params);


        } else if (name.equals(QQ.NAME)) {
            OnekeyShare oks = new OnekeyShare();
            //关闭sso授权
            oks.disableSSOWhenAuthorize();
            oks.setImageUrl(UrlConstant.sharehtml + "?user_id=" + loginBean.getInviteCode());
            oks.setTitleUrl(UrlConstant.sharehtml + "?user_id=" + loginBean.getInviteCode());
            oks.setText("我的邀请码是" + loginBean.getInviteCode());
            oks.setTitle("爱杰搜财");
            oks.setPlatform(QQ.NAME);
            oks.setCallback(new MyShareCallback());
//oks.setPlatform(QZone.NAME);
            oks.show(getActivity());
        }

    }


    private class MyShareCallback implements PlatformActionListener {

        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            Toast.makeText(getActivity(), "分享成功", Toast.LENGTH_SHORT).show();
getShareM();
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            Toast.makeText(getActivity(), "分享失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(Platform platform, int i) {
            Toast.makeText(getActivity(), "分享取消", Toast.LENGTH_SHORT).show();
        }
    }

    private void getShareM() {
        Data data = new Data.Builder()
                .putString("user_id", loginBean.getUserId())
                .putString("money_m", "500")
                .build();
        OneTimeWorkRequest myWorkRequest =

                new OneTimeWorkRequest.Builder(GetMWork.class)
                        .addTag(MyWorkConstant.saveCaogao)
                        .setInputData(data)
                        .build();
        WorkManager.getInstance().enqueue(myWorkRequest);
    }
}
