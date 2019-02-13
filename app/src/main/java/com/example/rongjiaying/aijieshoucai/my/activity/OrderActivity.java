package com.example.rongjiaying.aijieshoucai.my.activity;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.constant.MyConstant;
import com.example.rongjiaying.aijieshoucai.home.fragment.OrderFragment;
import com.example.rongjiaying.aijieshoucai.order.fragment.OrderCheckFragment;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends BaseActivity {

    @Override
    public AppCompatActivity getActivity() {
        return null;
    }
    List<String> titles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        findViewById(R.id.iv_back).setVisibility(View.GONE);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);

        TabLayout tab =findViewById(R.id.tab);

        tvTitle.setText(getString(R.string.string_ordercenter));

        ViewPager viewPager =findViewById(R.id.viewpager);



        titles = new ArrayList<>();
        titles.add("审核");
        titles.add("签约");
        titles.add("已放款");


        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tab.setupWithViewPager(viewPager);
        Log.i("asdf",""+getSharedFileUtils().getString(MyConstant.loginuser));

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
