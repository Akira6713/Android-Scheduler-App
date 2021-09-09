package UtilityClassess;

public class DateComparator {
    public boolean compareDates(String endDate, String startDate) {
        String[] startDateSplitted = startDate.split("/");
        String[] endDateSplitted = endDate.split("/");
        int startMonth = Integer.valueOf(startDateSplitted[0]);
        int endMonth = Integer.valueOf(endDateSplitted[0]);
        int startDay = Integer.valueOf(startDateSplitted[1]);
        int endDay = Integer.valueOf(endDateSplitted[1]);
        int startYear = Integer.valueOf(startDateSplitted[2]);
        int endYear = Integer.valueOf(endDateSplitted[2]);
        if((startYear < endYear) || (startYear == endYear) && (endMonth > startMonth) || (startYear == endYear) && (endMonth == startMonth) && (endDay > startDay)){
            return true;
        }
        return false;
    }

    public String updateDate(int year, int month, int dateOfMonth){
        String aDate = new StringBuilder().append(month+1).append('/').append(dateOfMonth).append('/').append(year).toString();
        return aDate;
    }
}
