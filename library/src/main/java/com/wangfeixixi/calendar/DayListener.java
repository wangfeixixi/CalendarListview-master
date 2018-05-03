package com.wangfeixixi.calendar;

public interface DayListener {
    void onDayClick(CalendarDay firstDay);

    void onDaysSelected(CalendarDay firstDay, CalendarDay lastDay);

}