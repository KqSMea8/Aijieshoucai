package com.example.rongjiaying.aijieshoucai.order.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.callback.ComViewProgressBarStringCallback;
import com.example.rongjiaying.aijieshoucai.order.bean.OrderListBean;
import com.example.rongjiaying.aijieshoucai.order.eventbus.OrderListItemDeleEventbus;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.util.List;

/**
 * 初审增添资料待上传
 */
public class OrderLastCompletionActivity extends BaseActivity implements View.OnClickListener {

    AppCompatImageView ivIcon1, ivIcon2, ivIcon3, ivIcon4, ivIcon5, ivIcon6;//营业执照
    OrderListBean orderListBean;
    ProgressBar progressBar;
    int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_last_completion);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_string_orderlastcompletion));
        orderListBean = getIntent().getParcelableExtra("item");
        progressBar = findViewById(R.id.progressbar);
        ivIcon1 = findViewById(R.id.iv_icon1);
        ivIcon1.setOnClickListener(this);
        ivIcon2 = findViewById(R.id.iv_icon2);
        ivIcon2.setOnClickListener(this);
        ivIcon3 = findViewById(R.id.iv_icon3);
        ivIcon3.setOnClickListener(this);
        ivIcon4 = findViewById(R.id.iv_icon4);
        ivIcon4.setOnClickListener(this);
        ivIcon5 = findViewById(R.id.iv_icon5);
        ivIcon5.setOnClickListener(this);
        ivIcon6 = findViewById(R.id.iv_icon6);
        ivIcon6.setOnClickListener(this);
        findViewById(R.id.tv_commit).setOnClickListener(this);
    }


    @Override
    public AppCompatActivity getActivity() {
        return OrderLastCompletionActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.iv_icon1:
                type = 1;
                selectImage();
                break;
            case R.id.iv_icon2:
                type = 2;
                selectImage();
                break;


            case R.id.iv_icon3:
                type = 3;
                selectImage();
                break;


            case R.id.iv_icon4:
                type = 4;
                selectImage();
                break;

            case R.id.iv_icon5:
                type = 5;
                selectImage();
                break;

            case R.id.iv_icon6:
                type = 6;
                selectImage();
                break;

            case R.id.tv_commit:
                if (!Judge.getBoolean_isNull(image1) && !Judge.getBoolean_isNull(image2)
                        && !Judge.getBoolean_isNull(image3) && !Judge.getBoolean_isNull(image4)
                        && !Judge.getBoolean_isNull(image5) && !Judge.getBoolean_isNull(image6)) {


                    HttpUtil.instanceorder(orderListBean.getOrderCode(),
                            image1,
                            image2,
                            image3,
                            image4,
                            image5,
                            image6,
                            new ComViewProgressBarStringCallback(progressBar,v) {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    try {
                                        org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());
                                        Log.i("asdf",""+response.body());
                                        int code = jsonObject.getInt("code");
                                        if (code == 0) {
                                            EventBus.getDefault().post(new OrderListItemDeleEventbus(orderListBean));
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
                                    Toast.makeText(getActivity(),Toast.LENGTH_LONG,Toast.LENGTH_LONG).show();
                                }
                            });

                }else {
                    Toast.makeText(getActivity(),"请选择图片",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void selectImage() {
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
    }

    String image1 = "", image2 = "", image3 = "", image4 = "", image5 = "", image6 = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            List<BaseMedia> list = Boxing.getResult(data);
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.ic_boxing_default_image);
            if (list != null && list.size() > 0) {
                switch (type) {
                    case 1:
                        Glide.with(getActivity())
                                .load(list.get(0).getPath())
                                .apply(options)
                                .into(ivIcon1);
                        image1 = list.get(0).getPath();
                        break;

                    case 2:
                        Glide.with(getActivity())
                                .load(list.get(0).getPath())
                                .apply(options)
                                .into(ivIcon2);
                        image2 = list.get(0).getPath();
                        break;

                    case 3:

                        Glide.with(getActivity())
                                .load(list.get(0).getPath())
                                .apply(options)
                                .into(ivIcon3);
                        image3 = list.get(0).getPath();
                        break;

                    case 4:
                        Glide.with(getActivity())
                                .load(list.get(0).getPath())
                                .apply(options)
                                .into(ivIcon4);
                        image4 = list.get(0).getPath();
                        break;

                    case 5:
                        Glide.with(getActivity())
                                .load(list.get(0).getPath())
                                .apply(options)
                                .into(ivIcon5);
                        image5 = list.get(0).getPath();
                        break;
                    case 6:
                        Glide.with(getActivity())
                                .load(list.get(0).getPath())
                                .apply(options)
                                .into(ivIcon6);
                        image6 = list.get(0).getPath();
                        break;

                }

            }
        }

        //注意判断null
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag("instanceorder");
    }
}
