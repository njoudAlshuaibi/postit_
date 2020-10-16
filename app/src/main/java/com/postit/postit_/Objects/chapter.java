package com.postit.postit_.Objects;

public class chapter {
    private String college;
    private String major;
    private String course;
    private String chapterNum;
    private String id;

    public chapter() {
    }

    public chapter(String college, String major, String course, String chapterNum, String id) {
        this.college = college;
        this.major = major;
        this.course = course;
        this.chapterNum = chapterNum;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
