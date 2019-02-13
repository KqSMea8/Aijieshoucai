package com.example.rongjiaying.aijieshoucai.order.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseViewHolder;
import com.example.rongjiaying.aijieshoucai.baseadapter.SimpleAdapter;
import com.example.rongjiaying.aijieshoucai.order.bean.OrderSingningBean;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.fang.dashview.DashView;

import java.util.List;

public class OrderSigningAdapter extends SimpleAdapter<OrderSingningBean> {
    public OrderSigningAdapter(Context context) {
        super(context, R.layout.item_ordersigninglayout);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, OrderSingningBean item) {
        View ivOval = viewHoder.getView(R.id.iv_oval);
        AppCompatTextView tvTitle = viewHoder.getAppCompatTextView(R.id.tv_title);
        DashView dashViewcheck = (DashView) viewHoder.getView(R.id.dashview_check);
        DashView dashViewnotcheck = (DashView) viewHoder.getView(R.id.dashview_nocheck);
        if (!Judge.getBoolean_isNull(item.getProcess())) {
            tvTitle.setText(item.getProcess());
        }

        if (item.isCheck()) {
            tvTitle.setTextColor(context.getResources().getColor(R.color.color_178C68));
            ivOval.setBackgroundResource(R.drawable.oval_178c68_8dp);
            dashViewcheck.setVisibility(View.VISIBLE);
            dashViewnotcheck.setVisibility(View.GONE);
        } else {
            ivOval.setBackgroundResource(R.drawable.oval_bababa_8dp);
            dashViewcheck.setVisibility(View.GONE);
            dashViewnotcheck.setVisibility(View.VISIBLE);
            tvTitle.setTextColor(context.getResources().getColor(R.color.color_bababa));
        }
        if (position == (getDatas().size() - 1)) {
            dashViewcheck.setVisibility(View.GONE);
            dashViewnotcheck.setVisibility(View.GONE);
        }


    }

    public void setStatusCheck(int backStatus) {
        List<OrderSingningBean> list = getDatas();
        boolean allcheck = false;
        OrderSingningBean item=null;
        //检查所有
        for (int i = 0; i < list.size(); i++) {
            if (backStatus == list.get(i).getCode()) {
                allcheck = true;
                item=list.get(i);
            } else {

            }

        }
        if (!allcheck) {
            setAllCheck(false);
        } else {
            int position=getDatas().indexOf(item);
          if (position!=-1)
          {
              for (int j=0;j<getDatas().size();j++)
              {
                  if (j<=position)
                  {
                      getDatas().get(j).setCheck(true);
                  }else {
                      getDatas().get(j).setCheck(false);
                  }
              }


          }else {
              setAllCheck(false);
          }

        }

        notifyDataSetChanged();
    }

    public void setAllCheck(boolean check) {
        for (int i = 0; i < getDatas().size(); i++) {
            getDatas().get(i).setCheck(check);
        }
    }
}
