package com.uniquedu.myeventbus;

/**
 * Created by ZhongHang on 2016/5/5.
 */
public class EventMessage {
    private String content;
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    enum Type {
        TYPE_ACTIVITY,
        TYPE_SERVICE
    }

    public EventMessage(String content, Type type) {
        this.content = content;
        this.type = type;
    }

    public EventMessage(String content) {
        this.content = content;
    }

    public EventMessage() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
