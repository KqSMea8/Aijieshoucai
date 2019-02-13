package com.example.rongjiaying.aijieshoucai.interfac;

import com.example.rongjiaying.aijieshoucai.my.bean.CommentQuestionListBean;

/**
 * 联系客服 问题
 */
public interface OnExserviceQuestionItemListener {
    void onExserviceQuestion(CommentQuestionListBean commentQuestionListBean);
    void onCheckQuestion();//换一换问题
}
