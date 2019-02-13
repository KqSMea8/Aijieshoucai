package com.example.rongjiaying.aijieshoucai.work;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.rongjiaying.aijieshoucai.my.eventbus.DraftsEventbus;
import com.example.rongjiaying.aijieshoucai.order.bean.OrderListBean;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.work.Worker;

/**
 * 保存草稿
 */
public class SaveCaoGaoWork extends Worker {
    @NonNull
    @Override
    public WorkerResult doWork() {
        String user_id = getInputData().getString("user_id", "");
        String username = getInputData().getString("username", "");
        String phone = getInputData().getString("phone", "");
        String pid = getInputData().getString("pid", "");
        String drivinglicenseimage = getInputData().getString("drivinglicenseimage", "");
        final String orderListBean=getInputData().getString("orderListBean","");
        String frontimage = getInputData().getString("frontimage", "");
        String reverseimage = getInputData().getString("reverseimage", "");
        String order_code = getInputData().getString("order_code", "");
        String status = getInputData().getString("status", "");
        HttpUtil.saveCaogao(user_id,
                username,
                phone,
                pid,
                drivinglicenseimage,
                frontimage,
                reverseimage, order_code, status, new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response.body());

                            int code=jsonObject.getInt("code");
                            if (code==0)
                            {

                                if (!Judge.getBoolean_isNull(orderListBean))
                                {
                                    OrderListBean o = com.alibaba.fastjson.JSONObject.parseObject(orderListBean,OrderListBean.class);
                                    if (o!=null)
                                    {
                                        EventBus.getDefault().post(new DraftsEventbus(o, 1));
                                    }
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }


                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        Log.i("asdf", "onStart");
                    }
                });
        return WorkerResult.SUCCESS;
    }
}
