package com.example.rongjiaying.aijieshoucai.home.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.constant.InternetConstant;
import com.example.rongjiaying.aijieshoucai.home.bean.BrokderDetailBean;
import com.example.rongjiaying.aijieshoucai.home.bean.BrokerBean;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

/**
 * 经纪人详情
 */
public class BrokderRankingDetailActivity extends BaseActivity implements View.OnClickListener {
    String user_id;
    AppCompatImageView ivIcon;
    AppCompatTextView tvName, tvMoney;
    AppCompatTextView tv_incom;//提现
    AppCompatTextView tv_approve;//审批
    AppCompatTextView tvfangkuan;//放款
    AppCompatTextView tv_bohui;//驳回

    AppCompatTextView tv_sum_commision;

    AppCompatTextView tvSecondPerson,tvThirdPersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brokder_ranking_detail);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("经纪人详情");
        user_id = getIntent().getStringExtra("user_id");
        ivIcon = findViewById(R.id.iv_icon);
        tvName = findViewById(R.id.tv_name);
        tvMoney = findViewById(R.id.tv_money);

        tv_incom = findViewById(R.id.tv_incom);
        tv_approve = findViewById(R.id.tv_approve);
        tvfangkuan = findViewById(R.id.tv_fangkuan);
        tv_bohui = findViewById(R.id.tv_bohui);

        tv_sum_commision=findViewById(R.id.tv_sum_commision);

        tvSecondPerson = findViewById(R.id.tv_directcount);
        tvThirdPersion = findViewById(R.id.tv_indirectcount);
        initBroderDetail(user_id);


//   tv_incom

    }

    private void initBroderDetail(String userId) {
        HttpUtil.userDetail(userId, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        Log.i("asdf",jsonObject.getString("msg"));
                        JSONObject j = new JSONObject(jsonObject.getString("msg"));
                        if (j != null) {
                            RoundedCorners roundedCorners = new RoundedCorners(20);
                            RequestOptions options2 = new RequestOptions()
                                    .fitCenter()
                                    .bitmapTransform(roundedCorners)
                                    .error(R.drawable.ic_boxing_default_image)
                                    .placeholder(R.drawable.ic_boxing_default_image);
                            Glide.with(ivIcon.getContext())
                                    .load(InternetConstant.imageurl + j.getString("icon"))
                                    .apply(options2)
                                    .into(ivIcon);


                            if (!Judge.getBoolean_isNull(j.getString("username"))) {
                                tvName.setText(j.getString("username"));
                            } else {
                                tvName.setText("");
                            }


                            tvMoney.setText(j.getDouble("month_commission") + "");
                            //tv_incom

//
//            tv_incom.setText(brokerBean.getUser_detail_incom()+"单");
//        tv_approve.setText(brokerBean.getUser_detail_approve()+"单");
//        tvfangkuan.setText(brokerBean.getUser_detail_loan()+"单");
//        tv_bohui.setText(brokerBean.getUser_detail_turned_down()+"单");

                            tv_incom.setText(j.getDouble("user_detail_incom")+"单");
                            tv_approve.setText(j.getDouble("user_detail_approve")+"单");
                            tvfangkuan.setText(j.getDouble("user_detail_loan")+"单");
                            tv_bohui.setText(j.getDouble("user_detail_turned_down")+"单");

                            int  directCount=j.getInt("directCount");
                            int indirectCount=j.getInt("indirectCount");
                            tvSecondPerson.setText(directCount+"人");
                            tvThirdPersion.setText(indirectCount+"人");

                            tv_sum_commision.setText(j.getString("sum_commision"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public AppCompatActivity getActivity() {
        return BrokderRankingDetailActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag("userDetail");
    }
}
