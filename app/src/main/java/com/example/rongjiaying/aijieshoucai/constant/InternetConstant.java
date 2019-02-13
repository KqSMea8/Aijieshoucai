package com.example.rongjiaying.aijieshoucai.constant;

/**
 * Created by BODY on 2017/4/17.
 */

public interface InternetConstant {

    /**
     * 接口IP http://114.116.100.233:8080/x_springboot/
     *
     * 弟弟的ip  192.168.31.124
     */
    public static final String SERVER_HOST = "114.116.100.233:8080";
   //public static final String SERVER_HOST = "t.513zj.com";
    /**
     * 项目名称
     */
    public static final String API = "x_springboot";

    /**
     * 接口地址拼写
     */
    public static final String API_URL = "http://" + SERVER_HOST  + "/"+API+"/";
//http://114.116.100.233:8080/x_springboot/images/banner2.jpg
  String imageurl=API_URL+"images/";  //图片
}

