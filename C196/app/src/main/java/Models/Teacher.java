package Models;

import java.io.Serializable;

public class Teacher implements Serializable {
    private int id;
    private String phoneNumber;
    private String emailAddress;
    private String teacherPersonName;
    private int courseID;

    public Teacher(){

    }

    public Teacher(String teacherPersonName, String phoneNumber, String emailAddress, int courseID ){
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.teacherPersonName = teacherPersonName;
        this.courseID = courseID;
    }

    public Teacher(int id, String phoneNumber, String emailAddress, String teacherPersonName){
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.teacherPersonName = teacherPersonName;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getTeacherPersonName() {
        return teacherPersonName;
    }

    public void setTeacherPersonName(String teacherPersonName) {
        this.teacherPersonName = teacherPersonName;
    }
}
