package com.example.rongjiaying.aijieshoucai.home.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSONObject;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseAdapter;
import com.example.rongjiaying.aijieshoucai.callback.ProgressBarStringCallback;
import com.example.rongjiaying.aijieshoucai.home.adapter.ProductListAdapter;
import com.example.rongjiaying.aijieshoucai.home.bean.ProductListBean;
import com.example.rongjiaying.aijieshoucai.interfac.OnProductApplyListener;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.IntentUtil;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BorrowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BorrowFragment extends Fragment {


    public BorrowFragment() {
        // Required empty public constructor
    }

    /**
     */
    // TODO: Rename and change types and number of parameters
    public static BorrowFragment newInstance() {
        BorrowFragment fragment = new BorrowFragment();
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
    ProgressBar progressBar;
    ProductListAdapter productListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_borrow, container, false);
        progressBar = view.findViewById(R.id.progressbar);
        RecyclerView recyclerView =  view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        productListAdapter = new ProductListAdapter(getActivity());
        recyclerView.setAdapter(productListAdapter);
        initBusinessData();
        productListAdapter.setOnProductApplyListener(new OnProductApplyListener() {
            @Override
            public void onProductApply(ProductListBean productListBean) {
                if (productListAdapter.getDatas().indexOf(productListBean)!=-1)
                {
                    IntentUtil.Intent_EntryInformationActivity(getActivity()
                            ,productListBean);
                }
            }
        });
        productListAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position!=-1)
                {
                    IntentUtil.Intent_ProductDetailActivity(getActivity()
                            ,productListAdapter.getDatas().get(position));
                }
            }
        });
        return view;
    }
    /**
     * 业务
     */
    private void initBusinessData() {
        HttpUtil.productList(new ProgressBarStringCallback(progressBar) {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());

                    int code = jsonObject.getInt("code");
                    if (code == 0) {
                        List<ProductListBean> productListBeans = JSONObject.parseArray(jsonObject.getString("msg"), ProductListBean.class);
                        if (productListBeans != null && productListBeans.size() > 0) {
                            productListAdapter.refreshData(productListBeans);
                        }
                    }

                } catch (Exception e) {

                }
            }
        });
    }
}
