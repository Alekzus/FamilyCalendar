package fr.alexandremarcq.familycalendar.date;

public class Date {

    private int mNumber;
    private String mDay;

    public Date(int number, String day) {
        mNumber = number;
        mDay = day;
    }

    public int getNumber() {
        return this.mNumber;
    }

    public void setNumber(int number) {
        this.mNumber = number;
    }

    public String getDay() {
        return this.mDay;
    }

    public void setDay(String day) {
        this.mDay = day;
    }
}
