/***********************************************************************************
 * The MIT License (MIT)

 * Copyright (c) 2014 Robin Chutaux

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ***********************************************************************************/
package com.wangfeixixi.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;


import java.util.Calendar;
import java.util.HashMap;

public class CalendarAdapter extends RecyclerView.Adapter<MonthViewHolder> implements OnDayClickListener {
    protected static final int MONTHS_IN_YEAR = 12;
    private final TypedArray typedArray;
    private final Context mContext;
    private final DatePickerListenr datePickerListenr;
    private final Calendar calendar;
    private final SelectedDays<CalendarDay> selectedDays;
    private final Integer firstMonth;
    private final Integer lastMonth;

    public CalendarAdapter(Context context, DatePickerListenr datePickerController, TypedArray typedArray) {
        this.typedArray = typedArray;
        calendar = Calendar.getInstance();
        firstMonth = typedArray.getInt(R.styleable.CalendarView_firstMonth, calendar.get(Calendar.MONTH));
        lastMonth = typedArray.getInt(R.styleable.CalendarView_lastMonth, (calendar.get(Calendar.MONTH) - 1) % MONTHS_IN_YEAR);
        selectedDays = new SelectedDays<>();
        mContext = context;
        datePickerListenr = datePickerController;
        init();
    }

    @Override
    public MonthViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final MonthView simpleMonthView = new MonthView(mContext, typedArray, i);
        return new MonthViewHolder(simpleMonthView, this);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(MonthViewHolder viewHolder, int position) {
        final MonthView v = viewHolder.monthView;
        final HashMap<String, Integer> drawingParams = new HashMap<String, Integer>();
        int month;
        int year;

        month = (firstMonth + (position % MONTHS_IN_YEAR)) % MONTHS_IN_YEAR;
        year = position / MONTHS_IN_YEAR + calendar.get(Calendar.YEAR) + ((firstMonth + (position % MONTHS_IN_YEAR)) / MONTHS_IN_YEAR);

        int selectedFirstDay = -1;
        int selectedLastDay = -1;
        int selectedFirstMonth = -1;
        int selectedLastMonth = -1;
        int selectedFirstYear = -1;
        int selectedLastYear = -1;

        if (selectedDays.getFirst() != null) {
            selectedFirstDay = selectedDays.getFirst().day;
            selectedFirstMonth = selectedDays.getFirst().month;
            selectedFirstYear = selectedDays.getFirst().year;
        }

        if (selectedDays.getLast() != null) {
            selectedLastDay = selectedDays.getLast().day;
            selectedLastMonth = selectedDays.getLast().month;
            selectedLastYear = selectedDays.getLast().year;
        }

        v.reuse();

        drawingParams.put(MonthView.VIEW_PARAMS_SELECTED_BEGIN_YEAR, selectedFirstYear);
        drawingParams.put(MonthView.VIEW_PARAMS_SELECTED_LAST_YEAR, selectedLastYear);
        drawingParams.put(MonthView.VIEW_PARAMS_SELECTED_BEGIN_MONTH, selectedFirstMonth);
        drawingParams.put(MonthView.VIEW_PARAMS_SELECTED_LAST_MONTH, selectedLastMonth);
        drawingParams.put(MonthView.VIEW_PARAMS_SELECTED_BEGIN_DAY, selectedFirstDay);
        drawingParams.put(MonthView.VIEW_PARAMS_SELECTED_LAST_DAY, selectedLastDay);
        drawingParams.put(MonthView.VIEW_PARAMS_YEAR, year);
        drawingParams.put(MonthView.VIEW_PARAMS_MONTH, month);
        drawingParams.put(MonthView.VIEW_PARAMS_WEEK_START, calendar.getFirstDayOfWeek());
        v.setMonthParams(drawingParams);
        v.invalidate();
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 3;

    }

    protected void init() {
        if (typedArray.getBoolean(R.styleable.CalendarView_currentDaySelected, false))
            onDayTapped(new CalendarDay(System.currentTimeMillis()));
    }

    public void onDayClick(MonthView simpleMonthView, CalendarDay calendarDay) {
        if (calendarDay != null) {
            onDayTapped(calendarDay);
        }
    }

    protected void onDayTapped(CalendarDay calendarDay) {
        datePickerListenr.onDayOfMonthSelected(calendarDay.year, calendarDay.month, calendarDay.day);
        setSelectedDay(calendarDay);
    }

    public void setSelectedDay(CalendarDay calendarDay) {
        if (calendarDay == null) {

            notifyDataSetChanged();
            return;
        }

        if (selectedDays.getFirst() != null && selectedDays.getLast() == null) {
            selectedDays.setLast(calendarDay);

            if (selectedDays.getFirst().month < calendarDay.month) {
                for (int i = 0; i < selectedDays.getFirst().month - calendarDay.month - 1; ++i)
                    datePickerListenr.onDayOfMonthSelected(selectedDays.getFirst().year, selectedDays.getFirst().month + i, selectedDays.getFirst().day);
            }

            datePickerListenr.onDateRangeSelected(selectedDays);
        } else if (selectedDays.getLast() != null) {
            selectedDays.setFirst(calendarDay);
            selectedDays.setLast(null);
        } else
            selectedDays.setFirst(calendarDay);

        notifyDataSetChanged();
    }

    public SelectedDays<CalendarDay> getSelectedDays() {
        return selectedDays;
    }
}