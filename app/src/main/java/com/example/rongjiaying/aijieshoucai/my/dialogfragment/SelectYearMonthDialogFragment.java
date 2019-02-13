package com.example.rongjiaying.aijieshoucai.my.dialogfragment;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;

import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.widget.MyDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * 选择时间
 */
public class SelectYearMonthDialogFragment extends MyDialogFragment {

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xffffff0));
       getDialog().getWindow().setLayout((int) (dm.widthPixels ), ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    public SelectYearMonthDialogFragment() {
        // Required empty public constructor
    }

    /**
     */
    // TODO: Rename and change types and number of parameters
    public static SelectYearMonthDialogFragment newInstance() {
        SelectYearMonthDialogFragment fragment = new SelectYearMonthDialogFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_year_month_dialog, container, false);
        // 设置宽度为屏宽、靠近屏幕底部。
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);

        final NumberPicker npYear = view.findViewById(R.id.np_year);
        final NumberPicker npMonth = view.findViewById(R.id.np_month);
        AppCompatTextView tvCancle = view.findViewById(R.id.tv_cancle);
        AppCompatTextView tvCommit = view.findViewById(R.id.tv_commit);
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        try {
            int year = Integer.valueOf(getCurrentYear());
            npYear.setMinValue(year - 20);
            npYear.setMaxValue(year + 20);
            npYear.setValue(year);
            int month = Integer.valueOf(getCurrentMonth());
            npMonth.setMinValue(1);
            npMonth.setMaxValue(12);
            npMonth.setValue(month);
            tvCommit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String npyearvalue = npYear.getValue() + "";
                    String npmonthvalue = getMonthValue(npMonth.getValue());
                    if (onSelectYearMonthListener != null) {
                        onSelectYearMonthListener.onSelectYearMonthItem(npyearvalue + "-" + npmonthvalue);
                    }
                    dismiss();
                }
            });


        } catch (Exception ex) {
        }
        return view;
    }

    private String getMonthValue(int value) {
        if (value > 10) {
            return value + "";
        } else {
            return "0" + value;
        }
    }

    public String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }

    public String getCurrentMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        Date date = new Date();
        return sdf.format(date);
    }

    public void setOnSelectYearMonthListener(OnSelectYearMonthListener onSelectYearMonthListener) {
        this.onSelectYearMonthListener = onSelectYearMonthListener;
    }

    OnSelectYearMonthListener onSelectYearMonthListener;

    public interface OnSelectYearMonthListener {
        void onSelectYearMonthItem(String date);
    }
}
