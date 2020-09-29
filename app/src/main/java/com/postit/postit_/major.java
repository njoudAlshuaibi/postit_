package com.postit.postit_;

public class major {
    private String collegeNmae;
    private String majorName;
    private int cou=1;


    public String getCollegeNmae() {
        return collegeNmae;
    }

    public void setCollegeNmae(String collegeNmae) {
        this.collegeNmae = collegeNmae;
    }

    public String getmajorName() {
        return majorName;
    }

    public void setmajorName(String majorName) {
        this.majorName = cou+majorName;

    }

    public major(String collegeNmae, String majorName) {
        this.collegeNmae = collegeNmae;
        cou++;
        this.majorName = majorName;

    }
}
