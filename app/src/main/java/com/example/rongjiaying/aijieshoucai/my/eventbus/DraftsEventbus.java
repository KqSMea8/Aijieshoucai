package com.example.rongjiaying.aijieshoucai.my.eventbus;

import com.example.rongjiaying.aijieshoucai.order.bean.OrderListBean;

public class DraftsEventbus {
    private OrderListBean orderListBean;
    private int deleorEdit = 2;//dele  1  edit 2



    public DraftsEventbus(OrderListBean orderListBean, int deleorEdit) {
        this.orderListBean = orderListBean;
        this.deleorEdit = deleorEdit;
    }

    public OrderListBean getOrderListBean() {
        return orderListBean;
    }

    public void setOrderListBean(OrderListBean orderListBean) {
        this.orderListBean = orderListBean;
    }

    public int getDeleorEdit() {
        return deleorEdit;
    }

    public void setDeleorEdit(int deleorEdit) {
        this.deleorEdit = deleorEdit;
    }
}
