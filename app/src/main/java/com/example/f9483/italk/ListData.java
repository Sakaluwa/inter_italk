package com.example.f9483.italk;

/**
 * Created by 94839 on 2016/4/17.
 */
//数据封装
public class ListData {
    private String  content;
    //常量用户
    public static final int SEND=1;
    //常量图灵
    public static final int RECEIVER=2;
    //标识
    private int flag;
    private String time;

    public ListData(String content,int flag,String time){
        setContent(content);
        setFlag(flag);
        setTime(time);

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
