package com.postit.postit_.Objects;

public class chat {
  //  private  String id;
    private String msg;
    private String senderId;
    private String receiverId;
    private String msgtime;


    public chat() {
    }

    public chat(String msg, String senderId, String receiverId,String msgtime) {
      //  this.id = id;
        this.msg = msg;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.msgtime = msgtime;
    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }

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

    public String getMsgTime() {
        return msgtime;
    }

    public void setMsgTime(String msgtime) {
        this.msgtime = msgtime;
    }
}
