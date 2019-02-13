package com.example.rongjiaying.aijieshoucai.callback;

import android.view.View;
import android.widget.ProgressBar;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.base.Request;

public abstract class ProgressBarStringCallback extends StringCallback {
    ProgressBar progressBar;

    public ProgressBarStringCallback(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    public void onStart(Request<String, ? extends Request> request) {
        super.onStart(request);
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }
}
