package com.postit.postit_;

public class note {
    private String college;
    private String major;
    private String course;
    private int chapterNum;
    private String title;
    private String caption;
    private String email;

    public note(String college, String major, String course, int chapterNum, String title, String caption, String email) {
        this.college = college;
        this.major = major;
        this.course = course;
        this.chapterNum = chapterNum;
        this.title = title;
        this.caption = caption;
        this.email = email;
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

    public int getChapterNum() {
        return chapterNum;
    }

    public void setChapterNum(int chapterNum) {
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
}