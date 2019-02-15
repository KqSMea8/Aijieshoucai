package com.example.rongjiaying.aijieshoucai.home.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.constant.InternetConstant;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.home.bean.ProductListBean;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.util.IntentUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.shizhefei.view.largeimage.LargeImageView;
import com.shizhefei.view.largeimage.factory.FileBitmapDecoderFactory;

import java.io.File;

/**
 * 业务详情
 */
public class ProductDetailActivity extends BaseActivity implements View.OnClickListener {
    ProductListBean productListBean;
    private LargeImageView mageView;
    LoginBean loginBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        productListBean = getIntent().getParcelableExtra("item");
        String message = getSharedFileUtils().getString(MyConstant.loginuser);
        loginBean = JSONObject.parseObject(message, LoginBean.class);
        tvTitle.setText(productListBean.getName());


        Log.i("asdf", "" + InternetConstant.imageurl + productListBean.getDetail());
        mageView = findViewById(R.id.imageview);


downloadImage(InternetConstant.imageurl+productListBean.getDetail());
        findViewById(R.id.btn_commit).setOnClickListener(this);
    }


    public void downloadImage(final String image) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Context context = getApplicationContext();
                    FutureTarget<File> target = Glide.with(context)
                            .load(image)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                    final File imageFile = target.get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //  Toast.makeText(context, imageFile.getPath(), Toast.LENGTH_LONG).show();
                            mageView.setEnabled(true);
                            //通过文件的方式加载sd卡中的大图
                            mageView.setImage(new FileBitmapDecoderFactory(imageFile));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public AppCompatActivity getActivity() {
        return ProductDetailActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.btn_commit:

                if (loginBean != null && !Judge.getBoolean_isNull(loginBean.getUserId())) {
                    IntentUtil.Intent_EntryInformationActivity(getActivity(), productListBean);
                } else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    IntentUtil.Intent_LoginActivity(getActivity());
                }


                break;
        }
    }


}
