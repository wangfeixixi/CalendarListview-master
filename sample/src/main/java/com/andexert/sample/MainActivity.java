package com.andexert.sample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wangfeixixi.calendar.CalendarDay;
import com.wangfeixixi.calendar.DayListener;
import com.wangfeixixi.calendar.CalendarView;

public class MainActivity extends Activity implements DayListener {

    private CalendarView dayPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dayPickerView = (CalendarView) findViewById(R.id.pickerView);
        dayPickerView.setListener(this);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayPickerView.clear();
            }
        });

    }

//    @Override
//    public void onDayClick(int year, int month, int day) {
//        Log.e("aaaa 000000000", day + " / " + month + " / " + year);
////        Toast.makeText(this, day + " / " + month + " / " + year, Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    public void onDaysSelected(SelectedDays<CalendarDay> selectedDays) {
//        Log.e("aaaa  daaaaaaaaaa", selectedDays.getFirst().toString() + " --> " + selectedDays.getLast().toString());
////        Toast.makeText(this, selectedDays.getFirst().toString() + " --> " + selectedDays.getLast().toString(), Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void onDayClick(CalendarDay firstDay) {
//        Log.e("outer", firstDay.toString());
    }

    @Override
    public void onDaysSelected(CalendarDay firstDay, CalendarDay lastDay) {

        Log.e("outer", firstDay.toString() + " --> " + lastDay.toString() + ": " + DateUtilsWWW.getStringByFormat(firstDay.getDate().getTime(), DateUtilsWWW.dateFormatYMD) + "-->" + DateUtilsWWW.getStringByFormat(firstDay.getDate().getTime(), DateUtilsWWW.dateFormatYMD));
//        Toast.makeText(this, firstDay.toString() + " --> " + lastDay.toString(), Toast.LENGTH_SHORT).show();
    }
}
