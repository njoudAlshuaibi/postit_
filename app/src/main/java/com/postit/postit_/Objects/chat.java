package com.postit.postit_.Objects;

public class chat {
    private  String id;
    private String msg;
    private String senderId;
    private String receiverId;
    private long msgTime;

    public chat() {
    }

    public chat(String id, String msg, String senderId, String receiverId, long msgTime) {
        this.id = id;
        this.msg = msg;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.msgTime = msgTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public long getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(long msgTime) {
        this.msgTime = msgTime;
    }
}
