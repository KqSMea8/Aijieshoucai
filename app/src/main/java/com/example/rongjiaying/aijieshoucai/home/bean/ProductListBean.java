package com.example.rongjiaying.aijieshoucai.home.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 商品列表 bean
 */
public class ProductListBean implements Parcelable {
    /**
     * id : 1
     * name : 有车贷
     * path : business_1.png
     * detail : welcom1.jpg
     */
String memo="";
    private int id;
    private String name;
    private String path;
    private String detail;
    String  expectMonthInterest="";
    String maxLoan="";


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.memo);
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.path);
        dest.writeString(this.detail);
        dest.writeString(this.expectMonthInterest);
        dest.writeString(this.maxLoan);
    }

    public ProductListBean() {
    }

    protected ProductListBean(Parcel in) {
        this.memo = in.readString();
        this.id = in.readInt();
        this.name = in.readString();
        this.path = in.readString();
        this.detail = in.readString();
        this.expectMonthInterest = in.readString();
        this.maxLoan = in.readString();
    }

    public static final Creator<ProductListBean> CREATOR = new Creator<ProductListBean>() {
        @Override
        public ProductListBean createFromParcel(Parcel source) {
            return new ProductListBean(source);
        }

        @Override
        public ProductListBean[] newArray(int size) {
            return new ProductListBean[size];
        }
    };

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getExpectMonthInterest() {
        return expectMonthInterest;
    }

    public void setExpectMonthInterest(String expectMonthInterest) {
        this.expectMonthInterest = expectMonthInterest;
    }

    public String getMaxLoan() {
        return maxLoan;
    }

    public void setMaxLoan(String maxLoan) {
        this.maxLoan = maxLoan;
    }
}
