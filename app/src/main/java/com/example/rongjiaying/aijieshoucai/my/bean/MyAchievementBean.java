package com.example.rongjiaying.aijieshoucai.my.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 我的业绩 bean
 */
public class MyAchievementBean implements Parcelable {


    /**
     * userId : 20181224141016
     * username : 容嘉杰程序员
     * phone : 15088139471
     * password : 123456
     * createTime : Dec 24, 2018 2:10:16 PM
     * level : 1
     * icon : 20190101140613usericon.png
     * moneyG : 120
     * moneyM : 5000
     * myIntegral : 6000
     * bankCard : 32
     * directUserId : 32
     * indirectUserId : 32
     * areaName : 32
     * moveCode : 32
     * offlineHonoraryRecommendation : 0
     * user_detail_approve : 0
     * user_detail_incom : 0
     * user_detail_loan : 0
     * user_detail_turned_down : 0
     * month_commission : 0
     * my_incom : 9
     * my_approve : 4
     * my_loan : 0
     * this_month_incom : 0
     * this_month_approve : 0
     * this_month_loan : 0
     * m_to_g_rate : 1
     * mToGPersent : 0.2
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
    private double m_to_g_rate;
    private double mToGPersent;

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

    public double getM_to_g_rate() {
        return m_to_g_rate;
    }

    public void setM_to_g_rate(double m_to_g_rate) {
        this.m_to_g_rate = m_to_g_rate;
    }

    public double getMToGPersent() {
        return mToGPersent;
    }

    public void setMToGPersent(double mToGPersent) {
        this.mToGPersent = mToGPersent;
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
        dest.writeDouble(this.m_to_g_rate);
        dest.writeDouble(this.mToGPersent);
    }

    public MyAchievementBean() {
    }

    protected MyAchievementBean(Parcel in) {
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
        this.m_to_g_rate = in.readDouble();
        this.mToGPersent = in.readDouble();
    }

    public static final Parcelable.Creator<MyAchievementBean> CREATOR = new Parcelable.Creator<MyAchievementBean>() {
        @Override
        public MyAchievementBean createFromParcel(Parcel source) {
            return new MyAchievementBean(source);
        }

        @Override
        public MyAchievementBean[] newArray(int size) {
            return new MyAchievementBean[size];
        }
    };
}
