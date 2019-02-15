package com.example.rongjiaying.aijieshoucai.callback;

import android.view.View;
import android.widget.ProgressBar;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.base.Request;

public abstract class ComViewProgressBarStringCallback extends StringCallback {
    ProgressBar progressBar;
    View view;

    public ComViewProgressBarStringCallback(ProgressBar progressBar, View view) {
        this.progressBar = progressBar;
        this.view = view;
    }

    @Override
    public void onStart(Request<String, ? extends Request> request) {
        super.onStart(request);
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        if (view != null) {
            view.setClickable(false);
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        if (view != null) {
            view.setClickable(true);
        }
    }
}
