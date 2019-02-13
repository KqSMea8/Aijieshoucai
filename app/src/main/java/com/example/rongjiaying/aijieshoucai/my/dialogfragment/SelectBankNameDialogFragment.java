package com.example.rongjiaying.aijieshoucai.my.dialogfragment;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
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
import com.example.rongjiaying.aijieshoucai.callback.ProgressBarStringCallback;
import com.example.rongjiaying.aijieshoucai.interfac.OnSelectBankListener;
import com.example.rongjiaying.aijieshoucai.my.adapter.BankListAdapter;
import com.example.rongjiaying.aijieshoucai.my.bean.BankListBean;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.widget.MyDialogFragment;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectBankNameDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectBankNameDialogFragment extends MyDialogFragment {


    public SelectBankNameDialogFragment() {
        // Required empty public constructor
    }

    /**
     */
    // TODO: Rename and change types and number of parameters
    public static SelectBankNameDialogFragment newInstance() {
        SelectBankNameDialogFragment fragment = new SelectBankNameDialogFragment();
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
        getDialog().getWindow().setLayout((int) (dm.widthPixels), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    ProgressBar progressBar;
    BankListAdapter bankListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_bank_name_dialog, container, false);
        // 设置宽度为屏宽、靠近屏幕底部。
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);

        FrameLayout frameLayout = view.findViewById(R.id.framelayout);
        int width = ScreenUtils.getScreenWidth();
        int heigth = ScreenUtils.getScreenHeight();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, heigth / 2);
        frameLayout.setLayoutParams(params);

        progressBar = view.findViewById(R.id.progressbar);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bankListAdapter = new BankListAdapter(getActivity());
        recyclerView.setAdapter(bankListAdapter);
        initBankName();

        bankListAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position != -1) {
                    if (onSelectBankListener != null) {
                        onSelectBankListener.onSelectBank(
                                bankListAdapter.getDatas().get(position)
                        );
                    }
                    dismiss();
                }
            }
        });
        return view;
    }

    private void initBankName() {
        HttpUtil.bankName(new ProgressBarStringCallback(progressBar) {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body());

                    int code = jsonObject.getInt("code");
                    if (code == 0) {

                        List<BankListBean> bankListBeans = com.alibaba.fastjson.JSONObject.parseArray(jsonObject.getString("msg"), BankListBean.class);
                        bankListAdapter.refreshData(bankListBeans);
                    }

                } catch (Exception e) {
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);

            }
        });
    }

    public void setOnSelectBankListener(OnSelectBankListener onSelectBankListener) {
        this.onSelectBankListener = onSelectBankListener;
    }

    OnSelectBankListener onSelectBankListener;

}
