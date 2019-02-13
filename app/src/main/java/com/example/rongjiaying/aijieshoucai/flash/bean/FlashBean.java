package com.example.rongjiaying.aijieshoucai.flash.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class FlashBean implements Parcelable {
    /**
     * id : 1
     * flag : welcome_images
     * result : welcom1.jpg
     * sort : 1
     */

    private int id;
    private String flag;
    private String result;
    private int sort;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.flag);
        dest.writeString(this.result);
        dest.writeInt(this.sort);
    }

    public FlashBean() {
    }

    protected FlashBean(Parcel in) {
        this.id = in.readInt();
        this.flag = in.readString();
        this.result = in.readString();
        this.sort = in.readInt();
    }

    public static final Parcelable.Creator<FlashBean> CREATOR = new Parcelable.Creator<FlashBean>() {
        @Override
        public FlashBean createFromParcel(Parcel source) {
            return new FlashBean(source);
        }

        @Override
        public FlashBean[] newArray(int size) {
            return new FlashBean[size];
        }
    };
}
