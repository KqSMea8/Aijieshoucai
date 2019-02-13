package com.example.rongjiaying.aijieshoucai.my.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 常见问题
 */
public class CommentQuestionListBean implements Parcelable {


    /**
     * id : 1
     * problem : 录单业务审核及放款流程
     * answer : problem1_a.jpg;problem1_b.jpg
     * memo : 重要
     * characterAnswer : 发士大夫
     */

    private int id;
    private String problem;
    private String answer;
    private String memo;
    private String characterAnswer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCharacterAnswer() {
        return characterAnswer;
    }

    public void setCharacterAnswer(String characterAnswer) {
        this.characterAnswer = characterAnswer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.problem);
        dest.writeString(this.answer);
        dest.writeString(this.memo);
        dest.writeString(this.characterAnswer);
    }

    public CommentQuestionListBean() {
    }

    protected CommentQuestionListBean(Parcel in) {
        this.id = in.readInt();
        this.problem = in.readString();
        this.answer = in.readString();
        this.memo = in.readString();
        this.characterAnswer = in.readString();
    }

    public static final Creator<CommentQuestionListBean> CREATOR = new Creator<CommentQuestionListBean>() {
        @Override
        public CommentQuestionListBean createFromParcel(Parcel source) {
            return new CommentQuestionListBean(source);
        }

        @Override
        public CommentQuestionListBean[] newArray(int size) {
            return new CommentQuestionListBean[size];
        }
    };
}
