package com.example.rongjiaying.aijieshoucai.order.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseViewHolder;
import com.example.rongjiaying.aijieshoucai.baseadapter.SimpleAdapter;
import com.example.rongjiaying.aijieshoucai.order.bean.DocumenTtypeBean;

import java.util.List;

public class DocumentTypeBeanAdapter extends SimpleAdapter<DocumenTtypeBean> {
    public DocumentTypeBeanAdapter(Context context) {
        super(context, R.layout.item_documenttypelayout);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, final DocumenTtypeBean item) {
        AppCompatImageView icon = viewHoder.getAppCompatImageView(R.id.iv_icon);
        AppCompatTextView tvTitle = viewHoder.getAppCompatTextView(R.id.tv_title);
        if (item.isIscheck()) {
            icon.setImageResource(R.drawable.icon_check1);
        } else {
            icon.setImageResource(R.drawable.icon_nocheck1);
        }
        tvTitle.setText(item.getTitle());


        icon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (onDocumentTypeListener != null) {
                                            onDocumentTypeListener.onDocumentTypeItem(item);
                                        }
                                    }
                                }
        );
    }

    public void setOnDocumentTypeListener(OnDocumentTypeListener onDocumentTypeListener) {
        this.onDocumentTypeListener = onDocumentTypeListener;
    }

    OnDocumentTypeListener onDocumentTypeListener;

    //单选
    public void setCheckPositon(int position) {
        List<DocumenTtypeBean> list = getDatas();
        for (int i = 0; i < getDatas().size(); i++) {
            if (position == i) {
                getDatas().get(i).setIscheck(true);
            } else {
                getDatas().get(i).setIscheck(false);
            }
        }
        notifyDataSetChanged();
    }

    public DocumenTtypeBean getPosition() {
        DocumenTtypeBean documenTtypeBean=null;
        List<DocumenTtypeBean> list = getDatas();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isIscheck()) {
                documenTtypeBean=list.get(i);
            }
        }

        return documenTtypeBean;
    }

    public interface OnDocumentTypeListener {
        void onDocumentTypeItem(DocumenTtypeBean item);
    }


}
