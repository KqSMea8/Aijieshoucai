package com.example.rongjiaying.aijieshoucai.order.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.rongjiaying.aijieshoucai.order.adapter.DocumentTypeBeanAdapter;
import com.example.rongjiaying.aijieshoucai.order.bean.DocumenTtypeBean;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 终审补资料
 */
public class OrderLastCompletionActivity extends BaseActivity implements View.OnClickListener {
    DocumentTypeBeanAdapter documentTypeBeanAdapter;
    AppCompatImageView ivIcon;
OrderListBean orderListBean;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_last_completion);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_string_orderlastcompletion));
        orderListBean=getIntent().getParcelableExtra("item");
        //证件类型
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        documentTypeBeanAdapter = new DocumentTypeBeanAdapter(getActivity());
        recyclerView.setAdapter(documentTypeBeanAdapter);
        //证件类型
        progressBar=findViewById(R.id.progressbar);
        List<DocumenTtypeBean> list = initData();
        documentTypeBeanAdapter.refreshData(list);

        documentTypeBeanAdapter.setOnDocumentTypeListener(new DocumentTypeBeanAdapter.OnDocumentTypeListener() {
            @Override
            public void onDocumentTypeItem(DocumenTtypeBean item) {
                int position = documentTypeBeanAdapter.getDatas().indexOf(item);
                if (position != -1) {
                    documentTypeBeanAdapter.setCheckPositon(position);
                }
            }
        });


        ivIcon = findViewById(R.id.iv_icon);
        ivIcon.setOnClickListener(this);
        findViewById(R.id.tv_commit).setOnClickListener(this);
    }

    private List<DocumenTtypeBean> initData() {
        List<DocumenTtypeBean> documenTtypeBeans = new ArrayList<>();
        String[] titles = new String[]{"营业执照", "寿险保单", "房产证/购房合同", "公积金截图", "社保截图", "芝麻信用分截图"};
        for (int i = 0; i < titles.length; i++) {
            DocumenTtypeBean documenTtypeBean = new DocumenTtypeBean();
            documenTtypeBean.setId((i + 1));
            documenTtypeBean.setIscheck(false);
            documenTtypeBean.setTitle(titles[i]);
            documenTtypeBeans.add(documenTtypeBean);
        }
        return documenTtypeBeans;
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

            case R.id.iv_icon:
                selectImage();
                break;

            case R.id.tv_commit:
                if (documentTypeBeanAdapter.getPosition().getId() == 0) {
                    Toast.makeText(getActivity(), "请勾选证件类型", Toast.LENGTH_SHORT).show();
                } else if (Judge.getBoolean_isNull(image)) {
                    Toast.makeText(getActivity(), "请选择证件图片", Toast.LENGTH_SHORT).show();
                } else {
                    HttpUtil.instanceorder(documentTypeBeanAdapter.getPosition().getId()+"",
                            orderListBean.getOrderCode(),
                            image,
                            new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    try {
                                        JSONObject jsonObject=new JSONObject(response.body());
                                        int code=jsonObject.getInt("code");
                                        Log.i("asdf",""+response.body());
                                        if (code==0)
                                        {
                                            EventBus.getDefault().post(new OrderListItemDeleEventbus(orderListBean));
                                            finish();
                                        }else {
                                            Toast.makeText(getActivity(),"提交失败",Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(getActivity(),"提交失败",Toast.LENGTH_SHORT).show();
                                    }
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

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                    Toast.makeText(getActivity(),"提交失败",Toast.LENGTH_SHORT).show();
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

    String image = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            List<BaseMedia> list = Boxing.getResult(data);
            if (list != null && list.size() > 0) {
                RequestOptions options3 = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.ic_boxing_default_image);
                Glide.with(this)
                        .load(list.get(0).getPath())
                        .apply(options3)
                        .into(ivIcon);
                image = list.get(0).getPath();
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
