package com.example.rongjiaying.aijieshoucai.my.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.example.rongjiaying.aijieshoucai.constant.MyWorkConstant;
import com.example.rongjiaying.aijieshoucai.home.bean.ProductListBean;
import com.example.rongjiaying.aijieshoucai.home.dialogfragment.SelectProductDialogFragment;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.my.eventbus.DraftsEventbus;
import com.example.rongjiaying.aijieshoucai.order.bean.OrderListBean;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.example.rongjiaying.aijieshoucai.work.SaveCaoGaoWork;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.util.List;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

/**
 * 草稿箱
 */
public class DraftsActivity extends BaseActivity implements View.OnClickListener {
    AppCompatImageView ivDrivinglicense, ivFront, ivReverse;

    OrderListBean orderListBean;

    AppCompatEditText etUsername, etPhone, etCode;//

    AppCompatTextView tvProductName;
    LoginBean loginBean;//登录信息
    AppCompatTextView tvSendCode;//发送验证码
    /**
     * 倒计时控件
     */
    private CountDownTimer timer;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drafts);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_drafts));
        String message = getSharedFileUtils().getString(MyConstant.loginuser);
        loginBean = JSONObject.parseObject(message, LoginBean.class);
        orderListBean = getIntent().getParcelableExtra("item");

        ivDrivinglicense = findViewById(R.id.iv_drivinglicense);
        ivFront = findViewById(R.id.iv_front);
        ivReverse = findViewById(R.id.iv_reverse);

        ivDrivinglicense.setOnClickListener(this);
        ivFront.setOnClickListener(this);
        ivReverse.setOnClickListener(this);

        findViewById(R.id.ll_selectproduct).setOnClickListener(this);
        findViewById(R.id.tv_dimiss).setOnClickListener(this);
        findViewById(R.id.tv_commit).setOnClickListener(this);
        findViewById(R.id.tv_commit).setOnClickListener(this); //重新录入
        tvSendCode = findViewById(R.id.tv_sendcode);
        tvSendCode.setOnClickListener(this);//发送短信验证码
        //textview
        etUsername = findViewById(R.id.et_username);
        etPhone = findViewById(R.id.et_phone);
        etCode = findViewById(R.id.et_code);
        tvProductName = findViewById(R.id.tv_producename);

        progressBar = findViewById(R.id.progressbar);
        //textview

        if (orderListBean != null) {
            if (!Judge.getBoolean_isNull(orderListBean.getBorrowerName())) {
                etUsername.setText(orderListBean.getBorrowerName());
                etUsername.setSelection(etUsername.getText().length());
            }

            if (!Judge.getBoolean_isNull(orderListBean.getBorrowerPhone())) {
                etPhone.setText(orderListBean.getBorrowerPhone());
                etPhone.setSelection(etPhone.getText().length());
            }

            //产品名字
            if (!Judge.getBoolean_isNull(orderListBean.getProduceName())) {
                tvProductName.setText(orderListBean.getProduceName());
            }

            //行驶证
            if (!Judge.getBoolean_isNull(orderListBean.getDriviceLincern())) {
                RequestOptions options1 = new RequestOptions()
                        .centerCrop()
                        .error(R.drawable.ic_boxing_default_image)
                        .placeholder(R.drawable.ic_boxing_default_image);
                Glide.with(getActivity())
                        .load(InternetConstant.imageurl + orderListBean.getDriviceLincern())
                        .apply(options1)
                        .into(ivDrivinglicense);
            }
            //行驶证
            //正面
            if (!Judge.getBoolean_isNull(orderListBean.getCardFront())) {
                RequestOptions options2 = new RequestOptions()
                        .centerCrop()
                        .error(R.drawable.ic_boxing_default_image)
                        .placeholder(R.drawable.ic_boxing_default_image);
                Glide.with(getActivity())
                        .load(InternetConstant.imageurl + orderListBean.getCardFront())
                        .apply(options2)
                        .into(ivFront);
            }
            //正面

            //背面
            if (!Judge.getBoolean_isNull(orderListBean.getCardBack())) {
                RequestOptions options3 = new RequestOptions()
                        .centerCrop()
                        .error(R.drawable.ic_boxing_default_image)
                        .placeholder(R.drawable.ic_boxing_default_image);
                Glide.with(getActivity())
                        .load(InternetConstant.imageurl + orderListBean.getCardBack())
                        .apply(options3)
                        .into(ivReverse);
            }
            //背面
        }
    }

    @Override
    public AppCompatActivity getActivity() {
        return DraftsActivity.this;
    }

    int type = 0;//1驾驶证   2正面身份证  3反面身份证
    ProductListBean p;//产品bean
    String drivinglicenseimage = "", frontimage = "", reverseimage = "";

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

            case R.id.ll_selectproduct:
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
                            tvProductName.setText(p.getName());
                        }
                    }
                });
                break;

            case R.id.tv_dimiss:
                finish();
                break;

            //重新录入
            case R.id.tv_commit:
                if (Judge.getBoolean_isNull(etUsername.getText().toString())) {
                    Toast.makeText(getActivity(), getString(R.string.string_hint_username), Toast.LENGTH_SHORT).show();
                } else if (Judge.getBoolean_isNull(etPhone.getText().toString())) {
                    Toast.makeText(getActivity(), getString(R.string.string_hint_phone), Toast.LENGTH_SHORT).show();
                } else if (Judge.getBoolean_isNull(orderListBean.getApplyProduce())) {
                    Toast.makeText(getActivity(), getString(R.string.string_hint_selectproduct), Toast.LENGTH_SHORT).show();
                }
                if (Judge.getBoolean_isNull(etCode.getText().toString())) {
                    Toast.makeText(getActivity(), getString(R.string.string_hint_code), Toast.LENGTH_SHORT).show();
                } else if (Judge.getBoolean_isNull(orderListBean.getDriviceLincern())) {
                    Toast.makeText(getActivity(), "请选择驾驶证图片", Toast.LENGTH_SHORT).show();
                } else if (Judge.getBoolean_isNull(orderListBean.getCardFront())) {
                    Toast.makeText(getActivity(), "请选择身份证正面图片", Toast.LENGTH_SHORT).show();
                } else if (Judge.getBoolean_isNull(orderListBean.getCardBack())) {
                    Toast.makeText(getActivity(), "请选择身份证反面图片", Toast.LENGTH_SHORT).show();
                } else {


                    Data data = new Data.Builder()
                            .putString("user_id", loginBean.getUserId())
                            .putString("username", etUsername.getText().toString())
                            .putString("phone", etPhone.getText().toString())
                            .putString("pid", orderListBean.getApplyProduce() + "")//这个code 不是验证二维码   而是业务id
                            .putString("drivinglicenseimage", drivinglicenseimage)
                            .putString("frontimage", frontimage)
                            .putString("orderListBean",JSONObject.toJSONString(orderListBean))
                            .putString("status","2")
                            .putString("reverseimage", reverseimage)
                            .build();
                    OneTimeWorkRequest myWorkRequest =

                            new OneTimeWorkRequest.Builder(SaveCaoGaoWork.class)
                                    .addTag(MyWorkConstant.saveCaogao)
                                    .setInputData(data)
                                    .build();
                    WorkManager.getInstance().enqueue(myWorkRequest);
//                    EventBus.getDefault().post(new DraftsEventbus(orderListBean, 1));
                    deloredit = 1;
                    finish();
//                    HttpUtil.saveCaogao(loginBean.getUserId() + "",
//                            etUsername.getText().toString(),
//                            etPhone.getText().toString(),
//                            orderListBean.getApplyProduce(),
//                            orderListBean.getDriviceLincern(),
//                            orderListBean.getCardFront(),
//                            orderListBean.getCardBack(),
//                            orderListBean.getOrderCode(),
//                            "2", new StringCallback() {
//                                @Override
//                                public void onSuccess(Response<String> response) {
//                                    try {
//                                        org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());
//                                        int code = jsonObject.getInt("code");
//                                        if (code == 0) {
//                                            type = 0;
//                                            deloredit = 1;
//                                            EventBus.getDefault().post(new DraftsEventbus(orderListBean, 1));
//                                            finish();
//                                        } else {
//                                            Toast.makeText(getActivity(), "录入失败", Toast.LENGTH_SHORT).show();
//                                        }
//                                    } catch (JSONException e) {
//                                        Toast.makeText(getActivity(), "录入错误", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//                                @Override
//                                public void onStart(Request<String, ? extends Request> request) {
//                                    super.onStart(request);
//                                    progressBar.setVisibility(View.VISIBLE);
//                                }
//
//                                @Override
//                                public void onFinish() {
//                                    super.onFinish();
//                                    progressBar.setVisibility(View.GONE);
//                                }
//
//                                @Override
//                                public void onError(Response<String> response) {
//                                    super.onError(response);
//                                    Toast.makeText(getActivity(), "录入错误", Toast.LENGTH_SHORT).show();
//                                }
//                            });
                }


                break;
            //发送短信验证码
            case R.id.tv_sendcode:
                if (Judge.getBoolean_isNull(etPhone.getText().toString().trim())) {
                    Toast.makeText(getActivity(), getString(R.string.string_hint_phone), Toast.LENGTH_SHORT).show();
                } else {
                    HttpUtil.sendCode(etPhone.getText().toString(), new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Log.i("asdf", "" + response.body());
                            try {
                                org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());
                                int code = jsonObject.getInt("code");
                                if (code != 0) {
                                    Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                                } else {

                                    if (timer != null) {
                                        timer.cancel();
                                        timer = null;
                                    }
                                    /** 倒计时60秒，一次1秒 */
                                    timer = new CountDownTimer(60 * 1000, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            if (getActivity() != null) {
                                                // TODO Auto-generated method stub
                                                tvSendCode.setText("还剩" + millisUntilFinished / 1000 + "秒");
                                                tvSendCode.setClickable(false);
                                            }

                                        }

                                        @Override
                                        public void onFinish() {
                                            tvSendCode.setText("重新获取验证码");
                                            tvSendCode.setClickable(true);
                                        }
                                    }.start();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), getString(R.string.string_sendcodeerr), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            Toast.makeText(getActivity(), getString(R.string.string_sendcodeerr), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onStart(Request<String, ? extends Request> request) {
                            super.onStart(request);
                            progressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
                break;


        }
    }

    int deloredit = 2;//dele  1  edit2

    @Override
    protected void onPause() {
        super.onPause();

        if (type == 0 && deloredit == 2) {
            String code = "";
            String producename = "";
            if (p != null) {
                code = p.getId() + "";
                producename = p.getName();
            } else {
                if (Judge.getBoolean_isNull(orderListBean.getOrderCode())) {
                    code = "";
                    producename = "";
                } else {
                    code = orderListBean.getApplyProduce();
                    producename = orderListBean.getProduceName();
                }

            }
            orderListBean.setBorrowerName(etUsername.getText().toString());
            orderListBean.setApplyProduce(code);
            orderListBean.setBorrowerId(orderListBean.getBorrowerId());
            orderListBean.setBackStatus(orderListBean.getBackStatus());
            orderListBean.setBorrowerPhone(etPhone.getText().toString());
            orderListBean.setProduceName(producename);
            orderListBean.setOrderCode(orderListBean.getOrderCode());
            HttpUtil.saveCaogao(loginBean.getUserId(),
                    etUsername.getText().toString(),
                    etPhone.getText().toString(),
                    code,
                    orderListBean.getDriviceLincern(),
                    orderListBean.getCardFront(),
                    orderListBean.getCardBack(),
                    orderListBean.getOrderCode(), "", new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            EventBus.getDefault().post(new DraftsEventbus(orderListBean, deloredit));
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                        }

                        @Override
                        public void onStart(Request<String, ? extends Request> request) {
                            super.onStart(request);
                        }
                    });
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
                        orderListBean.setDriviceLincern(drivinglicenseimage);
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
                        orderListBean.setCardFront(frontimage);
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
                        orderListBean.setCardBack(reverseimage);
                        break;
                }
                type = 0;
            }
        }

        //注意判断null
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
