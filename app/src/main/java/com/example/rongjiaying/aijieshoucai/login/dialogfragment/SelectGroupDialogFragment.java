package com.example.rongjiaying.aijieshoucai.login.dialogfragment;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.blankj.utilcode.util.ScreenUtils;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseAdapter;
import com.example.rongjiaying.aijieshoucai.login.adapter.SelectGroupAdapter;
import com.example.rongjiaying.aijieshoucai.widget.MyDialogFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.RequestCallback;
import cn.jpush.im.android.api.model.GroupBasicInfo;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectGroupDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectGroupDialogFragment extends MyDialogFragment {


    public SelectGroupDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xffffff0));
        getDialog().getWindow().setLayout((int) (dm.widthPixels), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     */
    // TODO: Rename and change types and number of parameters
    public static SelectGroupDialogFragment newInstance() {
        SelectGroupDialogFragment fragment = new SelectGroupDialogFragment();
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

    SelectGroupAdapter selectGroupAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_group_dialog, container, false);

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

        final SmartRefreshLayout smartRefreshLayout = view.findViewById(R.id.refresh);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        selectGroupAdapter = new SelectGroupAdapter(getActivity());
        recyclerView.setAdapter(selectGroupAdapter);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                page = 0;
                initData();
                smartRefreshLayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                initData();
                smartRefreshLayout.finishLoadMore(2000/*,false*/);//传入false表示刷新失败
            }
        });
        initData();

        selectGroupAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position != -1) {
                    if (onSelectGroupListener != null) {
                        onSelectGroupListener.onSelectGroupItem(selectGroupAdapter.getDatas().get(position).getGroupID());
                    }
                    dismiss();
                }
            }
        });

        return view;
    }

    public void setOnSelectGroupListener(OnSelectGroupListener onSelectGroupListener) {
        this.onSelectGroupListener = onSelectGroupListener;
    }

    OnSelectGroupListener onSelectGroupListener;

    public interface OnSelectGroupListener {
        void onSelectGroupItem(long groupid);
    }

    int page = 0;

    private void initData() {
        JMessageClient.getPublicGroupListByApp(null, page, 5, new RequestCallback<List<GroupBasicInfo>>() {
            @Override
            public void gotResult(int i, String s, List<GroupBasicInfo> groupBasicInfos) {
                if (i == 0) {
                    if (page == 0) {
                        selectGroupAdapter.refreshData(groupBasicInfos);
                    } else {
                        selectGroupAdapter.loadMoreData(groupBasicInfos);
                    }
                } else {
                    Toast.makeText(getActivity(), "没有群可选", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
