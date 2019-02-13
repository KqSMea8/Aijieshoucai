package com.example.rongjiaying.aijieshoucai.order.eventbus;

import com.example.rongjiaying.aijieshoucai.order.bean.OrderListBean;

/**
 *
 */
public class OrderListItemDeleEventbus {
    OrderListBean orderListBean;

    public OrderListItemDeleEventbus(OrderListBean orderListBean) {
        this.orderListBean = orderListBean;
    }

    public OrderListBean getOrderListBean() {
        return orderListBean;
    }

    public void setOrderListBean(OrderListBean orderListBean) {
        this.orderListBean = orderListBean;
    }
}
