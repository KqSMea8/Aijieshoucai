package com.example.rongjiaying.aijieshoucai.message.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.rongjiaying.aijieshoucai.BaseActivity;
import com.example.rongjiaying.aijieshoucai.R;
import com.example.rongjiaying.aijieshoucai.interfac.OnExserviceQuestionItemListener;
import com.example.rongjiaying.aijieshoucai.message.adapter.ExserviceAdapter;
import com.example.rongjiaying.aijieshoucai.message.bean.ExserviceBean;
import com.example.rongjiaying.aijieshoucai.message.bean.TuLingBean;
import com.example.rongjiaying.aijieshoucai.my.bean.CommentQuestionListBean;
import com.example.rongjiaying.aijieshoucai.util.HttpUtil;
import com.example.rongjiaying.aijieshoucai.util.Judge;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 联系客服
 */
public class ExserviceActivity extends BaseActivity implements View.OnClickListener {
    AppCompatEditText etQuestion;

    @Override
    public AppCompatActivity getActivity() {
        return ExserviceActivity.this;
    }

    ExserviceAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exservice);
        findViewById(R.id.iv_back).setVisibility(View.GONE);
        AppCompatTextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("联系客服");
        exserviceBeans = new ArrayList<>();

        etQuestion = findViewById(R.id.et_question);
        findViewById(R.id.iv_addquestion).setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ExserviceAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        initQuestionData();


        adapter.setOnExserviceQuestionItemListener(new OnExserviceQuestionItemListener() {
            @Override
            public void onExserviceQuestion(CommentQuestionListBean commentQuestionListBean) {
                ExserviceBean exserviceBean = new ExserviceBean();
                exserviceBean.setMessage(commentQuestionListBean.getProblem());
                exserviceBean.setType(2);
                exserviceBean.setCreateTime(new Date().getTime());
                exserviceBeans.add(exserviceBean);
                adapter.getData().add(exserviceBean);
                adapter.notifyItemChanged(adapter.getData().size() - 1, exserviceBean);
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                uploadData(commentQuestionListBean.getProblem());
            }

            //换一换问题
            @Override
            public void onCheckQuestion() {
                if (((cquestions.size() * questionpage) + cquestions.size()) > commentQuestionListBeans.size()) {

                    if (questionpage * cquestions.size() - commentQuestionListBeans.size() > 0) {
                        Toast.makeText(getActivity(), "已经没有其他问题了", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ExserviceBean exserviceBean = new ExserviceBean();
                    exserviceBean.setCommentQuestionListBeans(commentQuestionListBeans.subList(questionpage * cquestions.size(), commentQuestionListBeans.size()));
                    questionpage = questionpage + 1;
                    exserviceBean.setType(0);
                    exserviceBean.setCreateTime(new Date().getTime());
                    exserviceBeans.add(exserviceBean);
                    adapter.getData().add(exserviceBean);
                    adapter.notifyItemChanged(adapter.getData().size() - 1, exserviceBean);
                    recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                } else {
                    cquestions = commentQuestionListBeans.subList(cquestions.size() * questionpage, (cquestions.size() * questionpage) + cquestions.size());
                    Log.i("asdf", "" + commentQuestionListBeans.size());
                    Log.i("asdf", "" + cquestions.size());
                    ExserviceBean exserviceBean = new ExserviceBean();
                    if (commentQuestionListBeans.size() - cquestions.size() > cquestions.size()) {
                        cquestions = commentQuestionListBeans.subList(cquestions.size() * questionpage, cquestions.size() * questionpage + cquestions.size());
                        exserviceBean.setCommentQuestionListBeans(cquestions);
                    } else {
                        exserviceBean.setCommentQuestionListBeans(commentQuestionListBeans.subList(questionpage * cquestions.size(), commentQuestionListBeans.size()));
                    }
                    questionpage = questionpage + 1;
                    exserviceBean.setType(0);
                    exserviceBean.setCreateTime(new Date().getTime());
                    exserviceBeans.add(exserviceBean);
                    adapter.getData().add(exserviceBean);
                    adapter.notifyItemChanged(adapter.getData().size() - 1, exserviceBean);
                    recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                }


            }
        });
    }

    List<ExserviceBean> exserviceBeans;//对话列表
    List<CommentQuestionListBean> commentQuestionListBeans;//总的问题列表
    List<CommentQuestionListBean> cquestions;//分页的问题列表
    int questionpage = 1;

    private void initQuestionData() {
        HttpUtil.commentquestion(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                try {
                    org.json.JSONObject jsonObject = new org.json.JSONObject(response.body());
                    commentQuestionListBeans = JSONObject.parseArray(jsonObject.getString("msg"), CommentQuestionListBean.class);
                    if (commentQuestionListBeans != null && commentQuestionListBeans.size() > 0) {
                        ExserviceBean exserviceBean = new ExserviceBean();
                        if (commentQuestionListBeans.size() > 3) {
                            cquestions = commentQuestionListBeans.subList(0, 3);
                            exserviceBean.setCommentQuestionListBeans(cquestions);
                        } else {
                            exserviceBean.setCommentQuestionListBeans(commentQuestionListBeans);
                        }

                        exserviceBean.setType(0);
                        exserviceBean.setCreateTime(new Date().getTime());
                        exserviceBeans.add(exserviceBean);
                        adapter.refreshData(exserviceBeans);

                    }
                } catch (Exception e) {
                    Log.i("", "");
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Log.i("", "");
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_addquestion:
                if (Judge.getBoolean_isNull(etQuestion.getText().toString())) {
                    Toast.makeText(getActivity(), "请输入常见问题", Toast.LENGTH_SHORT).show();
                } else {

                    ExserviceBean exserviceBean = new ExserviceBean();
                    exserviceBean.setMessage(etQuestion.getText().toString());
                    exserviceBean.setType(2);
                    exserviceBean.setCreateTime(new Date().getTime());
                    exserviceBeans.add(exserviceBean);
                    adapter.getData().add(exserviceBean);
                    adapter.notifyItemChanged(adapter.getData().size() - 1, exserviceBean);
                    recyclerView.scrollToPosition(adapter.getItemCount() - 1);

                    uploadData(etQuestion.getText().toString());
                    etQuestion.setText("");
                }
                break;
        }
    }

    private void uploadData(String message) {
        //   String s="{\"reqType\":0,\"perception\": {\"inputText\": {\"text\": \"其他问题\"},},\"userInfo\": {\"apiKey\": \"ce395eb19b2d4483906054dc5dbe5a19\",\"userId\": \"11\"}}";

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> perceptionmap = new HashMap<>();
        Map<String, Object> userinfomap = new HashMap<>();
        userinfomap.put("apiKey", getString(R.string.string_tulingkey));
        userinfomap.put("userId", "11");
        Map<String, Object> inputTextmap = new HashMap<>();
        inputTextmap.put("text", message);
        perceptionmap.put("inputText", new org.json.JSONObject(inputTextmap));
        map.put("reqType", 0);
        map.put("perception", new org.json.JSONObject(perceptionmap));
        map.put("userInfo", new org.json.JSONObject(userinfomap));
        Log.i("asdf", new org.json.JSONObject(map).toString());
        OkGo.<String>post(getString(R.string.string_tulingurl))
                .tag("tuling")
                .upJson(new org.json.JSONObject(map))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i("asdf", "tuling" + response.body());
                        try {

                            TuLingBean tuLingBean = JSONObject.parseObject(response.body(), TuLingBean.class);
                            if (tuLingBean != null) {
                                ExserviceBean exserviceBean = new ExserviceBean();
                                exserviceBean.setMessage(tuLingBean.getResults().get(0).getValues().getText());
                                exserviceBean.setType(1);
                                exserviceBean.setCreateTime(new Date().getTime());
                                exserviceBeans.add(exserviceBean);
                                adapter.getData().add(exserviceBean);
                                adapter.notifyItemChanged(adapter.getData().size() - 1, exserviceBean);
                                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                            }

                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.i("asdf", "tulingerr" + response.body());
                    }
                });
    }
}
