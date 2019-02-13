package com.example.rongjiaying.aijieshoucai.my.bean;

import java.util.List;

/**
 * 提现记录 bean
 */
public class WithdrawalsrecordBean {
    /**
     * createTime : 2019-58-02  14:58:07
     * mouthSum : 600
     */

    private String createTime;
    private String mouthSum;
private List<WithdrawalsrecordDetailListBean>presentEntities;
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMouthSum() {
        return mouthSum;
    }

    public void setMouthSum(String mouthSum) {
        this.mouthSum = mouthSum;
    }


    public List<WithdrawalsrecordDetailListBean> getPresentEntities() {
        return presentEntities;
    }

    public void setPresentEntities(List<WithdrawalsrecordDetailListBean> presentEntities) {
        this.presentEntities = presentEntities;
    }
}
