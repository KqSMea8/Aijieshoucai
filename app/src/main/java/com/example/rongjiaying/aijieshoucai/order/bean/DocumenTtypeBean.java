package com.example.rongjiaying.aijieshoucai.order.bean;

/**
 * 证件类型
 */
public class DocumenTtypeBean {
    String title = "";
    int id = 0;
    boolean ischeck = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    @Override
    public String toString() {
        return "DocumenTtypeBean{" +
                "title='" + title + '\'' +
                ", id=" + id +
                ", ischeck=" + ischeck +
                '}';
    }
}
