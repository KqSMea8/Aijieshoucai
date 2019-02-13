package com.example.rongjiaying.aijieshoucai.my.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseViewHolder;
import com.example.rongjiaying.aijieshoucai.baseadapter.SimpleAdapter;
import com.example.rongjiaying.aijieshoucai.interfac.OnMyBankItemListener;
import com.example.rongjiaying.aijieshoucai.my.bean.MyBankListBean;

import java.util.List;

public class MyBankAdapter extends SimpleAdapter<MyBankListBean> {


    public MyBankAdapter(Context context) {
        super(context, R.layout.item_mybanklayout);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MyBankListBean item) {
        AppCompatImageView ivBank=helper.getAppCompatImageView(R.id.iv_bank);
        AppCompatTextView tvBankname = helper.getAppCompatTextView(R.id.tv_bankname);
        AppCompatTextView tvMessage = helper.getAppCompatTextView(R.id.tv_message);
        AppCompatTextView tvDel = helper.getAppCompatTextView(R.id.tv_del);
        AppCompatTextView tvDefault = helper.getAppCompatTextView(R.id.tv_defaule);

        if (item.getLevel().equals("1")) {
            tvDefault.setText("默认银行卡");
        } else {
            tvDefault.setText("设置为默认");
        }
        tvBankname.setText(item.getBankName());
        tvMessage.setText("尾号" + getMessage(item.getBankCode()) + "储蓄卡");

        tvDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMyBankItemListener != null) {
                    onMyBankItemListener.onDefaultItem(item);
                }
            }
        });

        tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMyBankItemListener != null) {
                    onMyBankItemListener.onDelItem(item);
                }
            }
        });

        if (item.getBankName().equals("广发银行"))
        {
            ivBank.setImageResource(R.drawable.icon_bank_guangfa);
        }else if (item.getBankName().equals("光大银行"))
        {
            ivBank.setImageResource(R.drawable.icon_bank_guangda);
        }
        else if (item.getBankName().equals("华夏银行"))
        {
            ivBank.setImageResource(R.drawable.icon_bank_huaxia);
        }
        else if (item.getBankName().equals("华兴银行"))
        {
            ivBank.setImageResource(R.drawable.icon_bank_huaxing);
        }else if (item.getBankName().equals("建设银行"))
        {
            ivBank.setImageResource(R.drawable.icon_bank_jianshe);
        }else if (item.getBankName().equals("交通银行"))
        {
            ivBank.setImageResource(R.drawable.icon_bank_jiaotong);
        }else if (item.getBankName().equals("民生银行"))
        {
            ivBank.setImageResource(R.drawable.icon_bank_minsheng);
        }else if (item.getBankName().equals("农业银行"))
        {
            ivBank.setImageResource(R.drawable.icon_bank_nongye);
        }else if (item.getBankName().equals("平安银行"))
        {
            ivBank.setImageResource(R.drawable.icon_bank_pingan);
        }else if (item.getBankName().equals("浦发银行"))
        {
            ivBank.setImageResource(R.drawable.icon_bank_pufa);
        }else if (item.getBankName().equals("兴业银行"))
        {
            ivBank.setImageResource(R.drawable.icon_bank_xingye);
        }else if (item.getBankName().equals("邮政银行"))
        {
            ivBank.setImageResource(R.drawable.icon_bank_youzheng);
        }else if (item.getBankName().equals("招商银行"))
        {
            ivBank.setImageResource(R.drawable.icon_bank_zhaoshang);
        }
        else if (item.getBankName().equals("中国银行"))
        {
            ivBank.setImageResource(R.drawable.icon_bank_zhongguo);
        }
        else if (item.getBankName().equals("中信银行"))
        {
            ivBank.setImageResource(R.drawable.icon_bank_zhongxin);
        }
        else if (item.getBankName().equals("招商银行"))
        {
            ivBank.setImageResource(R.drawable.icon_bank_zhaoshang);
        }
        else if (item.getBankName().equals("招商银行"))
        {
            ivBank.setImageResource(R.drawable.icon_bank_zhaoshang);
        }

    }

    private String getMessage(String bankCode) {
        if (bankCode.length() > 4) {
            return bankCode.substring(bankCode.length() - 4, bankCode.length());
        } else {
            return "";
        }
    }

    public void setOnMyBankItemListener(OnMyBankItemListener onMyBankItemListener) {
        this.onMyBankItemListener = onMyBankItemListener;
    }

    OnMyBankItemListener onMyBankItemListener;


    public String getDefaultId() {
        List<MyBankListBean> myBankListBeans = getDatas();
        MyBankListBean myBankListBean = null;
        for (int i = 0; i < myBankListBeans.size(); i++) {
            if (myBankListBeans.get(i).getLevel().equals("1")) {
                myBankListBean = myBankListBeans.get(i);
            }
        }
        if (myBankListBean != null) {
            return myBankListBean.getId();
        } else {
            return "";
        }

    }
}
