package com.example.rongjiaying.aijieshoucai.order.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
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
import com.bumptech.glide.request.RequestOptions;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.constant.InternetConstant;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.home.bean.ProductListBean;
import com.example.rongjiaying.aijieshoucai.home.dialogfragment.SelectProductDialogFragment;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.order.bean.OrderListBean;
import com.example.rongjiaying.aijieshoucai.order.eventbus.OrderListItemDeleEventbus;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.util.List;

/**
 * 初审补全资料
 */
public class OrderCompletionActivity extends BaseActivity implements View.OnClickListener {
    AppCompatImageView ivDrivinglicense, ivFront, ivReverse;
    AppCompatEditText etUsername, etPhone;
    OrderListBean orderListBean;

    AppCompatTextView tvDrivinglicenseMessage, tvFrontMessage, tvReverseMessage;
    AppCompatTextView tvProduceName;//产品名字

    ProductListBean p;//产品bean
    LoginBean loginBean;//登录信息

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_completion);
        orderListBean = getIntent().getParcelableExtra("item");
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_ordercompletion));

        String message = getSharedFileUtils().getString(MyConstant.loginuser);
        loginBean = JSONObject.parseObject(message, LoginBean.class);

        ivDrivinglicense = findViewById(R.id.iv_drivinglicense);
        ivFront = findViewById(R.id.iv_front);
        ivReverse = findViewById(R.id.iv_reverse);
        ivDrivinglicense.setOnClickListener(this);
        ivFront.setOnClickListener(this);
        ivReverse.setOnClickListener(this);

        etUsername = findViewById(R.id.et_username);
        etUsername.setText(orderListBean.getBorrowerName());
        etUsername.setSelection(etUsername.getText().length());

        progressBar = findViewById(R.id.progressbar);
        etPhone = findViewById(R.id.et_phone);
        etPhone.setText(orderListBean.getBorrowerPhone());
        etPhone.setSelection(etPhone.getText().length());


        tvProduceName = findViewById(R.id.tv_producename);
        tvProduceName.setText(orderListBean.getProduceName());
        tvProduceName.setOnClickListener(this);


        RequestOptions options1 = new RequestOptions()
                .centerCrop()
                .error(R.drawable.ic_boxing_default_image)
                .placeholder(R.drawable.ic_boxing_default_image);
        Glide.with(getActivity())
                .load(InternetConstant.imageurl + orderListBean.getDriviceLincern())
                .apply(options1)
                .into(ivDrivinglicense);


        RequestOptions options2 = new RequestOptions()
                .centerCrop()
                .error(R.drawable.ic_boxing_default_image)
                .placeholder(R.drawable.ic_boxing_default_image);
        Glide.with(this)
                .load(InternetConstant.imageurl + orderListBean.getCardFront())
                .apply(options2)
                .into(ivFront);


        RequestOptions options3 = new RequestOptions()
                .centerCrop()
                .error(R.drawable.ic_boxing_default_image)
                .placeholder(R.drawable.ic_boxing_default_image);
        Glide.with(this)
                .load(InternetConstant.imageurl + orderListBean.getCardBack())
                .apply(options3)
                .into(ivReverse);

        tvDrivinglicenseMessage = findViewById(R.id.tv_dirvinglicensemessage);
        tvFrontMessage = findViewById(R.id.tv_frontmessage);
        tvReverseMessage = findViewById(R.id.tv_reversemessage);

        if (!Judge.getBoolean_isNull(orderListBean.getDrvicerLincenceBack())) {
            tvDrivinglicenseMessage.setText(orderListBean.getDrvicerLincenceBack());
        }

        if (!Judge.getBoolean_isNull(orderListBean.getCardFrontBack())) {
            tvFrontMessage.setText(orderListBean.getCardFrontBack());
        }

        if (!Judge.getBoolean_isNull(orderListBean.getCardBackBack())) {
            tvReverseMessage.setText(orderListBean.getCardBackBack());
        }

        findViewById(R.id.tv_commit).setOnClickListener(this);//提交
    }

    @Override
    public AppCompatActivity getActivity() {
        return OrderCompletionActivity.this;
    }

    int type = 0;//1驾驶证   2正面身份证  3反面身份证

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            //驾驶证
            case R.id.iv_drivinglicense:
                type = 1;
                selectImage();
                break;

            //正面
            case R.id.iv_front:
                type = 2;
                selectImage();
                break;

            //反面
            case R.id.iv_reverse:
                type = 3;
                selectImage();
                break;

            //产品名字
            case R.id.tv_producename:
                SelectProductDialogFragment selectProductDialogFragment =
                        SelectProductDialogFragment.newInstance();


                selectProductDialogFragment.show(getSupportFragmentManager(),

                        selectProductDialogFragment.getClass().getName());


                selectProductDialogFragment.setOnProductItemListener(new SelectProductDialogFragment.OnProductItemListener() {
                    @Override
                    public void onProductItem(ProductListBean productListBean) {
                        if (productListBean != null) {
                            p = productListBean;
                            orderListBean.setApplyProduce(p.getId() + "");
                            tvProduceName.setText(p.getName());
                        }
                    }
                });
                break;
            //提交
            case R.id.tv_commit:
                if (Judge.getBoolean_isNull(drivinglicenseimage)) {
                    Toast.makeText(getActivity(), "请选择驾驶证图片", Toast.LENGTH_SHORT).show();
                } else if (Judge.getBoolean_isNull(frontimage)) {
                    Toast.makeText(getActivity(), "请选择身份证正面图片", Toast.LENGTH_SHORT).show();
                } else if (Judge.getBoolean_isNull(reverseimage)) {
                    Toast.makeText(getActivity(), "请选择身份证反面图片", Toast.LENGTH_SHORT).show();
                } else {
                    HttpUtil.orderCompletion(loginBean.getUserId(),
                            etUsername.getText().toString(),
                            orderListBean.getApplyProduce(),
                            orderListBean.getOrderCode(),
                            drivinglicenseimage,
                            frontimage,
                            reverseimage,
                            new StringCallback() {
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
                                    Toast.makeText(getActivity(), "提交失败", Toast.LENGTH_SHORT).show();
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
                            });
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

    String drivinglicenseimage = "", frontimage = "", reverseimage = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            List<BaseMedia> list = Boxing.getResult(data);
            if (list != null && list.size() > 0) {
                switch (type) {
                    case 1:
                        RequestOptions options1 = new RequestOptions()
                                .centerCrop()
                                .placeholder(R.drawable.ic_boxing_default_image);
                        Glide.with(this)
                                .load(list.get(0).getPath())
                                .apply(options1)
                                .into(ivDrivinglicense);
                        drivinglicenseimage = list.get(0).getPath();
                        break;

                    case 2:
                        RequestOptions options2 = new RequestOptions()
                                .centerCrop()
                                .placeholder(R.drawable.ic_boxing_default_image);
                        Glide.with(this)
                                .load(list.get(0).getPath())
                                .apply(options2)
                                .into(ivFront);
                        frontimage = list.get(0).getPath();
                        break;
                    case 3:
                        RequestOptions options3 = new RequestOptions()
                                .centerCrop()
                                .placeholder(R.drawable.ic_boxing_default_image);
                        Glide.with(this)
                                .load(list.get(0).getPath())
                                .apply(options3)
                                .into(ivReverse);
                        reverseimage = list.get(0).getPath();
                        break;
                }
            }
        }

        //注意判断null
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag("orderCompletion");
    }
}
