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

            if ((item.getStatus()+"").equals("1"))
            {
                tvStatus.setText("待初审");
                tvStatus.setTextColor(context.getResources().getColor(R.color.color_178C68));
            }
           else if ((item.getStatus()+"").equals("2"))
            {
                tvStatus.setText("初审待复审");
                tvStatus.setTextColor(context.getResources().getColor(R.color.color_178C68));
            }
            else if ((item.getStatus()+"").equals("3"))
            {
                tvStatus.setText("初审增添资料待上传");
                tvStatus.setTextColor(context.getResources().getColor(R.color.color_178C68));
            }
            else if ((item.getStatus()+"").equals("4"))
            {
                tvStatus.setText("初审增添资料待审核");
                tvStatus.setTextColor(context.getResources().getColor(R.color.color_178C68));
            }
            else if ((item.getStatus()+"").equals("5"))
            {
                tvStatus.setText("初审增添资料待复审");
                tvStatus.setTextColor(context.getResources().getColor(R.color.color_178C68));
            }
            else if ((item.getStatus()+"").equals("6"))
            {
                tvStatus.setText("待指派装G");
                tvStatus.setTextColor(context.getResources().getColor(R.color.color_178C68));
            }
            else if ((item.getStatus()+"").equals("7"))
            {
                tvStatus.setText("待装G");
                tvStatus.setTextColor(context.getResources().getColor(R.color.color_178C68));
            }
            else if ((item.getStatus()+"").equals("8"))
            {
                tvStatus.setText("待指派权证");
                tvStatus.setTextColor(context.getResources().getColor(R.color.color_178C68));
            }
            else if ((item.getStatus()+"").equals("9"))
            {
                tvStatus.setText("待权证");
                tvStatus.setTextColor(context.getResources().getColor(R.color.color_178C68));
            }
            else if ((item.getStatus()+"").equals("10"))
            {
                tvStatus.setText("已完成");
                tvStatus.setTextColor(context.getResources().getColor(R.color.color_178C68));
            }
            else if ((item.getStatus()+"").equals("1-1"))
            {   tvStatus.setText("请补全资料");
                tvStatus.setTextColor(context.getResources().getColor(R.color.color_4C5CF7));
                tvStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onOrderItemListener != null) {
                            onOrderItemListener.checkMessageFirstTral(item);
                        }
                    }
                });

            }
            else if ((item.getStatus()+"").equals("2-1"))
            {   tvStatus.setText("请补全资料");
                tvStatus.setTextColor(context.getResources().getColor(R.color.color_4C5CF7));
                tvStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onOrderItemListener != null) {
                            onOrderItemListener.checkMessageLastInstance(item);
                        }
                    }
                });

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
