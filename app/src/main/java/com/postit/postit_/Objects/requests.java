package com.postit.postit_.Objects;

public class requests {
    private String requestedMajor;
    private String requestedCourse;
    private String requestedChapter;
    private String id;

    public requests() {
    }

    public requests(String requestedMajor, String requestedCourse, String requestedChapter, String id) {
        this.requestedMajor = requestedMajor;
        this.requestedCourse = requestedCourse;
        this.requestedChapter = requestedChapter;
        this.id = id;
    }

    public String getRequestedMajor() {
        return requestedMajor;
    }

    public void setRequestedMajor(String requestedMajor) {
        this.requestedMajor = requestedMajor;
    }

    public String getRequestedCourse() {
        return requestedCourse;
    }

    public void setRequestedCourse(String requestedCourse) {
        this.requestedCourse = requestedCourse;
    }

    public String getRequestedChapter() {
        return requestedChapter;
    }

    public void setRequestedChapter(String requestedChapter) {
        this.requestedChapter = requestedChapter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "requests{" +
                "requestedMajor='" + requestedMajor + '\'' +
                ", requestedCourse='" + requestedCourse + '\'' +
                ", requestedChapter='" + requestedChapter + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
