package com.example.rongjiaying.aijieshoucai.interfac;

import com.example.rongjiaying.aijieshoucai.my.bean.MyBankListBean;

/**
 * 我的银行卡
 */
public interface OnMyBankItemListener {
    void onDefaultItem(MyBankListBean myBankListBean);

    void onDelItem(MyBankListBean myBankListBean);
}
