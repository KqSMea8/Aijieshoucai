package com.example.rongjiaying.aijieshoucai.order.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderListBean implements Parcelable {


    /**
     * orderCode : 20181227101643
     * borrowerName : 测试姓名1
     * borrowerId : 20181227101643
     * borrowerPhone : 15088139471
     * driviceLincern : 20181227101642drivice_lincence.png
     * cardFront : 20181227101642card_front.png
     * cardBack : 20181227101642card_back.png
     * status : 5
     * backStatus : 5
     * drvicerLincenceBack : 太模糊
     * cardFrontBack : 太模糊
     * cardBackBack : 太模糊
     * userId : 20181224141016
     * applyProduce : 1
     */
    String produceName = "";
    String backReason = "";
    private String orderCode= "";
    private String borrowerName;
    private String borrowerId= "";
    private String borrowerPhone= "";
    private String driviceLincern= "";
    private String cardFront= "";
    private String cardBack= "";
    private int status;
    private int backStatus;
    private String drvicerLincenceBack= "";
    private String cardFrontBack= "";
    private String cardBackBack= "";
    private String userId= "";
    private String applyProduce= "";


    public String getProduceName() {
        return produceName;
    }

    public void setProduceName(String produceName) {
        this.produceName = produceName;
    }

    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }

    public String getBorrowerPhone() {
        return borrowerPhone;
    }

    public void setBorrowerPhone(String borrowerPhone) {
        this.borrowerPhone = borrowerPhone;
    }

    public String getDriviceLincern() {
        return driviceLincern;
    }

    public void setDriviceLincern(String driviceLincern) {
        this.driviceLincern = driviceLincern;
    }

    public String getCardFront() {
        return cardFront;
    }

    public void setCardFront(String cardFront) {
        this.cardFront = cardFront;
    }

    public String getCardBack() {
        return cardBack;
    }

    public void setCardBack(String cardBack) {
        this.cardBack = cardBack;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBackStatus() {
        return backStatus;
    }

    public void setBackStatus(int backStatus) {
        this.backStatus = backStatus;
    }

    public String getDrvicerLincenceBack() {
        return drvicerLincenceBack;
    }

    public void setDrvicerLincenceBack(String drvicerLincenceBack) {
        this.drvicerLincenceBack = drvicerLincenceBack;
    }

    public String getCardFrontBack() {
        return cardFrontBack;
    }

    public void setCardFrontBack(String cardFrontBack) {
        this.cardFrontBack = cardFrontBack;
    }

    public String getCardBackBack() {
        return cardBackBack;
    }

    public void setCardBackBack(String cardBackBack) {
        this.cardBackBack = cardBackBack;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApplyProduce() {
        return applyProduce;
    }

    public void setApplyProduce(String applyProduce) {
        this.applyProduce = applyProduce;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.produceName);
        dest.writeString(this.backReason);
        dest.writeString(this.orderCode);
        dest.writeString(this.borrowerName);
        dest.writeString(this.borrowerId);
        dest.writeString(this.borrowerPhone);
        dest.writeString(this.driviceLincern);
        dest.writeString(this.cardFront);
        dest.writeString(this.cardBack);
        dest.writeInt(this.status);
        dest.writeInt(this.backStatus);
        dest.writeString(this.drvicerLincenceBack);
        dest.writeString(this.cardFrontBack);
        dest.writeString(this.cardBackBack);
        dest.writeString(this.userId);
        dest.writeString(this.applyProduce);
    }

    public OrderListBean() {
    }

    protected OrderListBean(Parcel in) {
        this.produceName = in.readString();
        this.backReason = in.readString();
        this.orderCode = in.readString();
        this.borrowerName = in.readString();
        this.borrowerId = in.readString();
        this.borrowerPhone = in.readString();
        this.driviceLincern = in.readString();
        this.cardFront = in.readString();
        this.cardBack = in.readString();
        this.status = in.readInt();
        this.backStatus = in.readInt();
        this.drvicerLincenceBack = in.readString();
        this.cardFrontBack = in.readString();
        this.cardBackBack = in.readString();
        this.userId = in.readString();
        this.applyProduce = in.readString();
    }

    public static final Creator<OrderListBean> CREATOR = new Creator<OrderListBean>() {
        @Override
        public OrderListBean createFromParcel(Parcel source) {
            return new OrderListBean(source);
        }

        @Override
        public OrderListBean[] newArray(int size) {
            return new OrderListBean[size];
        }
    };

    @Override
    public String toString() {
        return "OrderListBean{" +
                "produceName='" + produceName + '\'' +
                ", backReason='" + backReason + '\'' +
                ", orderCode='" + orderCode + '\'' +
                ", borrowerName='" + borrowerName + '\'' +
                ", borrowerId='" + borrowerId + '\'' +
                ", borrowerPhone='" + borrowerPhone + '\'' +
                ", driviceLincern='" + driviceLincern + '\'' +
                ", cardFront='" + cardFront + '\'' +
                ", cardBack='" + cardBack + '\'' +
                ", status=" + status +
                ", backStatus=" + backStatus +
                ", drvicerLincenceBack='" + drvicerLincenceBack + '\'' +
                ", cardFrontBack='" + cardFrontBack + '\'' +
                ", cardBackBack='" + cardBackBack + '\'' +
                ", userId='" + userId + '\'' +
                ", applyProduce='" + applyProduce + '\'' +
                '}';
    }
}
