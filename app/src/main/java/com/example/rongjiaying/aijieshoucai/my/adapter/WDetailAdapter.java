package com.example.rongjiaying.aijieshoucai.my.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseViewHolder;
import com.example.rongjiaying.aijieshoucai.baseadapter.SimpleAdapter;
import com.example.rongjiaying.aijieshoucai.my.bean.WithdrawalsrecordDetailListBean;
import com.example.rongjiaying.aijieshoucai.util.Judge;

public class WDetailAdapter extends SimpleAdapter<WithdrawalsrecordDetailListBean> {

    public WDetailAdapter(Context context) {
        super(context, R.layout.item_wdetaillayout);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, WithdrawalsrecordDetailListBean item) {
        AppCompatTextView tvWithdrawMenth = viewHoder.getAppCompatTextView(R.id.tv_withdrawMenth);
        tvWithdrawMenth.setText("+" + (Double.valueOf(item.getPresentG()) + (Double.valueOf(item.getPresentM()) * Double.valueOf(item.getMChangeGRate()))));
        AppCompatTextView tvTime = viewHoder.getAppCompatTextView(R.id.tv_time);
        AppCompatTextView tvStatus = viewHoder.getAppCompatTextView(R.id.tv_status);
        AppCompatTextView tvCheck = viewHoder.getAppCompatTextView(R.id.tv_check);
        final LinearLayout llcheck = (LinearLayout) viewHoder.getView(R.id.ll_check);
        AppCompatTextView tvOrderId = viewHoder.getAppCompatTextView(R.id.tv_orderid);
        llcheck.setVisibility(View.GONE);

        AppCompatImageView iv_check_view1 = viewHoder.getAppCompatImageView(R.id.iv_check_view1);
        AppCompatImageView iv_check_view2 = viewHoder.getAppCompatImageView(R.id.iv_check_view2);
        AppCompatImageView iv_check_view3 = viewHoder.getAppCompatImageView(R.id.iv_check_view3);


        View view1 = viewHoder.getView(R.id.view_check_view1);
        View view2 = viewHoder.getView(R.id.view_check_view2);
        View view3 = viewHoder.getView(R.id.view_check_view3);
        View view4 = viewHoder.getView(R.id.view_check_view4);

        AppCompatTextView tvReaseon = viewHoder.getAppCompatTextView(R.id.tv_reason);//失败原因

        if (!Judge.getBoolean_isNull(item.getId())) {
            tvOrderId.setText(item.getId());
        }
        tvTime.setText(item.getCreateTime());
        if (item.getPresentProgress().equals("0")) {
            viewHoder.getView(R.id.ll_status01).setVisibility(View.VISIBLE);
            viewHoder.getView(R.id.ll_errlayout).setVisibility(View.GONE);
            tvStatus.setText("申请中");
            iv_check_view1.setImageResource(R.drawable.icon_done);
            iv_check_view2.setImageResource(R.drawable.oval_bababa_8dp);
            iv_check_view3.setImageResource(R.drawable.oval_bababa_8dp);

            view1.setBackgroundColor(context.getResources().getColor(R.color.color_257ace));
            view2.setBackgroundColor(context.getResources().getColor(R.color.color_bababa));
            view3.setBackgroundColor(context.getResources().getColor(R.color.color_bababa));
            view4.setBackgroundColor(context.getResources().getColor(R.color.color_bababa));
        } else if (item.getPresentProgress().equals("1")) {
            tvStatus.setText("审核中");
            viewHoder.getView(R.id.ll_status01).setVisibility(View.VISIBLE);
            viewHoder.getView(R.id.ll_errlayout).setVisibility(View.GONE);

            iv_check_view1.setImageResource(R.drawable.icon_done);
            iv_check_view2.setImageResource(R.drawable.icon_done);
            iv_check_view3.setImageResource(R.drawable.oval_bababa_8dp);

            view1.setBackgroundColor(context.getResources().getColor(R.color.color_257ace));
            view2.setBackgroundColor(context.getResources().getColor(R.color.color_257ace));
            view3.setBackgroundColor(context.getResources().getColor(R.color.color_257ace));
            view4.setBackgroundColor(context.getResources().getColor(R.color.color_bababa));
        } else if (item.getPresentProgress().equals("2")) {
            tvStatus.setText("已到账");
            viewHoder.getView(R.id.ll_status01).setVisibility(View.VISIBLE);
            viewHoder.getView(R.id.ll_errlayout).setVisibility(View.GONE);
            iv_check_view1.setImageResource(R.drawable.icon_done);
            iv_check_view2.setImageResource(R.drawable.icon_done);
            iv_check_view3.setImageResource(R.drawable.icon_done);

            view1.setBackgroundColor(context.getResources().getColor(R.color.color_257ace));
            view2.setBackgroundColor(context.getResources().getColor(R.color.color_257ace));
            view3.setBackgroundColor(context.getResources().getColor(R.color.color_257ace));
            view4.setBackgroundColor(context.getResources().getColor(R.color.color_257ace));
        } else if (item.getPresentProgress().equals("3")) {
            tvStatus.setText("到账失败");
            viewHoder.getView(R.id.ll_status01).setVisibility(View.GONE);
            viewHoder.getView(R.id.ll_errlayout).setVisibility(View.VISIBLE);
            tvReaseon.setText(item.getFailureReason());
        } else {
            tvStatus.setText("");
            viewHoder.getView(R.id.ll_status01).setVisibility(View.GONE);
            viewHoder.getView(R.id.ll_errlayout).setVisibility(View.GONE);
        }
        AppCompatTextView tvWthdrawmenth = viewHoder.getAppCompatTextView(R.id.tv_withdrawmenth);
        if (!Judge.getBoolean_isNull(item.getBankId()) && item.getBankId().length() > 4)

        {
            tvWthdrawmenth.setText(item.getWithdrawMenth() + "(" + item.getBankId().substring(item.getBankId().length() - 4, item.getBankId().length()) + ")");
        } else {
            tvWthdrawmenth.setText(item.getWithdrawMenth() + "(" + 0 + ")");
        }
final AppCompatImageView ivCheck=viewHoder.getAppCompatImageView(R.id.iv_check);
        viewHoder.getView(R.id.ll_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llcheck.getVisibility() == View.GONE) {
                    llcheck.setVisibility(View.VISIBLE);
                    ivCheck.setImageResource(R.drawable.icon_up);
                } else {
                    llcheck.setVisibility(View.GONE);
                    ivCheck.setImageResource(R.drawable.icon_down);
                }

            }
        });

    }
}