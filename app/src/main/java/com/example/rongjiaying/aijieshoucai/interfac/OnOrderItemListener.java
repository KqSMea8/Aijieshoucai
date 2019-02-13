package com.example.rongjiaying.aijieshoucai.interfac;

import com.example.rongjiaying.aijieshoucai.order.bean.OrderListBean;

/**
 * 订单列表
 */
public interface OnOrderItemListener {
    void checkMessageFirstTral(OrderListBean orderListBean);//初审补资料

    void checkMessageLastInstance(OrderListBean orderListBean);//终审审补资料

    void checkMessageRefusal(OrderListBean orderListBean);//拒批详情

    void checkMessageSigning(OrderListBean orderListBean);
}
