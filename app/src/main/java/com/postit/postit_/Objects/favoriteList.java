package com.postit.postit_.Objects;

public class favoriteList {
    private String college;
    private String major;
    private String course;
    private String chapterNum;
    private String title;
    private String caption;
    private String email;
    private String id;
    private String nid;
    private String date;
    private String userID;
    private float rate;
    private float allrates;
    private int ratingCount;
    private int color;

    public favoriteList() {
    }

    public favoriteList(String college, String major, String course, String chapterNum, String title, String caption, String email, String id, String nid, String date, String userID, float rate,float allrates, int ratingCount, int color) {
        this.college = college;
        this.major = major;
        this.course = course;
        this.chapterNum = chapterNum;
        this.title = title;
        this.caption = caption;
        this.email = email;
        this.id = id;
        this.nid = nid;
        this.date = date;
        this.userID = userID;
        this.rate = rate;
        this.allrates = allrates;
        this.ratingCount = ratingCount;
    }



    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getChapterNum() {
        return chapterNum;
    }

    public void setChapterNum(String chapterNum) {
        this.chapterNum = chapterNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getAllrates() {
        return allrates;
    }

    public void setAllrates(float allrates) {
        this.allrates = allrates;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }
}
