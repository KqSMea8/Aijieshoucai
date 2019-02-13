package com.example.rongjiaying.aijieshoucai.order.bean;

public class OrderSingningBean {

    /**
     * id : 8
     * code : 7
     * process : 面签
     * processType : signing_process
     * sort : 1
     */

    private int id;
    private int code;
    private String process;
    private String processType;
    private int sort;
private boolean check=false;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
