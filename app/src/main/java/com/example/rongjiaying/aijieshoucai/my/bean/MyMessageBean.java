package com.example.rongjiaying.aijieshoucai.my.bean;

/**
 * 消息管理
 */
public class MyMessageBean {
    /**
     * id : 20181229230807
     * title : 测试标题
     * memo : 改标题的内容
     * userId : 20181224141016
     */
private String createTime="";
    private String id;
    private String title;
    private String memo;
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "MyMessageBean{" +
                "createTime='" + createTime + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", memo='" + memo + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
