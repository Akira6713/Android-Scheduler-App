package Models;

import java.io.Serializable;

public class Assessment implements Serializable {
    private int id;
    private String description;
    private String title;
    private String date;
    private int assessmentCourse;
    private String category;

    public Assessment(){

    }

    public Assessment(String description, int assessmentCourse, String category, String title, String date){
        this.description = description;
        this.assessmentCourse = assessmentCourse;
        this.category = category;
        this.title = title;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAssessmentCourse() {
        return assessmentCourse;
    }

    public void setAssessmentCourse(int assessmentCourse) {
        this.assessmentCourse = assessmentCourse;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
