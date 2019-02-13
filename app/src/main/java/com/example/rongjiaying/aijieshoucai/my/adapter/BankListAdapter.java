package com.example.rongjiaying.aijieshoucai.my.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseViewHolder;
import com.example.rongjiaying.aijieshoucai.baseadapter.SimpleAdapter;
import com.example.rongjiaying.aijieshoucai.my.bean.BankListBean;

/**
 * 银行
 */
public class BankListAdapter extends SimpleAdapter<BankListBean> {
    public BankListAdapter(Context context) {
        super(context, R.layout.item_banklistlayout);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, BankListBean item) {
        AppCompatTextView tvName = viewHoder.getAppCompatTextView(R.id.tv_bankname);
        AppCompatImageView ivIcon = viewHoder.getAppCompatImageView(R.id.iv_icon);
        tvName.setText(item.getResult());
        if (item.getResult().equals("广发银行"))
        {
            ivIcon.setImageResource(R.drawable.icon_bank_guangfa);
        }else if (item.getResult().equals("光大银行"))
        {
            ivIcon.setImageResource(R.drawable.icon_bank_guangda);
        }
        else if (item.getResult().equals("华夏银行"))
        {
            ivIcon.setImageResource(R.drawable.icon_bank_huaxia);
        }
        else if (item.getResult().equals("华兴银行"))
        {
            ivIcon.setImageResource(R.drawable.icon_bank_huaxing);
        }else if (item.getResult().equals("建设银行"))
        {
            ivIcon.setImageResource(R.drawable.icon_bank_jianshe);
        }else if (item.getResult().equals("交通银行"))
        {
            ivIcon.setImageResource(R.drawable.icon_bank_jiaotong);
        }else if (item.getResult().equals("民生银行"))
        {
            ivIcon.setImageResource(R.drawable.icon_bank_minsheng);
        }else if (item.getResult().equals("农业银行"))
        {
            ivIcon.setImageResource(R.drawable.icon_bank_nongye);
        }else if (item.getResult().equals("平安银行"))
        {
            ivIcon.setImageResource(R.drawable.icon_bank_pingan);
        }else if (item.getResult().equals("浦发银行"))
        {
            ivIcon.setImageResource(R.drawable.icon_bank_pufa);
        }else if (item.getResult().equals("兴业银行"))
        {
            ivIcon.setImageResource(R.drawable.icon_bank_xingye);
        }else if (item.getResult().equals("邮政银行"))
        {
            ivIcon.setImageResource(R.drawable.icon_bank_youzheng);
        }else if (item.getResult().equals("招商银行"))
        {
            ivIcon.setImageResource(R.drawable.icon_bank_zhaoshang);
        }
        else if (item.getResult().equals("中国银行"))
        {
            ivIcon.setImageResource(R.drawable.icon_bank_zhongguo);
        }
        else if (item.getResult().equals("中信银行"))
        {
            ivIcon.setImageResource(R.drawable.icon_bank_zhongxin);
        }
        else if (item.getResult().equals("招商银行"))
        {
            ivIcon.setImageResource(R.drawable.icon_bank_zhaoshang);
        }
        else if (item.getResult().equals("招商银行"))
        {
            ivIcon.setImageResource(R.drawable.icon_bank_zhaoshang);
        }

    }


}
