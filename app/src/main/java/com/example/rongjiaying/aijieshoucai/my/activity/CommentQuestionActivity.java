package com.example.rongjiaying.aijieshoucai.my.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.baseadapter.BaseAdapter;
import com.example.rongjiaying.aijieshoucai.my.adapter.CommentQuestionListAdapter;
import com.example.rongjiaying.aijieshoucai.my.bean.CommentQuestionListBean;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.IntentUtil;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * 常见问题
 */
public class CommentQuestionActivity extends BaseActivity implements View.OnClickListener {
    CommentQuestionListAdapter commentQuestionListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_question);
        findViewById(R.id.iv_back).setOnClickListener(this);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getString(R.string.string_commentquestion));

        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        commentQuestionListAdapter=new CommentQuestionListAdapter(getActivity());
        recyclerView.setAdapter(commentQuestionListAdapter);
        initData();

        commentQuestionListAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position!=-1)
                {
                    IntentUtil.Intent_CommentQuestionDetailActivity(getActivity()
                    ,commentQuestionListAdapter.getDatas().get(position));
                }
            }
        });
    }

    private void initData() {
        HttpUtil.commentquestion(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
          try {
              org.json.JSONObject jsonObject=new org.json.JSONObject(response.body());
              int code=jsonObject.getInt("code");
              String msg=jsonObject.getString("msg");
              if (code==0)
              {
                  List<CommentQuestionListBean>listBeans= JSONObject.parseArray(msg,CommentQuestionListBean.class);
                  commentQuestionListAdapter.refreshData(listBeans);
              }


          }catch (Exception e)
          {

          }
            }
        });
    }

    @Override
    public AppCompatActivity getActivity() {
        return CommentQuestionActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
