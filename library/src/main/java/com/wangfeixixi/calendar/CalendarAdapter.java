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
    private final DayListenr datePickerListenr;
    private final Calendar calendar;
    private final Integer firstMonth;

    private CalendarDay firstDay;
    private CalendarDay lastDay;


    public CalendarAdapter(Context context, DayListenr datePickerController, TypedArray typedArray) {
        this.typedArray = typedArray;
        calendar = Calendar.getInstance();
        firstMonth = typedArray.getInt(R.styleable.CalendarView_firstMonth, calendar.get(Calendar.MONTH));
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
        final MonthView monthView = viewHolder.monthView;
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

        if (firstDay != null) {
            selectedFirstDay = firstDay.day;
            selectedFirstMonth = firstDay.month;
            selectedFirstYear = firstDay.year;
        }

        if (lastDay != null) {
            selectedLastDay = lastDay.day;
            selectedLastMonth = lastDay.month;
            selectedLastYear = lastDay.year;
        }

        monthView.reuse();

        drawingParams.put(MonthView.VIEW_PARAMS_SELECTED_BEGIN_YEAR, selectedFirstYear);
        drawingParams.put(MonthView.VIEW_PARAMS_SELECTED_LAST_YEAR, selectedLastYear);
        drawingParams.put(MonthView.VIEW_PARAMS_SELECTED_BEGIN_MONTH, selectedFirstMonth);
        drawingParams.put(MonthView.VIEW_PARAMS_SELECTED_LAST_MONTH, selectedLastMonth);
        drawingParams.put(MonthView.VIEW_PARAMS_SELECTED_BEGIN_DAY, selectedFirstDay);
        drawingParams.put(MonthView.VIEW_PARAMS_SELECTED_LAST_DAY, selectedLastDay);
        drawingParams.put(MonthView.VIEW_PARAMS_YEAR, year);
        drawingParams.put(MonthView.VIEW_PARAMS_MONTH, month);
        drawingParams.put(MonthView.VIEW_PARAMS_WEEK_START, calendar.getFirstDayOfWeek());
        monthView.setMonthParams(drawingParams);
        monthView.invalidate();
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
            setSelectedDay(new CalendarDay(System.currentTimeMillis()));
    }

    public void onDayClick(MonthView monthView, CalendarDay calendarDay) {
        if (calendarDay != null) {
            setSelectedDay(calendarDay);
        }
    }

    public void setSelectedDay(CalendarDay calendarDay) {
        datePickerListenr.onDayClick(calendarDay);
        if (firstDay != null && lastDay == null) {
            lastDay = calendarDay;
            datePickerListenr.onDaysSelected(firstDay, lastDay);
        } else if (lastDay != null) {
            firstDay = calendarDay;
            lastDay = null;
        } else
            firstDay = calendarDay;

        notifyDataSetChanged();
    }

    public void clear() {
        firstDay = null;
        lastDay = null;
        notifyDataSetChanged();

    }
}