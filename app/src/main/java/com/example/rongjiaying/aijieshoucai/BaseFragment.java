package com.example.rongjiaying.aijieshoucai;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.example.rongjiaying.aijieshoucai.util.SharepercentUtils;

public class BaseFragment extends Fragment {
    protected SharepercentUtils sharepercentUtils;
    /**
     * 获取文件操作工具
     *
     * @return body
     */
    public SharepercentUtils getSharedFileUtils(Activity activity) {
        if (sharepercentUtils == null) {
            sharepercentUtils = new SharepercentUtils(activity);
        }
        return sharepercentUtils;
    }
}
