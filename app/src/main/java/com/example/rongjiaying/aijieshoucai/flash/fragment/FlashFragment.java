package com.example.rongjiaying.aijieshoucai.flash.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.constant.InternetConstant;
import com.example.rongjiaying.aijieshoucai.flash.bean.FlashBean;
import com.example.rongjiaying.aijieshoucai.util.IntentUtil;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FlashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FlashFragment extends Fragment {


    public FlashFragment() {
        // Required empty public constructor
    }

    FlashBean flashBean;
    ArrayList<FlashBean> flashBeans;

    /**
     */
    // TODO: Rename and change types and number of parameters
    public static FlashFragment newInstance(FlashBean flashBean, ArrayList<FlashBean> flashBeans) {
        FlashFragment fragment = new FlashFragment();
        Bundle args = new Bundle();
        args.putParcelable("item", flashBean);
        args.putParcelableArrayList("items", flashBeans);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            flashBean = getArguments().getParcelable("item");
            flashBeans = getArguments().getParcelableArrayList("items");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flash, container, false);
        AppCompatImageView icon = view.findViewById(R.id.iv_icon);
        RequestOptions options2 = new RequestOptions()
                .centerCrop();
        Glide.with(icon.getContext())
                .load(InternetConstant.imageurl + flashBean.getResult())
                .apply(options2)
                .into(icon);

        if (flashBeans.indexOf(flashBean) == (flashBeans.size() - 1)) {
            view.findViewById(R.id.tv_commit).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.tv_commit).setVisibility(View.GONE);
        }
        view.findViewById(R.id.tv_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.Intent_LoginActivity(getActivity());
                getActivity().finish();
            }
        });
        return view;
    }

}
