package com.postit.postit_.Objects;

public class course {
    private String college;
    private String majorName;
    private String courseName;
    private String id;

    public course() {
    }

    public course(String college, String majorName, String courseName, String id) {
        this.college = college;
        this.majorName = majorName;
        this.courseName = courseName;
        this.id = id;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
