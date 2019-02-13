package com.example.rongjiaying.aijieshoucai.work;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import androidx.work.Worker;

/**
 * work
 * 增加M
 */
public class GetMWork extends Worker {
    @NonNull
    @Override
    public WorkerResult doWork() {

        String user_id = getInputData().getString("user_id", "");
        String money_m = getInputData().getString("money_m", "");
        HttpUtil.getM(user_id,money_m, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                Log.i("asdf","getM"+response.body());
            }
        });
        return WorkerResult.SUCCESS;
    }
}
