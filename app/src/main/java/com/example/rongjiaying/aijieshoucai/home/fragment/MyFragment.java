package com.example.rongjiaying.aijieshoucai.home.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.rongjiaying.aijieshoucai.BaseFragment;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseAdapter;
import com.example.rongjiaying.aijieshoucai.callback.ProgressBarStringCallback;
import com.example.rongjiaying.aijieshoucai.constant.InternetConstant;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.my.adapter.MyFragmentItemAdapter;
import com.example.rongjiaying.aijieshoucai.my.bean.MyFragmentItemBean;
import com.example.rongjiaying.aijieshoucai.my.eventbus.UpdateUserMessageEventbus;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.IntentUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.example.rongjiaying.aijieshoucai.widget.CustomShapeImageView;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jiguang.chat.application.JGApplication;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {

    MyFragmentItemAdapter myFragmentItemAdapter;

    public MyFragment() {
        // Required empty public constructor
    }

    /**
     */
    // TODO: Rename and change types and number of parameters
    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        EventBus.getDefault().register(this);
    }

    LoginBean loginBean;
    CustomShapeImageView customShapeImageView;
    ProgressBar progressBar, progress;
    AppCompatTextView tvUsername, tvLevel, tvMyintegral;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUserMessage(UpdateUserMessageEventbus messageEvent) {
        if (messageEvent != null && messageEvent.getLoginBean() != null) {
            updateLoginBean(messageEvent.getLoginBean());
        }
    }

    private void updateLoginBean(final LoginBean loginBean) {
        if (!Judge.getBoolean_isNull(loginBean.getIcon()))
        {
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
            if (new File(loginBean.getIcon()).exists()) {
                imageurl = loginBean.getIcon();
            } else {
                imageurl = InternetConstant.imageurl + loginBean.getIcon();
            }
            Glide.with(customShapeImageView.getContext())
                    .load(imageurl)
                    .apply(options)
                    .into(customShapeImageView);
        }



        //姓名
        tvUsername.setText(loginBean.getUsername());

        //level
        tvLevel.setText("LV" + loginBean.getLevel());
        try {

            final int level = Integer.valueOf(loginBean.getLevel());

            float ff=Float.valueOf(loginBean.getMyIntegral());
            final int myintegral=(int)ff;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switch (level) {
                        case 1:
                            tvLevel.setText("LV" + loginBean.getLevel() + "经纪人");
                            tvMyintegral.setText(myintegral + "/10000");
                            progress.setMax(10000);
                            progress.setProgress(myintegral);
                            break;

                        case 2:
                            tvLevel.setText("LV" + loginBean.getLevel() + "铜牌经纪人");
                            tvMyintegral.setText(myintegral + "/50000");
                            progress.setMax(50000);
                            progress.setProgress(myintegral);

                            break;

                        case 3:
                            tvLevel.setText("LV" + loginBean.getLevel() + "银牌经纪人");
                            tvMyintegral.setText(myintegral + "/100000");
                            progress.setMax(100000);
                            progress.setProgress(myintegral);
                            break;

                        case 4:
                            tvLevel.setText("LV" + loginBean.getLevel() + "金牌经纪人");
                            tvMyintegral.setText(myintegral + "/200000");
                            progress.setMax(200000);
                            progress.setProgress(myintegral);
                            break;
                        case 5:
                            tvLevel.setText("LV" + loginBean.getLevel() + "钻石经纪人");
                            tvMyintegral.setText(myintegral + "/400000");
                            progress.setMax(400000);
                            progress.setProgress(myintegral);
                            break;
                        case 6:
                            tvLevel.setText("LV" + loginBean.getLevel() + "团队长");
                            tvMyintegral.setText(myintegral + "/50000");
                            progress.setMax(50000);
                            progress.setProgress(myintegral);
                            break;
                        case 7:
                            tvLevel.setText("LV" + loginBean.getLevel() + "团队经理");
                            tvMyintegral.setText(myintegral + "/100000");
                            progress.setMax(100000);
                            progress.setProgress(myintegral);
                            break;
                        case 8:
                            tvLevel.setText("LV" + loginBean.getLevel() + "区域经理");
                            tvMyintegral.setText(myintegral + "/200000");
                            progress.setMax(200000);
                            progress.setProgress(myintegral);
                            break;
                        case 9:
                            tvLevel.setText("LV" + loginBean.getLevel() + "区域总监");
                            tvMyintegral.setText(myintegral + "/400000");
                            progress.setMax(400000);
                            progress.setProgress(myintegral);
                            break;
                        case 10:
                            tvLevel.setText("LV" + loginBean.getLevel() + "高级区域总监");
                            //  tvMyintegral.setText(myintegral+"/400000");
                            progress.setMax(100);
                            progress.setProgress(100);
                            break;
                        case 11:
                            tvLevel.setText("LV" + loginBean.getLevel() + "合伙人");
                            tvMyintegral.setText(myintegral + "/100000");
                            progress.setMax(100000);
                            progress.setProgress(myintegral);
                            break;
                        case 12:
                            tvLevel.setText("LV" + loginBean.getLevel() + "区域合伙人");
                            tvMyintegral.setText(myintegral + "/200000");
                            progress.setMax(200000);
                            progress.setProgress(myintegral);
                            break;
                        case 13:
                            tvLevel.setText("LV" + loginBean.getLevel() + "高级合伙人");
                            tvMyintegral.setText(myintegral + "/400000");
                            progress.setMax(400000);
                            progress.setProgress(myintegral);
                            break;
                        case 14:
                            tvLevel.setText("LV" + loginBean.getLevel() + "荣誉发展副总裁");
                            progress.setMax(100);
                            progress.setProgress(100);
                            break;
                        case 15:
                            tvLevel.setText("LV" + loginBean.getLevel() + "荣誉发展总裁");
                            progress.setMax(100);
                            progress.setProgress(100);
                            break;
                    }
                }
            });

        } catch (Exception e) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);

        String message = getSharedFileUtils(getActivity()).getString(MyConstant.loginuser);
        progress = view.findViewById(R.id.progress);
        progressBar = view.findViewById(R.id.progressbar);
        tvUsername = view.findViewById(R.id.tv_username);
        tvLevel = view.findViewById(R.id.tv_level);
        tvMyintegral = view.findViewById(R.id.tv_myintegral);

        loginBean = JSONObject.parseObject(message, LoginBean.class);
        //   view.findViewById(R.id.tv_drafts).setOnClickListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<MyFragmentItemBean> list = initData();
        myFragmentItemAdapter = new MyFragmentItemAdapter(getActivity());
        recyclerView.setAdapter(myFragmentItemAdapter);
        myFragmentItemAdapter.refreshData(list);


        myFragmentItemAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position != -1) {

                    if (loginBean != null && !Judge.getBoolean_isNull(loginBean.getUserId())) {
                        String message = myFragmentItemAdapter.getDatas().get(position).getTitle();
                        if (message.equals("我的消息")) {
                            IntentUtil.Intent_MyMessageActivity(getActivity());
                        } else if (message.equals("草稿箱")) {
                            IntentUtil.Intent_DraftsListActivity(getActivity());
                        } else if (message.equals("业绩看板")) {
                            IntentUtil.Intent_MyAchievementActivity(getActivity());
                        } else if (message.equals("我的推荐")) {
                            IntentUtil.Intent_MyRecommendationActivity(getActivity());
                        }else if (message.equals("分享")) {
                            IntentUtil.Intent_ShareActivity(getActivity());
                        }
                        else if (message.equals("常见问题")) {
                            IntentUtil.Intent_CommentQuestionActivity(getActivity());
                        }
                        else if (message.equals("关于我们")) {
                            IntentUtil.Intent_AboutUsActivity(getActivity());
                        }
                        else if (message.equals("订单中心"))
                        {
                            IntentUtil.Intent_OrderActivity(getActivity());
                        }
                    } else {
                        Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                        JGApplication app = (JGApplication) getActivity().getApplication();
                        List<AppCompatActivity> activities = app.activities;
                        for (Activity act : activities) {
                            act.finish();//显式结束
                        }
                        getSharedFileUtils(getActivity()).putString(MyConstant.loginuser, "");
                        IntentUtil.Intent_LoginActivity(getActivity(), false);
                    }


                }
            }
        });


        customShapeImageView = view.findViewById(R.id.iv_icon);//ic_boxing_default_image
        customShapeImageView.setOnClickListener(this);
        //  updateLoginBean(loginBean);
        if (loginBean != null && !Judge.getBoolean_isNull(loginBean.getUserId())) {
            initPersonData();
        } else {
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

            Glide.with(customShapeImageView.getContext())
                    .load(R.drawable.pic_user_off)
                    .apply(options)
                    .into(customShapeImageView);
        }

        return view;
    }

    private void initPersonData() {
        HttpUtil.myPersonal(loginBean.getUserId(),
                new ProgressBarStringCallback(progressBar) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());

                            int code = jsonObject.getInt("code");
                            if (code == 0) {
                                String msg=jsonObject.getString("msg");
                                loginBean = JSONObject.parseObject(jsonObject.getString("msg"), LoginBean.class);
                                if (loginBean != null) {
                                    getSharedFileUtils(getActivity()).putString(MyConstant.loginuser, JSONObject.toJSONString(loginBean));
                                    updateLoginBean(loginBean);
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private List<MyFragmentItemBean> initData() {
        List<MyFragmentItemBean> list = new ArrayList<>();
        int[] res = new int[]{ R.drawable.icon_drafts,
               R.drawable.icon_order, R.drawable.icon_achievement,
               R.drawable.icon_recommend , R.drawable.icon_share};
        String[] titles = new String[]{"草稿箱","订单中心", "业绩看板","团队管理", "分享"};
        for (int i = 0; i < titles.length; i++) {
            MyFragmentItemBean myFragmentItemBean = new MyFragmentItemBean();
            myFragmentItemBean.setId(i);
            myFragmentItemBean.setDrawableRes(res[i]);
            myFragmentItemBean.setTitle(titles[i]);
            list.add(myFragmentItemBean);
        }
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_icon:
                if (loginBean != null && !Judge.getBoolean_isNull(loginBean.getUserId())) {
                    IntentUtil.Intent_AccountManagementActivity(getActivity());
                } else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    JGApplication app = (JGApplication) getActivity().getApplication();
                    List<AppCompatActivity> activities = app.activities;
                    for (Activity act : activities) {
                        act.finish();//显式结束
                    }
                    getSharedFileUtils(getActivity()).putString(MyConstant.loginuser, "");
                    IntentUtil.Intent_LoginActivity(getActivity(), false);
                }

                break;


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            List<BaseMedia> list = Boxing.getResult(data);
            if (list != null && list.size() > 0) {
                uploadImage(list.get(0));
            }
        }
    }

    private void uploadImage(final BaseMedia baseMedia) {
        HttpUtil.upMyImage(loginBean.getUserId(),
                baseMedia.getPath(), new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

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
                                Glide.with(customShapeImageView.getContext())
                                        .load(baseMedia.getPath())
                                        .apply(options)
                                        .into(customShapeImageView);
                                loginBean.setIcon(baseMedia.getPath());
                                getSharedFileUtils(getActivity()).putString(MyConstant.loginuser, JSONObject.toJSONString(loginBean));


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
    }


}
