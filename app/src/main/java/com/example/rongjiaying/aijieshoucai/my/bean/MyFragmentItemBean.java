package com.example.rongjiaying.aijieshoucai.my.bean;

/**
 * 我的  item
 */
public class MyFragmentItemBean {
    private int id;
    private String title = "";
    private int drawableRes = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrawableRes() {
        return drawableRes;
    }

    public void setDrawableRes(int drawableRes) {
        this.drawableRes = drawableRes;
    }

    @Override
    public String toString() {
        return "MyFragmentItemBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", drawableRes=" + drawableRes +
                '}';
    }
}
