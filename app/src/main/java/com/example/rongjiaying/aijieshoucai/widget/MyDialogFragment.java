package com.example.rongjiaying.aijieshoucai.widget;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by rongjiaying on 2018/3/27.
 */

public class MyDialogFragment extends DialogFragment {
    @Override
    public int show(FragmentTransaction transaction, String tag) {
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(this, tag).addToBackStack(null);
        return transaction.commitAllowingStateLoss();
    }

}
