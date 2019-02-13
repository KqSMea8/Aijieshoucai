package com.example.rongjiaying.aijieshoucai.my.eventbus;

import com.example.rongjiaying.aijieshoucai.login.bean.LoginBean;

public class UpdateAccountManagementEventbus  {
    private LoginBean loginBean;

    public UpdateAccountManagementEventbus(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }
}
