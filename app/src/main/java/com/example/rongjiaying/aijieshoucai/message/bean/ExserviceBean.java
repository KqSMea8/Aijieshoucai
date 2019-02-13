package com.example.rongjiaying.aijieshoucai.message.bean;

import com.example.rongjiaying.aijieshoucai.my.bean.CommentQuestionListBean;

import java.util.List;

/**
 * 聊天机器人 bean 与自己对话的bean
 */
public class ExserviceBean {
    private List<CommentQuestionListBean> commentQuestionListBeans;
    int type = -1;//0问题列表  1机器人  2自己说话
    String message = "";
    String icon = "";
long createTime=0;

    public List<CommentQuestionListBean> getCommentQuestionListBeans() {
        return commentQuestionListBeans;
    }

    public void setCommentQuestionListBeans(List<CommentQuestionListBean> commentQuestionListBeans) {
        this.commentQuestionListBeans = commentQuestionListBeans;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ExserviceBean{" +
                "commentQuestionListBeans=" + commentQuestionListBeans +
                ", type=" + type +
                ", message='" + message + '\'' +
                ", icon='" + icon + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
