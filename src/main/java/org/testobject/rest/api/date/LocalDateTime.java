package org.testobject.rest.api.date;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;


public final class LocalDateTime {

    private final int hour, minute, second, year, dayOfMonth, dayOfYear, monthValue, nano;
    private final String month;
    private final String dayOfWeek;
    private final Chronology chronology;

    @JsonCreator
    public LocalDateTime(@JsonProperty("hour") int hour, @JsonProperty("minute") int minute, @JsonProperty("second") int second,
                         @JsonProperty("year") int year, @JsonProperty("month") String month,
                         @JsonProperty("dayOfMonth") int dayOfMonth, @JsonProperty("dayOfWeek") String dayOfWeek,
                         @JsonProperty("dayOfYear") int dayOfYear, @JsonProperty("monthValue") int monthValue,
                         @JsonProperty("nano") int nano, @JsonProperty("chronology") Chronology chronology) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.dayOfWeek = dayOfWeek;
        this.dayOfYear = dayOfYear;
        this.monthValue = monthValue;
        this.nano = nano;
        this.chronology = chronology;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public int getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public int getDayOfYear() {
        return dayOfYear;
    }

    public int getMonthValue() {
        return monthValue;
    }

    public int getNano() {
        return nano;
    }

    public Chronology getChronology() {
        return chronology;
    }

}



final class Chronology {
    private final String calendarType;
    private final String id;

    @JsonCreator
    public Chronology(@JsonProperty("calendarType") String calendarType, @JsonProperty("id") String id) {
        this.calendarType = calendarType;
        this.id = id;
    }

    public String getCalendarType() {
        return calendarType;
    }

    public String getId() {
        return id;
    }
}
