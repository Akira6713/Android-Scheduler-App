package Models;

import java.io.Serializable;

public class Course implements Serializable {

    private int id;
    private String name;
    private String startDate;
    private String endDate;
    private String courseStatus;
    private String courseTerm;

    public Course(){

    }

    public Course(String name, String startDate, String endDate, String courseStatus, String courseTerm){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = courseStatus;
        this.courseTerm = courseTerm;
    }



    public Course(int id, String name, String startDate, String endDate, int assesmentIDFK, String courseStatus){
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = courseStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCourseTerm() {
        return courseTerm;
    }

    public void setCourseTerm(String courseTerm) {
        this.courseTerm = courseTerm;
    }
}
