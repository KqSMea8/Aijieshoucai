package com.example.rongjiaying.aijieshoucai.order.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseViewHolder;
import com.example.rongjiaying.aijieshoucai.baseadapter.SimpleAdapter;
import com.example.rongjiaying.aijieshoucai.interfac.OnOrderItemListener;
import com.example.rongjiaying.aijieshoucai.order.bean.OrderListBean;
import com.example.rongjiaying.aijieshoucai.util.Judge;

public class OrderListAdapter extends SimpleAdapter<OrderListBean> {
    int type = 0;

    public OrderListAdapter(Context context, int type) {
        super(context, R.layout.item_orderlistlayout);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, final OrderListBean item) {
        AppCompatTextView tvOrderId = viewHoder.getAppCompatTextView(R.id.tv_orderid);//订单号
        AppCompatTextView tvUsername = viewHoder.getAppCompatTextView(R.id.tv_username);//申请人
        AppCompatTextView tvPhone = viewHoder.getAppCompatTextView(R.id.tv_phone);//电话号码
        AppCompatTextView tvStatus = viewHoder.getAppCompatTextView(R.id.tv_status);//status
        AppCompatTextView tvProduceName = viewHoder.getAppCompatTextView(R.id.tv_producename);//产品名字
        //订单号
        if (!Judge.getBoolean_isNull(item.getOrderCode())) {
            tvOrderId.setText(item.getOrderCode());
        }
        //订单号
        //申请人
        if (!Judge.getBoolean_isNull(item.getBorrowerName())) {
            tvUsername.setText(item.getBorrowerName());
        }
        //申请人

        //电话号码
        if (!Judge.getBoolean_isNull(item.getBorrowerPhone())) {
            tvPhone.setText(item.getBorrowerPhone());
        }
        //电话号码

        int status = item.getStatus();
        if (type == 0) {
            //状态
            switch (status) {
                case 2:
                case 3:
                    tvStatus.setText("初审中");
                    tvStatus.setTextColor(context.getResources().getColor(R.color.color_178C68));
                    break;
                case 4:
                    tvStatus.setText("终审中");
                    tvStatus.setTextColor(context.getResources().getColor(R.color.color_178C68));
                    break;
                case 5:
                case 6:
                    if (item.getBackStatus() == 1) {
                        tvStatus.setText("请补全资料");
                        tvStatus.setTextColor(context.getResources().getColor(R.color.color_4C5CF7));
                    } else {
                        tvStatus.setText("已拒批");
                        tvStatus.setTextColor(context.getResources().getColor(R.color.color_e92c2a));
                    }


                    tvStatus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onOrderItemListener != null) {
                                if (item.getStatus() == 5)//初审
                                {
                                    if (item.getBackStatus() == 1)//backstatus 1补全资料 2 拒批
                                    {

                                        onOrderItemListener.checkMessageFirstTral(item);
                                    } else {
                                        onOrderItemListener.checkMessageRefusal(item);
                                    }

                                } else if (item.getStatus() == 6)//终审
                                {
                                    if (item.getBackStatus() == 1)//backstatus 1补全资料 2 拒批
                                    {
                                        onOrderItemListener.checkMessageLastInstance(item);
                                    } else {

                                        onOrderItemListener.checkMessageRefusal(item);
                                    }

                                }
                            }
                        }
                    });


                    break;
            }
        } else if (type == 1) {
            tvStatus.setText("签约中");
            tvStatus.setTextColor(context.getResources().getColor(R.color.color_e92c2a));

            tvStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onOrderItemListener != null) {
                        onOrderItemListener.checkMessageSigning(item);
                    }
                }
            });
        } else if (type == 2) {
            tvStatus.setText("已放款");
            tvStatus.setTextColor(context.getResources().getColor(R.color.color_178C68));
        }


        if (!Judge.getBoolean_isNull(item.getProduceName())) {
            tvProduceName.setText(item.getProduceName());
        }
    }

    public void setOnOrderItemListener(OnOrderItemListener onOrderItemListener) {
        this.onOrderItemListener = onOrderItemListener;
    }

    OnOrderItemListener onOrderItemListener;


}
