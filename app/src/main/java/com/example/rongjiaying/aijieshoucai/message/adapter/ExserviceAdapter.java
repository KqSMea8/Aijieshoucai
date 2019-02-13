package com.example.rongjiaying.aijieshoucai.message.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseAdapter;
import com.example.rongjiaying.aijieshoucai.interfac.OnExserviceQuestionItemListener;
import com.example.rongjiaying.aijieshoucai.message.bean.ExserviceBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ExserviceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int Question = 0;//问题列表
    private final int lefeTitleItem = 1;//左边文字item
    private final int RightTitleItem = 2;//右边文字item

    protected List<ExserviceBean> datas;


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        SimpleDateFormat s = new SimpleDateFormat("HH:mm");
        if (viewHolder instanceof QuestionViewHolder) {
            QuestionViewHolder questionViewHolder = (QuestionViewHolder) viewHolder;
            questionViewHolder.tvTime.setText(s.format(new Date(datas.get(i).getCreateTime())));
            questionViewHolder.rvQuestion.setLayoutManager(new LinearLayoutManager(context));

            final ExserviceQuestionAdapter exserviceQuestionAdapter = new ExserviceQuestionAdapter(context);
            questionViewHolder.rvQuestion.setAdapter(exserviceQuestionAdapter);
            exserviceQuestionAdapter.refreshData(datas.get(i).getCommentQuestionListBeans());

            exserviceQuestionAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (position != -1) {
                        onExserviceQuestionItemListener.onExserviceQuestion(exserviceQuestionAdapter.getItem(position));
                    }
                }
            });

            //换一换
            questionViewHolder.tvCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onExserviceQuestionItemListener != null) {
                        onExserviceQuestionItemListener.onCheckQuestion();
                    }
                }
            });

        } else if (viewHolder instanceof LeftTitleViewHolder) {
            LeftTitleViewHolder leftTitleViewHolder = (LeftTitleViewHolder) viewHolder;
            leftTitleViewHolder.tvTime.setText(s.format(new Date(datas.get(i).getCreateTime())));
            leftTitleViewHolder.tvTitle.setText(datas.get(i).getMessage());
        } else if (viewHolder instanceof RightTitleViewHolder) {
            RightTitleViewHolder rightTitleViewHolder = (RightTitleViewHolder) viewHolder;
            rightTitleViewHolder.tvTime.setText(s.format(new Date(datas.get(i).getCreateTime())));
            rightTitleViewHolder.tvTitle.setText(datas.get(i).getMessage());
        }


    }

    private Context context;

    public void setOnExserviceQuestionItemListener(OnExserviceQuestionItemListener onExserviceQuestionItemListener) {
        this.onExserviceQuestionItemListener = onExserviceQuestionItemListener;
    }

    OnExserviceQuestionItemListener onExserviceQuestionItemListener;//问题点击相应

    public ExserviceAdapter(Context context) {
        this.datas = datas == null ? new ArrayList<ExserviceBean>() : datas;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (i == Question) {//如果viewType是轮播图就去创建轮播图的viewHolder
            view = getView(R.layout.item_exservice_question, viewGroup);
            return new QuestionViewHolder(view);
        } else if (i == lefeTitleItem) {
            view = getView(R.layout.item_exservice_lefttitle, viewGroup);
            return new LeftTitleViewHolder(view);
        } else if (i == RightTitleItem) {
            view = getView(R.layout.item_exservice_righttitle, viewGroup);
            return new RightTitleViewHolder(view);
        } else {
            view = getView(R.layout.item_exservice_lefttitle, viewGroup);
            return new LeftTitleViewHolder(view);
        }
    }

    /**
     * 用来引入布局的方法
     */
    private View getView(int view, ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(view, viewGroup, false);
    }

    /**
     * 获取item的类型
     *
     * @param position item的位置
     * @return item的类型
     * 有几种type就回在onCreateViewHolder方法中引入几种布局,也就是创建几个ViewHolder
     */
    @Override
    public int getItemViewType(int position) {
        /*
        区分item类型,返回不同的int类型的值
        在onCreateViewHolder方法中用viewType来创建不同的ViewHolder
         */
        int type = datas.get(position).getType();
        if (type == 0) {//第0个位置是轮播图
            return Question;
        } else if (type == 1) {//第一个是频道布局
            return lefeTitleItem;
        } else if (type == 2) {//第2个位置是美女布局
            return RightTitleItem;
        } else {
            return -1;
        }
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    public List<ExserviceBean> getData() {
        return datas;
    }

    /**
     * 问题viewholder
     */
    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rvQuestion;
        AppCompatTextView tvTime;
        AppCompatTextView tvCheck;

        QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            rvQuestion = itemView.findViewById(R.id.rv_question);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvCheck = itemView.findViewById(R.id.tv_check);
        }
    }

    /**
     * 左边文字标题viewholder
     */
    public class LeftTitleViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvTitle;
        AppCompatTextView tvTime;

        LeftTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }

    /**
     * 右边文字标题viewholder
     */
    public class RightTitleViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvTitle;
        AppCompatTextView tvTime;

        RightTitleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }

    public void refreshData(List<ExserviceBean> list) {

        clear();
        if (list != null && list.size() > 0) {


            int size = list.size();
            for (int i = 0; i < size; i++) {
                datas.add(i, list.get(i));
                notifyItemInserted(i);
            }

        }
    }

    public void clear() {
//        int itemCount = datas.size();
//        datas.clear();
//        this.notifyItemRangeRemoved(0,itemCount);

        if (datas == null || datas.size() <= 0)
            return;

        for (Iterator it = datas.iterator(); it.hasNext(); ) {

            ExserviceBean t = (ExserviceBean) it.next();
            int position = datas.indexOf(t);
            it.remove();
            notifyItemRemoved(position);
        }
    }
}
