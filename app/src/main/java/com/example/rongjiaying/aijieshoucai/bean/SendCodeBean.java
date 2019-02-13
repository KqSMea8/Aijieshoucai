package com.example.rongjiaying.aijieshoucai.bean;

/**
 * 本地判断验证码
 */
public class SendCodeBean {
    String code = "";
    long time = 0;
    String phone = "";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "SendCodeBean{" +
                "code='" + code + '\'' +
                ", time=" + time +
                ", phone='" + phone + '\'' +
                '}';
    }
}
