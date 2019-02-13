package com.example.rongjiaying.aijieshoucai.home.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ScreenUtils;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.constant.InternetConstant;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.home.bean.ProductListBean;
import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;
import com.example.rongjiaying.aijieshoucai.util.IntentUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;

/**
 * 业务详情
 */
public class ProductDetailActivity extends BaseActivity implements View.OnClickListener {
    ProductListBean productListBean;
    WebView webView;
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


        webView=findViewById(R.id.webview);
        initWebView();
        webView.loadUrl(InternetConstant.imageurl+productListBean.getDetail());



        findViewById(R.id.btn_commit).setOnClickListener(this);
    }
    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initWebView() {
        WebSettings ws = webView.getSettings();
        // 网页内容的宽度是否可大于WebView控件的宽度
        ws.setLoadWithOverviewMode(true);
        // 保存表单数据
        ws.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
        // 启动应用缓存
        ws.setAppCacheEnabled(true);
        // 设置缓存模式
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
    //    ws.setUseWideViewPort(true);
        // 不缩放
        webView.setInitialScale(100);
        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);
        //  页面加载好以后，再放开图片
        ws.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        ws.setDomStorageEnabled(true);
        // 排版适应屏幕
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否新窗口打开(加了后可能打不开网页)
        ws.setSupportMultipleWindows(true);

        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//            ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        }
        /** 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
        ws.setTextZoom(100);
        webView.setWebChromeClient(new WebChromeClient());
        // 与js交互
       // webView.addJavascriptInterface(new ImageClickInterface(this), "injectedObject");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                String javascript = "javascript:function ResizeImages() {" +
                        "var myimg,oldwidth;" +
                        "var maxwidth = document.body.clientWidth;" +
                        "for(i=0;i <document.images.length;i++){" +
                        "myimg = document.images[i];" +
                        "if(myimg.width > maxwidth){" +
                        "oldwidth = myimg.width;" +
                        "myimg.width = maxwidth;" +
                        "}" +
                        "}" +
                        "}";
           //     String width = String.valueOf(ScreenUtils.widthPixels(mContext));
                view.loadUrl(javascript);
                view.loadUrl("javascript:ResizeImages();");



            }
        });


        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final WebView.HitTestResult hitTestResult = webView.getHitTestResult();
                // 如果是图片类型或者是带有图片链接的类型
                if (hitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||
                        hitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                    // 弹出保存图片的对话框
                    new AlertDialog.Builder(getActivity())
                            .setItems(new String[]{"查看大图", "保存图片到相册"}, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String picUrl = hitTestResult.getExtra();
                                    //获取图片
                                    Log.e("picUrl", picUrl);
                                    switch (which) {
                                        case 0:
                                            break;
                                        case 1:
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            })
                            .show();
                    return true;
                }
                return false;
            }
        });

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

                if (loginBean!=null&&!Judge.getBoolean_isNull(loginBean.getUserId()))
                {
                    IntentUtil.Intent_EntryInformationActivity(getActivity(),productListBean);
                }else {
                    Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
                    IntentUtil.Intent_LoginActivity(getActivity());
                }


                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView!=null)
        {
            webView.clearHistory();
            webView.clearMatches();
            webView.clearCache(true);
            webView.destroy();
        }

    }
}
