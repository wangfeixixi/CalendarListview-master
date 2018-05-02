package com.wangfeixixi.calendar;

public interface DayListenr {
    void onDayClick(CalendarDay firstDay);

    void onDaysSelected(CalendarDay firstDay, CalendarDay lastDay);

}