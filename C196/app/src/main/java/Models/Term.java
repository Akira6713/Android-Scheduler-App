package Models;

import java.io.Serializable;

public class Term implements Serializable {
    private String termTitle;
    private String startDate;
    private String endDate;
    private int id;
    public Term(){

    }

    public Term(int id, String termTitle, String startDate, String endDate){
        this.id = id;
        this.termTitle = termTitle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Term(String termTitle, String startDate, String endDate){
        this.termTitle = termTitle;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
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


}