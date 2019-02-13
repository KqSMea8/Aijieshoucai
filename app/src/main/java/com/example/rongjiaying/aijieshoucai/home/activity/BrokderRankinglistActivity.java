package com.example.rongjiaying.aijieshoucai.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.home.fragment.BrokderFragment;
import com.example.rongjiaying.aijieshoucai.home.fragment.OrderFragment;
import com.example.rongjiaying.aijieshoucai.order.fragment.OrderCheckFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 经纪人排行
 */
public class BrokderRankinglistActivity extends BaseActivity implements View.OnClickListener {
    List<String> titles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brokder_rankinglist);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_rankinglist));
        ViewPager viewPager = findViewById(R.id.viewpager);
        TabLayout tab =findViewById(R.id.tab);
        tab.setTabMode(TabLayout.MODE_FIXED);
        titles = new ArrayList<>();
        titles.add("提现榜");
        titles.add("进件榜");
        titles.add("审批榜");
        titles.add("级别榜");
        titles.add("团队榜");

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tab.setupWithViewPager(viewPager);
    }

    @Override
    public AppCompatActivity getActivity() {
        return BrokderRankinglistActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_back:
                finish();
                break;
        }
    }
    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return BrokderFragment.newInstance(i);
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
