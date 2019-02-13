package com.example.rongjiaying.aijieshoucai.home.dialogfragment;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.ScreenUtils;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseAdapter;
import com.example.rongjiaying.aijieshoucai.home.adapter.ProductListDialogFragmentAdapter;
import com.example.rongjiaying.aijieshoucai.home.bean.ProductListBean;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.widget.MyDialogFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectProductDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectProductDialogFragment extends MyDialogFragment {
    public SelectProductDialogFragment() {
        // Required empty public constructor
    }

    /**
     */
    // TODO: Rename and change types and number of parameters
    public static SelectProductDialogFragment newInstance() {
        SelectProductDialogFragment fragment = new SelectProductDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xffffff0));
        //   getDialog().getWindow().setLayout((int) (dm.widthPixels * 0.90), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    ProgressBar progressBar;
    ProductListDialogFragmentAdapter productListDialogFragmentAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_product_dialog, container, false);
        // 设置宽度为屏宽、靠近屏幕底部。
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);

        FrameLayout frameLayout = view.findViewById(R.id.framelayout);
        int width = ScreenUtils.getScreenWidth();
        int height = ScreenUtils.getScreenHeight();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height / 2);
        frameLayout.setLayoutParams(params);

        progressBar = view.findViewById(R.id.progressbar);

        RecyclerView recyclerView=view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        productListDialogFragmentAdapter =new ProductListDialogFragmentAdapter(getActivity());
        recyclerView.setAdapter(productListDialogFragmentAdapter);
        initProductData();


        productListDialogFragmentAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (onProductItemListener!=null)
                {
                    onProductItemListener.onProductItem(
                            productListDialogFragmentAdapter.getDatas().get(position)
                    );
                }
                dismiss();
            }
        });
        return view;
    }

    /**
     * 产品列表
     */
    private void initProductData() {
        HttpUtil.productList(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body());

                    int code = jsonObject.getInt("code");
                    String msg = jsonObject.getString("msg");
                    if (code == 0) {
                        Log.i("asdf", "" + msg);
                        List<ProductListBean>productListBeans= com.alibaba.fastjson.JSONObject
                                .parseArray(msg,ProductListBean.class);

                        if (productListBeans!=null&&productListBeans.size()>0)
                        {
                            productListDialogFragmentAdapter.refreshData(productListBeans);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag("productList");
    }

    public void setOnProductItemListener(OnProductItemListener onProductItemListener) {
        this.onProductItemListener = onProductItemListener;
    }

    OnProductItemListener onProductItemListener;
    public interface OnProductItemListener{
        void onProductItem(ProductListBean productListBean);
    }
}
