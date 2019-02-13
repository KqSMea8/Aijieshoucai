package com.example.rongjiaying.aijieshoucai.message.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseAdapter;
import com.example.rongjiaying.aijieshoucai.callback.ProgressBarStringCallback;
import com.example.rongjiaying.aijieshoucai.constant.InternetConstant;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.constant.UrlConstant;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.my.adapter.RecommendationListAdapter;
import com.example.rongjiaying.aijieshoucai.my.bean.RecommendationBean;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.IntentUtil;
import com.example.rongjiaying.aijieshoucai.widget.CustomShapeImageView;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * 联系人列表
 */
public class ContactListActivity extends BaseActivity implements View.OnClickListener {
    LoginBean loginBean;
    ProgressBar progressBar;

    CustomShapeImageView ivIcon;//我的推荐人
    AppCompatTextView tvName;//我的推荐人姓名
    AppCompatTextView tvStatus;//是否齐架

    AppCompatImageView ivMyTuijian;//我的推荐 下拉图片

    RecyclerView rv1;
    RecyclerView rv2;

    AppCompatImageView ivTwoTuijian;
    AppCompatImageView ivThityTuijian;

    @Override
    public AppCompatActivity getActivity() {
        return ContactListActivity.this;
    }

    RecommendationListAdapter recommendationListAdapter1, recommendationListAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        progressBar = findViewById(R.id.progressbar);
        findViewById(R.id.iv_back).setVisibility(View.GONE);

        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("我的联系人");

        ivIcon = findViewById(R.id.iv_icon);
        tvName = findViewById(R.id.tv_name);
        tvStatus = findViewById(R.id.tv_status);
        ivMyTuijian = findViewById(R.id.iv_mytuijian);

        ivTwoTuijian = findViewById(R.id.iv_two_tuijian);
        ivThityTuijian = findViewById(R.id.iv_thity_tuijian);

        findViewById(R.id.ll_mytuijian).setOnClickListener(this);

        String message = getSharedFileUtils().getString(MyConstant.loginuser);
        loginBean = JSONObject.parseObject(message, LoginBean.class);
        initdirectUser(loginBean.getDirectUserId());


        rv1 = findViewById(R.id.rv_two);
        rv2 = findViewById(R.id.rv_thiry);

        rv1.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv2.setLayoutManager(new LinearLayoutManager(getActivity()));

        recommendationListAdapter1 = new RecommendationListAdapter(getActivity());
        recommendationListAdapter2 = new RecommendationListAdapter(getActivity());

        rv1.setAdapter(recommendationListAdapter1);
        rv2.setAdapter(recommendationListAdapter2);


        findViewById(R.id.ll_two).setOnClickListener(this);
        findViewById(R.id.ll_thity).setOnClickListener(this);
        initTwuoTuijian(0);
        initTwuoTuijian(1);

        recommendationListAdapter1.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position != -1) {
                    IntentUtil.Intent_BrokderRankingDetailActivity(getActivity(),
                            recommendationListAdapter1.getDatas().get(position).getUserId());
                }
            }
        });


        recommendationListAdapter2.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position != -1) {
                    IntentUtil.Intent_BrokderRankingDetailActivity(getActivity(),
                            recommendationListAdapter2.getDatas().get(position).getUserId());
                }
            }
        });
    }

    /**
     * 二级推荐人
     */
    private void initTwuoTuijian(final int type) {
        String url = "";
        switch (type) {
            case 0:
                url = UrlConstant.getDirectUser;
                break;
            case 1:
                url = UrlConstant.getInDirectUser;
                break;
            default:
                url = UrlConstant.getDirectUser;
                break;
        }
        HttpUtil.myRecommendation(url, loginBean.getUserId(), new ProgressBarStringCallback(progressBar) {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());

                    int code = jsonObject.getInt("code");
                    String msg = jsonObject.getString("msg");

                    if (code == 0) {
                        Log.i("asdf", jsonObject.getString("msg"));
                        List<RecommendationBean> recommendationBeans = JSONObject.parseArray(msg, RecommendationBean.class);
                        if (recommendationBeans != null && recommendationBeans.size() > 0) {
                            if (type == 0) {
                                recommendationListAdapter1.refreshData(recommendationBeans);
                            } else {
                                recommendationListAdapter2.refreshData(recommendationBeans);
                            }

                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 拿取父亲
     *
     * @param directUserId
     */
    private void initdirectUser(String directUserId) {
        HttpUtil.myPersonal(directUserId,
                new ProgressBarStringCallback(progressBar) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());

                            int code = jsonObject.getInt("code");
                            if (code == 0) {
                                final LoginBean lbean = JSONObject.parseObject(jsonObject.getString("msg"), LoginBean.class);
                                if (lbean != null) {
                                    RequestOptions options = new RequestOptions()
                                            .placeholder(R.mipmap.ic_launcher)                //加载成功之前占位图
                                            .error(R.mipmap.ic_launcher)                    //加载错误之后的错误图
                                            //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
                                            .fitCenter()
                                            //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                                            //.centerCrop()
                                            // .transform(new GlideCircleWithBorder(getActivity(), 5, Color.parseColor("#ffffff")))
                                            .skipMemoryCache(true)                            //跳过内存缓存
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)        //缓存所有版本的图像
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)        //跳过磁盘缓存
                                            .diskCacheStrategy(DiskCacheStrategy.DATA)        //只缓存原来分辨率的图片
                                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);  //只缓存最终的图片
                                    String imageurl = "";
                                    imageurl = InternetConstant.imageurl + lbean.getIcon();
                                    Glide.with(ivIcon.getContext())
                                            .load(imageurl)
                                            .apply(options)
                                            .into(ivIcon);

                                    //姓名
                                    tvName.setText(lbean.getUsername());
                                    int level = Integer.valueOf(lbean.getLevel());
                                    if (level > 10) {
                                        tvStatus.setText("已齐架");
                                        tvStatus.setTextColor(getResources().getColor(R.color.color_ff6900));
                                    } else if (level > 5) {
                                        tvStatus.setText("已架构");
                                        tvStatus.setTextColor(getResources().getColor(R.color.color_ff6900));
                                    } else {
                                        tvStatus.setText("尚未架构");
                                        tvStatus.setTextColor(getResources().getColor(R.color.color_bababa));
                                    }


                                    findViewById(R.id.ll_tuijiandata).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            IntentUtil.Intent_BrokderRankingDetailActivity(getActivity(),
                                                   lbean.getUserId());
                                        }
                                    });

                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    boolean ismytuijian = false;
    boolean ismytuijiantwo = false;
    boolean ismytuijianthity = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_mytuijian:
                if (!ismytuijian) {
                    ismytuijian = true;
                    findViewById(R.id.ll_tuijiandata).setVisibility(View.VISIBLE);
                    ivMyTuijian.setImageResource(R.drawable.icon_up2);
                } else {
                    ismytuijian = false;
                    findViewById(R.id.ll_tuijiandata).setVisibility(View.GONE);
                    ivMyTuijian.setImageResource(R.drawable.icon_dropdown2);
                }

                break;


            case R.id.ll_two:
                if (!ismytuijiantwo) {
                    ismytuijiantwo = true;
                  rv1.setVisibility(View.VISIBLE);
                    ivTwoTuijian.setImageResource(R.drawable.icon_up2);
                } else {
                    ismytuijiantwo = false;
                    rv1.setVisibility(View.GONE);
                    ivTwoTuijian.setImageResource(R.drawable.icon_dropdown2);
                }

                break;


            case R.id.ll_thity:
                if (!ismytuijianthity) {
                    ismytuijianthity = true;
                    rv2.setVisibility(View.VISIBLE);
                    ivThityTuijian.setImageResource(R.drawable.icon_up2);
                } else {
                    ismytuijianthity = false;
                  rv2.setVisibility(View.GONE);
                    ivThityTuijian.setImageResource(R.drawable.icon_dropdown2);
                }

                break;
        }
    }
}
