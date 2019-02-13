package com.example.rongjiaying.aijieshoucai.util;

import android.app.Activity;
import android.content.Intent;

import com.example.rongjiaying.aijieshoucai.home.activity.BrokderRankingDetailActivity;
import com.example.rongjiaying.aijieshoucai.home.activity.BrokderRankinglistActivity;
import com.example.rongjiaying.aijieshoucai.home.activity.EntryInformationActivity;
import com.example.rongjiaying.aijieshoucai.home.activity.HomeActivity;
import com.example.rongjiaying.aijieshoucai.home.activity.ProductDetailActivity;
import com.example.rongjiaying.aijieshoucai.home.activity.ProductListActivity;
import com.example.rongjiaying.aijieshoucai.home.bean.BrokerBean;
import com.example.rongjiaying.aijieshoucai.home.bean.ProductListBean;
import com.example.rongjiaying.aijieshoucai.login.activity.ForGotPasswordActivity;
import com.example.rongjiaying.aijieshoucai.login.activity.LoginActivity;
import com.example.rongjiaying.aijieshoucai.login.activity.RegistActivity;
import com.example.rongjiaying.aijieshoucai.message.activity.ContactListActivity;
import com.example.rongjiaying.aijieshoucai.message.activity.ExserviceActivity;
import com.example.rongjiaying.aijieshoucai.my.activity.AboutUsActivity;
import com.example.rongjiaying.aijieshoucai.my.activity.AccountManagementActivity;
import com.example.rongjiaying.aijieshoucai.my.activity.AddBankActivity;
import com.example.rongjiaying.aijieshoucai.my.activity.CashRuleActivity;
import com.example.rongjiaying.aijieshoucai.my.activity.CommentQuestionActivity;
import com.example.rongjiaying.aijieshoucai.my.activity.CommentQuestionDetailActivity;
import com.example.rongjiaying.aijieshoucai.my.activity.DraftsActivity;
import com.example.rongjiaying.aijieshoucai.my.activity.DraftsListActivity;
import com.example.rongjiaying.aijieshoucai.my.activity.EditNickNameActivity;
import com.example.rongjiaying.aijieshoucai.my.activity.EditPasswordActivity;
import com.example.rongjiaying.aijieshoucai.my.activity.EditPhoneActivity;
import com.example.rongjiaying.aijieshoucai.my.activity.MyAchievementActivity;
import com.example.rongjiaying.aijieshoucai.my.activity.MyBankActivity;
import com.example.rongjiaying.aijieshoucai.my.activity.MyMessageActivity;
import com.example.rongjiaying.aijieshoucai.my.activity.MyRecommendationActivity;
import com.example.rongjiaying.aijieshoucai.my.activity.OrderActivity;
import com.example.rongjiaying.aijieshoucai.my.activity.PerformanceStatisticsActivity;
import com.example.rongjiaying.aijieshoucai.my.activity.ShareActivity;
import com.example.rongjiaying.aijieshoucai.my.activity.WithdrawActivity;
import com.example.rongjiaying.aijieshoucai.my.activity.WithdrawErrActivity;
import com.example.rongjiaying.aijieshoucai.my.activity.WithdrawSuccessActivity;
import com.example.rongjiaying.aijieshoucai.my.activity.WithdrawalsrecordActivity;
import com.example.rongjiaying.aijieshoucai.my.bean.CommentQuestionListBean;
import com.example.rongjiaying.aijieshoucai.my.bean.MyAchievementBean;
import com.example.rongjiaying.aijieshoucai.order.activity.OrderCompletionActivity;
import com.example.rongjiaying.aijieshoucai.order.activity.OrderDetailActivity;
import com.example.rongjiaying.aijieshoucai.order.activity.OrderLastCompletionActivity;
import com.example.rongjiaying.aijieshoucai.order.activity.OrderRefusalDetailActivity;
import com.example.rongjiaying.aijieshoucai.order.activity.OrderSigningActivity;
import com.example.rongjiaying.aijieshoucai.order.bean.OrderListBean;

public class IntentUtil {
    /**
     * 登录页面
     *
     * @param activity
     */
    public static void Intent_LoginActivity(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }
    /**
     * 登录页面
     *
     * @param activity
     */
    public static void Intent_LoginActivity(Activity activity,boolean isloginout) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.putExtra("isloginout",isloginout);
        activity.startActivity(intent);
    }
    /**
     * 注册页面
     *
     * @param activity
     */
    public static void Intent_RegistActivity(Activity activity) {
        Intent intent = new Intent(activity, RegistActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 忘记密码页面
     *
     * @param activity
     */
    public static void Intent_ForGotPasswordActivity(Activity activity) {
        Intent intent = new Intent(activity, ForGotPasswordActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 主页面
     *
     * @param activity
     */
    public static void Intent_HomeActivity(Activity activity) {
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 信息录入
     *
     * @param activity
     */
    public static void Intent_EntryInformationActivity(Activity activity) {
        Intent intent = new Intent(activity, EntryInformationActivity.class);
        activity.startActivity(intent);
    }
    /**
     * 信息录入
     *
     * @param activity
     */
    public static void Intent_EntryInformationActivity(Activity activity, ProductListBean productListBean) {
        Intent intent = new Intent(activity, EntryInformationActivity.class);
        intent.putExtra("item",productListBean);
        activity.startActivity(intent);
    }
    /**
     * 信息录入
     *
     * @param activity
     */
    public static void Intent_ProductDetailActivity(Activity activity, ProductListBean productListBean) {
        Intent intent = new Intent(activity, ProductDetailActivity.class);
        intent.putExtra("item",productListBean);
        activity.startActivity(intent);
    }
    /**
     * 草稿箱
     *
     * @param activity
     */
    public static void Intent_DraftsActivity(Activity activity, OrderListBean orderListBean) {
        Intent intent = new Intent(activity, DraftsActivity.class);
        intent.putExtra("item", orderListBean);
        activity.startActivity(intent);
    }

    /**
     * 草稿箱列表
     *
     * @param activity
     */
    public static void Intent_DraftsListActivity(Activity activity) {
        Intent intent = new Intent(activity, DraftsListActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 订单详情
     *
     * @param activity
     */
    public static void Intent_OrderDetailActivity(Activity activity, OrderListBean item
            , int type) {
        Intent intent = new Intent(activity, OrderDetailActivity.class);
        intent.putExtra("item", item);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    /**
     * 初审补资料
     *
     * @param activity
     */
    public static void Intent_OrderCompletionActivity(Activity activity, OrderListBean item) {
        Intent intent = new Intent(activity, OrderCompletionActivity.class);
        intent.putExtra("item", item);
        activity.startActivity(intent);
    }

    /**
     * 初审补资料
     *
     * @param activity
     */
    public static void Intent_OrderLastCompletionActivity(Activity activity, OrderListBean item) {
        Intent intent = new Intent(activity, OrderLastCompletionActivity.class);
        intent.putExtra("item", item);
        activity.startActivity(intent);
    }

    /**
     * 审核  拒批
     *
     * @param activity
     */
    public static void Intent_OrderRefusalDetailActivity(Activity activity, OrderListBean item) {
        Intent intent = new Intent(activity, OrderRefusalDetailActivity.class);
        intent.putExtra("item", item);
        activity.startActivity(intent);
    }

    /**
     * 签约中
     *
     * @param activity
     */
    public static void Intent_OrderSigningActivity(Activity activity, OrderListBean item) {
        Intent intent = new Intent(activity, OrderSigningActivity.class);
        intent.putExtra("item", item);
        activity.startActivity(intent);
    }

    /**
     * 常见问题
     *
     * @param activity
     */
    public static void Intent_CommentQuestionActivity(Activity activity) {
        Intent intent = new Intent(activity, CommentQuestionActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 常见问题详情
     *
     * @param activity
     */
    public static void Intent_CommentQuestionDetailActivity(Activity activity
            , CommentQuestionListBean commentQuestionListBean) {
        Intent intent = new Intent(activity, CommentQuestionDetailActivity.class);
        intent.putExtra("commentQuestionListBean", commentQuestionListBean);
        activity.startActivity(intent);
    }

    /**
     * 我的消息
     *
     * @param activity
     */
    public static void Intent_MyMessageActivity(Activity activity) {
        Intent intent = new Intent(activity, MyMessageActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 我的业绩
     *
     * @param activity
     */
    public static void Intent_MyAchievementActivity(Activity activity) {
        Intent intent = new Intent(activity, MyAchievementActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 业绩统计
     *
     * @param activity
     */
    public static void Intent_PerformanceStatisticsActivity(Activity activity) {
        Intent intent = new Intent(activity, PerformanceStatisticsActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 提现记录
     *
     * @param activity
     */
    public static void Intent_WithdrawalsrecordActivity(Activity activity) {
        Intent intent = new Intent(activity, WithdrawalsrecordActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 提现
     *
     * @param activity
     */
    public static void Intent_WithdrawActivity(Activity activity, MyAchievementBean myAchievementBean) {
        Intent intent = new Intent(activity, WithdrawActivity.class);
        intent.putExtra("item", myAchievementBean);
        activity.startActivity(intent);
    }

    /**
     * 我的  管理
     *
     * @param activity
     */
    public static void Intent_AccountManagementActivity(Activity activity) {
        Intent intent = new Intent(activity, AccountManagementActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 我的银行卡
     *
     * @param activity
     */
    public static void Intent_MyBankActivity(Activity activity) {
        Intent intent = new Intent(activity, MyBankActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 添加银行卡
     *
     * @param activity
     */
    public static void Intent_AddBankActivity(Activity activity) {
        Intent intent = new Intent(activity, AddBankActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 修改昵称
     *
     * @param activity
     */
    public static void Intent_EditNickNameActivity(Activity activity) {
        Intent intent = new Intent(activity, EditNickNameActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 修改密码
     *
     * @param activity
     */
    public static void Intent_EditPasswordActivity(Activity activity) {
        Intent intent = new Intent(activity, EditPasswordActivity.class);
        activity.startActivity(intent);
    }
    /**
     * 修改密码
     *
     * @param activity
     */
    public static void Intent_EditPhoneActivity(Activity activity) {
        Intent intent = new Intent(activity, EditPhoneActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 提现成功
     *
     * @param activity
     */
    public static void Intent_WithdrawSuccessActivity(Activity activity) {
        Intent intent = new Intent(activity, WithdrawSuccessActivity.class);
        activity.startActivity(intent);
    }
    /**
     * 提现失败
     *
     * @param activity
     */
    public static void Intent_WithdrawErrActivity(Activity activity) {
        Intent intent = new Intent(activity, WithdrawErrActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 业务列表
     *
     * @param activity
     */
    public static void Intent_ProductListActivity(Activity activity) {
        Intent intent = new Intent(activity, ProductListActivity.class);
        activity.startActivity(intent);
    }
    /**
     * 经纪人排行榜
     *
     * @param activity
     */
    public static void Intent_BrokderRankinglistActivity(Activity activity) {
        Intent intent = new Intent(activity, BrokderRankinglistActivity.class);
        activity.startActivity(intent);
    }
    /**
     * 经纪人排行榜
     *
     * @param activity
     */
    public static void Intent_BrokderRankingDetailActivity(Activity activity, String user_id) {
        Intent intent = new Intent(activity, BrokderRankingDetailActivity.class);
        intent.putExtra("user_id",user_id);
        activity.startActivity(intent);
    }
    /**
     * 提现规则
     *
     * @param activity
     */
    public static void Intent_CashRuleActivity(Activity activity) {
        Intent intent = new Intent(activity, CashRuleActivity.class);
        activity.startActivity(intent);
    }
    /**
     * 我的推荐
     *
     * @param activity
     */
    public static void Intent_MyRecommendationActivity(Activity activity) {
        Intent intent = new Intent(activity, MyRecommendationActivity.class);
        activity.startActivity(intent);
    }
    /**
     * 关于我们
     *
     * @param activity
     */
    public static void Intent_AboutUsActivity(Activity activity) {
        Intent intent = new Intent(activity, AboutUsActivity.class);
        activity.startActivity(intent);
    }
    /**
     * 订单activity
     *
     * @param activity
     */
    public static void Intent_OrderActivity(Activity activity) {
        Intent intent = new Intent(activity, OrderActivity.class);
        activity.startActivity(intent);
    }
    /**
     * 关于我们
     *
     * @param activity
     */
    public static void Intent_ShareActivity(Activity activity) {
        Intent intent = new Intent(activity, ShareActivity.class);
        activity.startActivity(intent);
    }
    /**
     * 关于我们
     *
     * @param activity
     */
    public static void Intent_ExserviceActivity(Activity activity) {
        Intent intent = new Intent(activity, ExserviceActivity.class);
        activity.startActivity(intent);
    }
    /**
     * 联系人列表
     *
     * @param activity
     */
    public static void Intent_ContactListActivity(Activity activity) {
        Intent intent = new Intent(activity, ContactListActivity.class);
        activity.startActivity(intent);
    }
}
