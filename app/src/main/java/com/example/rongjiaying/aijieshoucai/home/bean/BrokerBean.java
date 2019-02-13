package com.example.rongjiaying.aijieshoucai.home.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 经纪人
 */
public class BrokerBean implements Parcelable {

    /**
     * userId : 20181224141016
     * username : 赵慧敏
     * phone : 18588857701
     * password : 18588857701
     * createTime : Dec 24, 2018 2:10:16 PM
     * level : 7
     * icon : user1.jpg
     * moneyG : 848.0
     * moneyM : 3012.0
     * myIntegral : 50000
     * bankCard : 32
     * directUserId : 32
     * indirectUserId : 32
     * areaName : 32
     * moveCode : eec27b26727845a59322
     * offlineHonoraryRecommendation : 0
     * size : 29
     * user_detail_approve : 6
     * user_detail_incom : 12
     * user_detail_loan : 3
     * user_detail_turned_down : 0
     * month_commission : 0
     * my_incom : 0
     * my_approve : 0
     * my_loan : 0
     * this_month_incom : 0
     * this_month_approve : 0
     * this_month_loan : 0
     * m_to_g_rate : 0
     * mToGPersent : 0
     * sum : 0
     */

    private String userId;
    private String username;
    private String phone;
    private String password;
    private String createTime;
    private String level;
    private String icon;
    private String moneyG;
    private String moneyM;
    private String myIntegral;
    private String bankCard;
    private String directUserId;
    private String indirectUserId;
    private String areaName;
    private String moveCode;
    private String offlineHonoraryRecommendation;
    private int size;
    private int user_detail_approve;
    private int user_detail_incom;
    private int user_detail_loan;
    private int user_detail_turned_down;
    private int month_commission;
    private int my_incom;
    private int my_approve;
    private int my_loan;
    private int this_month_incom;
    private int this_month_approve;
    private int this_month_loan;
    private int m_to_g_rate;
    private int mToGPersent;
    private int sum;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMoneyG() {
        return moneyG;
    }

    public void setMoneyG(String moneyG) {
        this.moneyG = moneyG;
    }

    public String getMoneyM() {
        return moneyM;
    }

    public void setMoneyM(String moneyM) {
        this.moneyM = moneyM;
    }

    public String getMyIntegral() {
        return myIntegral;
    }

    public void setMyIntegral(String myIntegral) {
        this.myIntegral = myIntegral;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getDirectUserId() {
        return directUserId;
    }

    public void setDirectUserId(String directUserId) {
        this.directUserId = directUserId;
    }

    public String getIndirectUserId() {
        return indirectUserId;
    }

    public void setIndirectUserId(String indirectUserId) {
        this.indirectUserId = indirectUserId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getMoveCode() {
        return moveCode;
    }

    public void setMoveCode(String moveCode) {
        this.moveCode = moveCode;
    }

    public String getOfflineHonoraryRecommendation() {
        return offlineHonoraryRecommendation;
    }

    public void setOfflineHonoraryRecommendation(String offlineHonoraryRecommendation) {
        this.offlineHonoraryRecommendation = offlineHonoraryRecommendation;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getUser_detail_approve() {
        return user_detail_approve;
    }

    public void setUser_detail_approve(int user_detail_approve) {
        this.user_detail_approve = user_detail_approve;
    }

    public int getUser_detail_incom() {
        return user_detail_incom;
    }

    public void setUser_detail_incom(int user_detail_incom) {
        this.user_detail_incom = user_detail_incom;
    }

    public int getUser_detail_loan() {
        return user_detail_loan;
    }

    public void setUser_detail_loan(int user_detail_loan) {
        this.user_detail_loan = user_detail_loan;
    }

    public int getUser_detail_turned_down() {
        return user_detail_turned_down;
    }

    public void setUser_detail_turned_down(int user_detail_turned_down) {
        this.user_detail_turned_down = user_detail_turned_down;
    }

    public int getMonth_commission() {
        return month_commission;
    }

    public void setMonth_commission(int month_commission) {
        this.month_commission = month_commission;
    }

    public int getMy_incom() {
        return my_incom;
    }

    public void setMy_incom(int my_incom) {
        this.my_incom = my_incom;
    }

    public int getMy_approve() {
        return my_approve;
    }

    public void setMy_approve(int my_approve) {
        this.my_approve = my_approve;
    }

    public int getMy_loan() {
        return my_loan;
    }

    public void setMy_loan(int my_loan) {
        this.my_loan = my_loan;
    }

    public int getThis_month_incom() {
        return this_month_incom;
    }

    public void setThis_month_incom(int this_month_incom) {
        this.this_month_incom = this_month_incom;
    }

    public int getThis_month_approve() {
        return this_month_approve;
    }

    public void setThis_month_approve(int this_month_approve) {
        this.this_month_approve = this_month_approve;
    }

    public int getThis_month_loan() {
        return this_month_loan;
    }

    public void setThis_month_loan(int this_month_loan) {
        this.this_month_loan = this_month_loan;
    }

    public int getM_to_g_rate() {
        return m_to_g_rate;
    }

    public void setM_to_g_rate(int m_to_g_rate) {
        this.m_to_g_rate = m_to_g_rate;
    }

    public int getMToGPersent() {
        return mToGPersent;
    }

    public void setMToGPersent(int mToGPersent) {
        this.mToGPersent = mToGPersent;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.username);
        dest.writeString(this.phone);
        dest.writeString(this.password);
        dest.writeString(this.createTime);
        dest.writeString(this.level);
        dest.writeString(this.icon);
        dest.writeString(this.moneyG);
        dest.writeString(this.moneyM);
        dest.writeString(this.myIntegral);
        dest.writeString(this.bankCard);
        dest.writeString(this.directUserId);
        dest.writeString(this.indirectUserId);
        dest.writeString(this.areaName);
        dest.writeString(this.moveCode);
        dest.writeString(this.offlineHonoraryRecommendation);
        dest.writeInt(this.size);
        dest.writeInt(this.user_detail_approve);
        dest.writeInt(this.user_detail_incom);
        dest.writeInt(this.user_detail_loan);
        dest.writeInt(this.user_detail_turned_down);
        dest.writeInt(this.month_commission);
        dest.writeInt(this.my_incom);
        dest.writeInt(this.my_approve);
        dest.writeInt(this.my_loan);
        dest.writeInt(this.this_month_incom);
        dest.writeInt(this.this_month_approve);
        dest.writeInt(this.this_month_loan);
        dest.writeInt(this.m_to_g_rate);
        dest.writeInt(this.mToGPersent);
        dest.writeInt(this.sum);
    }

    public BrokerBean() {
    }

    protected BrokerBean(Parcel in) {
        this.userId = in.readString();
        this.username = in.readString();
        this.phone = in.readString();
        this.password = in.readString();
        this.createTime = in.readString();
        this.level = in.readString();
        this.icon = in.readString();
        this.moneyG = in.readString();
        this.moneyM = in.readString();
        this.myIntegral = in.readString();
        this.bankCard = in.readString();
        this.directUserId = in.readString();
        this.indirectUserId = in.readString();
        this.areaName = in.readString();
        this.moveCode = in.readString();
        this.offlineHonoraryRecommendation = in.readString();
        this.size = in.readInt();
        this.user_detail_approve = in.readInt();
        this.user_detail_incom = in.readInt();
        this.user_detail_loan = in.readInt();
        this.user_detail_turned_down = in.readInt();
        this.month_commission = in.readInt();
        this.my_incom = in.readInt();
        this.my_approve = in.readInt();
        this.my_loan = in.readInt();
        this.this_month_incom = in.readInt();
        this.this_month_approve = in.readInt();
        this.this_month_loan = in.readInt();
        this.m_to_g_rate = in.readInt();
        this.mToGPersent = in.readInt();
        this.sum = in.readInt();
    }

    public static final Creator<BrokerBean> CREATOR = new Creator<BrokerBean>() {
        @Override
        public BrokerBean createFromParcel(Parcel source) {
            return new BrokerBean(source);
        }

        @Override
        public BrokerBean[] newArray(int size) {
            return new BrokerBean[size];
        }
    };
}
