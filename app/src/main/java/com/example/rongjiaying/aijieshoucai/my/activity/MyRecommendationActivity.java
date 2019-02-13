package com.example.rongjiaying.aijieshoucai.my.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.constant.InternetConstant;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.my.fragment.RecommendationFragment;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的推荐
 */
public class MyRecommendationActivity extends BaseActivity implements View.OnClickListener {
    List<String> titles = null;
    LoginBean loginBean;
    AppCompatTextView tvName, tvLevel, tvStatus;
    AppCompatImageView ivIcon;

    AppCompatTextView tvAllpersion,tvSecondPerson,tvThirdPersion;

    ViewPager viewPager;
    AppCompatTextView tvSelectSecond,tvSelectThiry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recommendation);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_myrecommentdation));
         viewPager = findViewById(R.id.viewpager);
        titles = new ArrayList<>();
        titles.add("二级经纪人");
        titles.add("三级经纪人");
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        String message = getSharedFileUtils().getString(MyConstant.loginuser);
        loginBean = com.alibaba.fastjson.JSONObject.parseObject(message, LoginBean.class);

        //姓名  等级   状态
        tvName = findViewById(R.id.tv_name);
        tvLevel = findViewById(R.id.tv_level);
        tvStatus = findViewById(R.id.tv_status);
        //姓名  等级   状态

        ivIcon = findViewById(R.id.iv_icon);

        tvAllpersion=findViewById(R.id.tv_allpersion);
        tvSecondPerson=findViewById(R.id.tv_secondperson);
        tvThirdPersion=findViewById(R.id.tv_thirdperson);

        tvSelectSecond=  findViewById(R.id.tv_selectsencond);
        tvSelectSecond.setOnClickListener(this);
       tvSelectThiry= findViewById(R.id.tv_selectthiry);
        tvSelectThiry.setOnClickListener(this);

        initBroderDetail(loginBean.getUserId());
    }

    @Override
    public AppCompatActivity getActivity() {
        return MyRecommendationActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;


            case R.id.tv_selectsencond:
                viewPager.setCurrentItem(0,true);
                tvSelectSecond.setBackgroundResource(R.drawable.circle_colorprimal_3dp_left);
                tvSelectSecond.setTextColor(getResources().getColor(R.color.white));
                tvSelectThiry.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvSelectThiry.setBackgroundResource(R.drawable.circle_white_3dp_right);
                break;

            case R.id.tv_selectthiry:
                viewPager.setCurrentItem(1,true);
                tvSelectSecond.setBackgroundResource(R.drawable.circle_white_3dp_left);
                tvSelectThiry.setBackgroundResource(R.drawable.circle_colorprimal_3dp_right);
                tvSelectSecond.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvSelectThiry.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return RecommendationFragment.newInstance(i);
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    /**
     * 经纪人详情
     *
     * @param userId
     */
    private void initBroderDetail(String userId) {
        HttpUtil.userDetail(userId, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body());

                    int code = jsonObject.getInt("code");
                    if (code == 0) {

                        JSONObject j = new JSONObject(jsonObject.getString("msg"));
                        if (j != null) {

                            //姓名
                            if (!Judge.getBoolean_isNull(j.getString("username"))) {
                                tvName.setText(j.getString("username"));
                            }
                            //姓名
                            //等级
                            if (!Judge.getBoolean_isNull(j.getString("username"))) {
                                tvLevel.setText(getLevel(j.getString("level")));
                            }
                            //等级
                            //姓名
                            if (!Judge.getBoolean_isNull(j.getString("username"))) {
                                tvName.setText(j.getString("username"));
                            }
                            //姓名


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
                            imageurl = InternetConstant.imageurl + j.getString("icon");
                            Glide.with(ivIcon.getContext())
                                    .load(imageurl)
                                    .apply(options)
                                    .into(ivIcon);

                            try {
                                int level = Integer.valueOf(j.getString("level"));

                                if (level > 10) {
                                    tvStatus.setText("已齐架");
                                } else if (level > 5) {
                                    tvStatus.setText("已架构");
                                } else {
                                    tvStatus.setText("尚未架构");
                                }

                                int  directCount=j.getInt("directCount");
                                int indirectCount=j.getInt("indirectCount");
                                int allcount=directCount+indirectCount;
                                tvAllpersion.setText(allcount+"");
                                tvSecondPerson.setText(directCount+"");
                                tvThirdPersion.setText(indirectCount+"");

                            } catch (Exception e) {

                            }

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String getLevel(String level) {
        int intlevel = Integer.valueOf(level);
        String s = "";
        switch (intlevel) {
            case 1:
                s = "经纪人";
                break;

            case 2:
                s = "铜牌经纪人";

                break;

            case 3:
                s = "银牌经纪人";
                break;

            case 4:
                s = "金牌经纪人";
                break;
            case 5:
                s = "钻石经纪人";
                break;
            case 6:
                s = "团队长";
                break;
            case 7:
                s = "团队经理";
                break;
            case 8:
                s = "区域经理";
                break;
            case 9:
                s = "区域总监";
                break;
            case 10:
                s = "高级区域总监";
                break;
            case 11:
                s = "合伙人";
                break;
            case 12:
                s = "区域合伙人";
                break;
            case 13:
                s = "高级合伙人";
                break;
            case 14:
                s = "荣誉发展副总裁";
                break;
            case 15:
                s = "荣誉发展总裁";
                break;
        }
        return s;
    }
}

