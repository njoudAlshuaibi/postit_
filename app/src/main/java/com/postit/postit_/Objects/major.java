package com.postit.postit_.Objects;

public class major {
    private String collegeNmae;
    private String majorName;
    private String majorID;

    public major() {
    }

    public major(String collegeNmae, String majorName, String majorID) {
        this.collegeNmae = collegeNmae;
        this.majorName = majorName;
        this.majorID = majorID;
    }

    public major(Object value) {
    }

    public String getCollegeNmae() {
        return collegeNmae;
    }

    public void setCollegeNmae(String collegeNmae) {
        this.collegeNmae = collegeNmae;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getMajorID() {
        return majorID;
    }

    public void setMajorID(String majorID) {
        this.majorID = majorID;
    }

    @Override
    public String toString() {
        return "major{" +
                "collegeNmae='" + collegeNmae + '\'' +
                ", majorName='" + majorName + '\'' +
                ", majorID='" + majorID + '\'' +
                '}';
    }
}
