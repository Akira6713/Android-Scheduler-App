package Models;

import android.provider.ContactsContract;

import java.io.Serializable;

public class Note implements Serializable {
    private int id;
    private String desc;
    private int courseID;

    public Note(){

    }

    public Note(String desc, int courseID){
        this.desc = desc;
        this.courseID = courseID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
