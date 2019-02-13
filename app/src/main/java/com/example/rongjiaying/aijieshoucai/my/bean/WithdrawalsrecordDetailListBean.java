package com.example.rongjiaying.aijieshoucai.my.bean;

/**
 * 提现记录 月份 详情
 */
public class WithdrawalsrecordDetailListBean {
    /**
     * id : 20181230125346
     * userId : 20181224141016
     * presentM : 500
     * presentG : 6
     * presentProgress : 0
     * createTime : 2018-11-06 11:16:34
     * bankId : 20181230095624
     * withdrawMenth : 工商银行
     * mChangeGRate : 1.0
     */
String failureReason="";
    private String id;
    private String userId;
    private String presentM;
    private String presentG;
    private String presentProgress;
    private String createTime;
    private String bankId;
    private String withdrawMenth;
    private String mChangeGRate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPresentM() {
        return presentM;
    }

    public void setPresentM(String presentM) {
        this.presentM = presentM;
    }

    public String getPresentG() {
        return presentG;
    }

    public void setPresentG(String presentG) {
        this.presentG = presentG;
    }

    public String getPresentProgress() {
        return presentProgress;
    }

    public void setPresentProgress(String presentProgress) {
        this.presentProgress = presentProgress;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getWithdrawMenth() {
        return withdrawMenth;
    }

    public void setWithdrawMenth(String withdrawMenth) {
        this.withdrawMenth = withdrawMenth;
    }

    public String getMChangeGRate() {
        return mChangeGRate;
    }

    public void setMChangeGRate(String mChangeGRate) {
        this.mChangeGRate = mChangeGRate;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getmChangeGRate() {
        return mChangeGRate;
    }

    public void setmChangeGRate(String mChangeGRate) {
        this.mChangeGRate = mChangeGRate;
    }
}
