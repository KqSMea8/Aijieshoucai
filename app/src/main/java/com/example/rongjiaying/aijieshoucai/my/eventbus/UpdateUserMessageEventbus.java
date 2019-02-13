package com.example.rongjiaying.aijieshoucai.my.eventbus;

import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;

/***
 * 更新用户信息
 */
public class UpdateUserMessageEventbus {
    private LoginBean loginBean;

    public UpdateUserMessageEventbus(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }
}
