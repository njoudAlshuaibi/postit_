package com.postit.postit_.Objects;

public class rate {
    private String id,noteid, userid,rate1;

    public rate(String id, String noteid, String userid,String rate1) {
        this.id = id;
        this.noteid = noteid;
        this.userid = userid;
        this.rate1 = rate1;
    }

    public rate() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getRate1() {
        return rate1;
    }

    public void setRate1(String rate1) {
        this.rate1 = rate1;
    }

    public String getNoteid() {
        return noteid;
    }

    public void setNoteid(String noteid) {
        this.noteid = noteid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
