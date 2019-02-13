package com.example.rongjiaying.aijieshoucai.util;

import com.example.rongjiaying.aijieshoucai.callback.ProgressBarStringCallback;
import com.example.rongjiaying.aijieshoucai.constant.UrlConstant;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

import java.io.File;

public class HttpUtil {
    /**
     * banner
     *
     * @param stringCallback
     */
    public static void banner(StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.banner)                            // 请求方式和请求url
                .tag("banner")                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(stringCallback);
    }

    public static void sendCode(String s, StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.sendCode)                            // 请求方式和请求url
                .tag("sendCode")
                .params("phone", s)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(stringCallback);
    }

    /**
     * 注册
     *
     * @param phone
     * @param password
     * @param sms_code
     * @param stringCallback
     */
    public static void registUser(String phone, String password, String sms_code, String code, StringCallback stringCallback) {
        PostRequest<String> postRequest = OkGo.<String>post(UrlConstant.registUser)                            // 请求方式和请求url
                .tag("registUser");

        postRequest.params("phone", phone);// 请求的 tag, 主要用于取消对应的请求
        postRequest.params("password", password);// 请求的 tag, 主要用于取消对应的请求
        postRequest.params("sms_code", sms_code);// 请求的 tag, 主要用于取消对应的请求
        if (!Judge.getBoolean_isNull(code)) {
            postRequest.params("code", code);// 请求的 tag, 主要用于取消对应的请求
        }

        postRequest.cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(stringCallback);
    }

    public static void login(String phone, String password, StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.login)                            // 请求方式和请求url
                .tag("login")
                .params("phone", phone)// 请求的 tag, 主要用于取消对应的请求
                .params("password", password)// 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(stringCallback);
    }

    /**
     * 滚动消息
     *
     * @param stringCallback
     */
    public static void rollData(StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.rollData)                            // 请求方式和请求url
                .tag("rollData")
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(stringCallback);
    }

    public static void productList(StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.productList)                            // 请求方式和请求url
                .tag("productList")
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(stringCallback);
    }

    public static void orderList(String user_id, String status, String offset, String limit, StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.orderList)                            // 请求方式和请求url
                .tag("orderList")
                .params("user_id", user_id)
                .params("status", status)
                .params("offset", offset)
                .params("limit", limit)
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(stringCallback);
    }

    public static void ordersingningDetail(StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.ordersingningDetail)
                .tag("")
                .cacheKey("cacheKey")
                .cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);
    }

    /**
     * 录入订单
     *
     * @param user_id
     * @param borrower_name
     * @param borrower_phone
     * @param sms_code
     * @param apply_produce
     * @param drivice_lincence
     * @param card_front
     * @param card_back
     * @param stringCallback
     */
    public static void uploadOrderData(String user_id, String borrower_name, String borrower_phone, String sms_code,
                                       String apply_produce, File drivice_lincence, File card_front, File card_back, StringCallback stringCallback) {

        OkGo.<String>post(UrlConstant.uploadOrderData)
                .tag("uploadOrderData")
                .params("user_id", user_id)
                .params("borrower_name", borrower_name)
                .params("borrower_phone", borrower_phone)
                .params("sms_code", sms_code)
                .params("apply_produce", apply_produce)
                .params("drivice_lincence", drivice_lincence)
                .params("card_front", card_front)
                .params("borrower_name", borrower_name)
                .params("card_back", card_back)
                .cacheKey("uploadOrderData")
                .cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);

    }

    /**
     * 常见问题
     */
    public static void commentquestion(StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.commentquestion)
                .tag("commentquestion")
                .cacheKey("cacheKey")
                .cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);
    }

    /**
     * 保存草稿
     *
     * @param user_id
     * @param borrower_name
     * @param borrower_phone
     * @param apply_produce
     * @param drivice_image
     * @param card_front
     * @param card_back
     * @param stringCallback
     */
    public static void saveCaogao(String user_id, String borrower_name, String borrower_phone, String apply_produce,
                                  String drivice_image,
                                  String card_front,
                                  String card_back,
                                  String order_code,
                                  String status, StringCallback stringCallback) {
        PostRequest<String> postRequest = OkGo.<String>post(UrlConstant.savecaogao)
                .tag("savecaogao");

        postRequest.params("user_id", user_id);
        if (!Judge.getBoolean_isNull(borrower_name)) {
            postRequest.params("borrower_name", borrower_name);
        }

        if (!Judge.getBoolean_isNull(borrower_phone)) {
            postRequest.params("borrower_phone", borrower_phone);
        }
        if (!Judge.getBoolean_isNull(apply_produce)) {
            postRequest.params("apply_produce", apply_produce);
        }

        File file = new File(drivice_image);
        boolean b = file.exists();
        if (!Judge.getBoolean_isNull(drivice_image) && new File(drivice_image).exists()) {
            postRequest.params("drivice_lincence", new File(drivice_image));
        }


        if (!Judge.getBoolean_isNull(card_front) && new File(card_front).exists()) {
            postRequest.params("card_front", new File(card_front));
        }

        if (!Judge.getBoolean_isNull(card_back) && new File(card_back).exists()) {
            postRequest.params("card_back", new File(card_back));
        }

        if (!Judge.getBoolean_isNull(order_code)) {
            postRequest.params("order_code", order_code);
        }

        if (!Judge.getBoolean_isNull(status)) {
            postRequest.params("status", status);
        }

        postRequest.cacheKey("cacheKey")
                .cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);

    }

    /**
     * 获取草稿
     *
     * @param user_id
     * @param offset
     * @param limit
     * @param stringCallback
     */
    public static void caogaolist(String user_id, String offset, String limit, StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.caogaolist)
                .tag("caogaolist")
                .cacheKey("cacheKey")
                .params("user_id", user_id)
                .params("offset", offset)
                .params("limit", limit)
                .cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);
    }


    public static void orderCompletion(String user_id, String borrower_name, String apply_produce, String order_code,
                                       String drivice_lincence,
                                       String card_front,
                                       String card_back, StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.orderCompletion)
                .tag("orderCompletion")
                .cacheKey("cacheKey")
                .params("user_id", user_id)
                .params("borrower_name", borrower_name)
                .params("apply_produce", apply_produce)
                .params("order_code", order_code)
                .params("drivice_lincence", new File(drivice_lincence))
                .params("card_front", new File(card_front))
                .params("card_back", new File(card_back))
                .cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);
    }

    public static void instanceorder(String prove_type, String order_code, String image, StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.instanceorder)
                .tag("instanceorder")
                .cacheKey("cacheKey")
                .params("prove_type", prove_type)
                .params("order_code", order_code)
                .params("prove_type_name", new File(image))
                .cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);
    }

    /**
     * 消息列表
     *
     * @param user_id
     * @param offset
     * @param limit
     * @param search
     * @param stringCallback
     */
    public static void myMessageData(String user_id, String offset, String limit, String search, StringCallback stringCallback) {
        PostRequest<String> postRequest = OkGo.<String>post(UrlConstant.myMessageData)
                .tag("myMessageData");

        postRequest.params("user_id", user_id);
        postRequest.params("offset", offset);
        postRequest.params("limit", limit);
        if (!Judge.getBoolean_isNull(search)) {
            postRequest.params("search", search);
        }
        postRequest.cacheKey("cacheKey")
                .cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);

    }

    /**
     * 我的业绩界面
     *
     * @param user_id
     * @param stringCallback
     */
    public static void myachievements(String user_id, StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.myachievements)
                .tag("myachievements")
                .cacheKey("cacheKey")
                .params("user_id", user_id)
                .cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);
    }

    public static void upMyImage(String userId, String path, StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.upMyImage)
                .tag("upMyImage")
                .cacheKey("cacheKey")
                .params("user_id", userId)
                .params("usericon", new File(path)).cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);
    }

    /**
     * 业绩统计
     *
     * @param userId
     * @param datetime
     * @param stringCallback
     */
    public static void achievementsStatistical(String userId, String datetime, StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.achievementsStatistical)
                .tag("achievementsStatistical")
                .cacheKey("cacheKey")
                .params("user_id", userId)
                .params("month", datetime).cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);
    }

    /**
     * 提现记录列表
     *
     * @param userId
     * @param stringCallback
     */
    public static void initWithdrawalsrecord(String userId, String month, StringCallback stringCallback) {
        PostRequest<String> postRequest = OkGo.<String>post(UrlConstant.initWithdrawalsrecord)
                .tag("initWithdrawalsrecord")
                .cacheKey("cacheKey");
        postRequest.params("user_id", userId);
        if (!Judge.getBoolean_isNull(month)) {
            postRequest.params("month", month);
        }

        postRequest.cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);
    }

    /**
     * 我的银行卡
     *
     * @param userId
     * @param stringCallback
     */
    public static void myBank(String userId, StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.myBank)
                .tag("myBank")
                .cacheKey("cacheKey")
                .params("user_id", userId).cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);
    }

    public static void saveBankNumber(String userId, String account_name, String deposit_bank, String bank_code, String bank_name, StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.saveBankNumber)
                .tag("saveBankNumber")
                .cacheKey("cacheKey")
                .params("user_id", userId)
                .params("account_name", account_name)
                .params("deposit_bank", deposit_bank)
                .params("bank_code", bank_code)
                .params("bank_name", bank_name)
                .cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);
    }

    /**
     * 删除银行卡
     *
     * @param stringCallback
     */
    public static void deleBank(String bank_id, StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.deleBank)
                .tag("deleBank")
                .cacheKey("cacheKey")
                .params("bank_id", bank_id)
                .cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);
    }

    /**
     * 设置默认
     *
     * @param newbank_id
     * @param oldbank_id
     * @param stringCallback
     */
    public static void updateBankDefault(String newbank_id, String oldbank_id, StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.updateBankDefault)
                .tag("deleBank")
                .cacheKey("cacheKey")
                .params("newbank_id", newbank_id)
                .params("oldbank_id", oldbank_id)
                .cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);
    }

    public static void bankName(ProgressBarStringCallback progressBarStringCallback) {
        OkGo.<String>post(UrlConstant.bankName)
                .tag("bankName")
                .cacheKey("cacheKey")
                .cacheMode(CacheMode.DEFAULT)
                .execute(progressBarStringCallback);
    }

    public static void editNickname(String userId, String name, ProgressBarStringCallback progressBarStringCallback) {
        OkGo.<String>post(UrlConstant.editname)
                .tag("bankName")
                .cacheKey("cacheKey")
                .params("user_id", userId)
                .params("name", name)
                .cacheMode(CacheMode.DEFAULT)
                .execute(progressBarStringCallback);
    }

    public static void forgotPassword(String phone, String new_password, ProgressBarStringCallback progressBarStringCallback) {
        OkGo.<String>post(UrlConstant.forgotPassword)
                .tag("forgotPassword")
                .cacheKey("cacheKey")
                .params("phone", phone)
                .params("new_password", new_password)
                .cacheMode(CacheMode.DEFAULT)
                .execute(progressBarStringCallback);
    }

    /**
     * 提现
     *
     * @param user_id
     * @param present_m
     * @param present_g
     * @param create_time
     * @param progressBarStringCallback
     */
    public static void savepresent(String user_id, String present_m, String present_g, String create_time, ProgressBarStringCallback progressBarStringCallback) {
        PostRequest<String> postRequest = OkGo.<String>post(UrlConstant.savepresent)
                .tag("savepresent")
                .cacheKey("cacheKey");
        postRequest.params("user_id", user_id);
        if (!Judge.getBoolean_isNull(present_m)) {
            postRequest.params("present_m", present_m);
        }

        postRequest.params("present_g", present_g);
        postRequest.params("create_time", create_time);
        postRequest.cacheMode(CacheMode.DEFAULT)
                .execute(progressBarStringCallback);
    }

    /**
     * 提现记录
     */
    public static void getPresentListByMonthList(String user_id, String mouth, ProgressBarStringCallback progressBarStringCallback) {
        OkGo.<String>post(UrlConstant.getPresentListByMonthList)
                .tag("getPresentListByMonthList")
                .cacheKey("cacheKey")
                .params("user_id", user_id)
                .params("mouth", mouth)
                .cacheMode(CacheMode.DEFAULT)
                .execute(progressBarStringCallback);
    }

    /**
     * 修改密码
     */
    public static void editPassword(String user_id, String password, ProgressBarStringCallback progressBarStringCallback) {
        OkGo.<String>post(UrlConstant.editPassword)
                .tag("editPassword")
                .cacheKey("cacheKey")
                .params("user_id", user_id)
                .params("password", password)
                .cacheMode(CacheMode.DEFAULT)
                .execute(progressBarStringCallback);
    }

    /**
     * 经纪人排行榜  提现
     *
     * @param stringCallback
     */
    public static void getRankingPresent(String url, StringCallback stringCallback) {
        OkGo.<String>post(url)
                .tag("getRankingPresent")
                .cacheKey("cacheKey")
                .cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);
    }

    /**
     * 欢迎页面
     */
    public static void welcome(ProgressBarStringCallback progressBarStringCallback) {
        OkGo.<String>post(UrlConstant.welcome)
                .tag("welcome")
                .cacheKey("cacheKey")
                .cacheMode(CacheMode.DEFAULT)
                .execute(progressBarStringCallback);
    }

    /**
     * 个人中心
     *
     * @param userId
     * @param progressBarStringCallback
     */

    public static void myPersonal(String userId, StringCallback progressBarStringCallback) {
        OkGo.<String>post(UrlConstant.myPersonal)
                .tag("myPersonal")
                .cacheKey("cacheKey")
                .params("user_id", userId)
                .cacheMode(CacheMode.DEFAULT)
                .execute(progressBarStringCallback);
    }

    /**
     * 经纪人详情
     *
     * @param userId
     * @param stringCallback
     */
    public static void userDetail(String userId, StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.userDetail)
                .tag("userDetail")
                .cacheKey("cacheKey")
                .params("user_id", userId)
                .cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);
    }

    /**
     * 修改手机
     *
     * @param userId
     * @param phone
     * @param stringCallback
     */
    public static void editPhone(String userId, String phone, ProgressBarStringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.editPhone)
                .tag("editPhone")
                .cacheKey("cacheKey")
                .params("user_id", userId)
                .params("phone", phone)
                .cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);
    }

    /**
     * 我的推荐
     *
     * @param url
     * @param userid
     */
    public static void myRecommendation(String url, String userid ,ProgressBarStringCallback progressBarStringCallback) {
        OkGo.<String>post(url)
                .tag("editPhone")
                .cacheKey("cacheKey")
                .params("user_id", userid)
                .cacheMode(CacheMode.DEFAULT)
                .execute(progressBarStringCallback);
    }

    public static void getAppData(StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.getAppData)
                .tag("getAppData")
                .cacheKey("cacheKey")
                .cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);
    }

    public static void getM(String user_id, String money_m, StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.getM)
                .tag("getM")
                .cacheKey("cacheKey")
                .params("user_id", user_id)
                .params("money_m", money_m)
                .cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);
    }

    public static void comegroup(String account_number, String user_id, StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.comegroup)
                .tag("comegroup")
                .cacheKey("cacheKey")
                .params("account_number", account_number)
                .params("user_id", user_id)
                .cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);
    }

    public static void sankeAddGroup(String account_number, long group_id, StringCallback stringCallback) {
        OkGo.<String>post(UrlConstant.sankeAddGroup)
                .tag("sankeAddGroup")
                .cacheKey("cacheKey")
                .params("account_number", account_number)
                .params("group_id", group_id)
                .cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);
    }
}
