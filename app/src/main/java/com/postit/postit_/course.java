package com.postit.postit_;

public class course {
    private String college;
    private String majorName;
    private String courseName;

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getmajorName() {
        return majorName;
    }

    public void setmajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getcourseName() {
        return courseName;
    }

    public void setcourseName(String courseName) {
        this.courseName = courseName;
    }

    public course(String college, String majorName, String courseName) {
        this.college = college;
        this.majorName = majorName;
        this.courseName = courseName;
    }
}
