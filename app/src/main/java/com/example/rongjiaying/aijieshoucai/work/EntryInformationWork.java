package com.example.rongjiaying.aijieshoucai.work;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;

import java.io.File;

import androidx.work.Worker;

public class EntryInformationWork extends Worker {
    @NonNull
    @Override
    public WorkerResult doWork() {

        String user_id = getInputData().getString("user_id", "");
        String username = getInputData().getString("username", "");
        String phone = getInputData().getString("phone", "");
        String code = getInputData().getString("code", "");
        String pid = getInputData().getString("pid", "");
        String drivinglicenseimage = getInputData().getString("drivinglicenseimage", "");
        String frontimage = getInputData().getString("frontimage", "");
        String reverseimage = getInputData().getString("reverseimage", "");

        HttpUtil.uploadOrderData(user_id, username
                , phone, code, pid,
                new File(drivinglicenseimage)
                , new File(frontimage)
                , new File(reverseimage), new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());

                            int code = jsonObject.getInt("code");
                            if (code == 0) {
                                Toast.makeText(getApplicationContext(), "录入成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "录入失败", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "录入失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });
        return WorkerResult.SUCCESS;
    }
}
