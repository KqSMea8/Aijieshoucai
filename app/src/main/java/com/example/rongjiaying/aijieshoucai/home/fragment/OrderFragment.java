package com.example.rongjiaying.aijieshoucai.home.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rongjiaying.aijieshoucai.BaseFragment;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.order.fragment.OrderCheckFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends BaseFragment {


    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
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


    List<String> titles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        view.findViewById(R.id.iv_back).setVisibility(View.GONE);
        AppCompatTextView tvTitle = view.findViewById(R.id.tv_title);

        TabLayout tab = view.findViewById(R.id.tab);

        tvTitle.setText(getString(R.string.string_ordercenter));

        ViewPager viewPager = view.findViewById(R.id.viewpager);



        titles = new ArrayList<>();
        titles.add("审核");
        titles.add("签约");
        titles.add("已放款");


        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));
        tab.setupWithViewPager(viewPager);
        Log.i("asdf",""+getSharedFileUtils(getActivity()).getString(MyConstant.loginuser));
        return view;
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return OrderCheckFragment.newInstance(i);
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
}
