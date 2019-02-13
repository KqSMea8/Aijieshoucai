package com.example.rongjiaying.aijieshoucai.my.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseViewHolder;
import com.example.rongjiaying.aijieshoucai.baseadapter.SimpleAdapter;
import com.example.rongjiaying.aijieshoucai.my.bean.WithdrawalsrecordBean;
import com.example.rongjiaying.aijieshoucai.my.bean.WithdrawalsrecordDetailListBean;
import com.example.rongjiaying.aijieshoucai.util.Judge;

import java.util.List;

public class WithdrawalsrecordAdapter extends SimpleAdapter<WithdrawalsrecordBean> {
    public WithdrawalsrecordAdapter(Context context) {
        super(context, R.layout.item_withdrawalsrecordlayout);
    }

    WDetailAdapter wDetailAdapter;

    @Override
    protected void convert(BaseViewHolder viewHoder, WithdrawalsrecordBean item) {
        AppCompatTextView tvMessage = viewHoder.getAppCompatTextView(R.id.tv_message);
        if (!Judge.getBoolean_isNull(item.getMouthSum())) {
            tvMessage.setText(item.getCreateTime() + "  总共提现" + item.getMouthSum() + "元");
        } else {
            tvMessage.setText(item.getCreateTime() + "  总共提现0元");
        }

        RecyclerView recyclerView = (RecyclerView) viewHoder.getView(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        wDetailAdapter = new WDetailAdapter(context);
        recyclerView.setAdapter(wDetailAdapter);
        if (item.getPresentEntities() != null && item.getPresentEntities().size() > 0) {
            wDetailAdapter.refreshData(item.getPresentEntities());
        }

    }




    public double getAllSchievement() {
        try {
            List<WithdrawalsrecordBean> list = getDatas();
            double d = 0;
            for (int i = 0; i < list.size(); i++) {
                d = d + Double.valueOf(list.get(i).getMouthSum());
            }
            return d;
        } catch (Exception e) {
            return 0;
        }
    }

}
