package com.andexert.sample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wangfeixixi.calendar.CalendarDay;
import com.wangfeixixi.calendar.DatePickerListenr;
import com.wangfeixixi.calendar.CalendarView;
import com.wangfeixixi.calendar.CalendarAdapter;
import com.wangfeixixi.calendar.SelectedDays;


public class MainActivity extends Activity implements DatePickerListenr {

    private CalendarView dayPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dayPickerView = (CalendarView) findViewById(R.id.pickerView);
        dayPickerView.setController(this);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayPickerView.clear();
            }
        });

    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {
        Log.e("aaaaDay Selected", day + " / " + month + " / " + year);
//        Toast.makeText(this, day + " / " + month + " / " + year, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateRangeSelected(SelectedDays<CalendarDay> selectedDays) {
        Log.e("aaaaDate range selected", selectedDays.getFirst().toString() + " --> " + selectedDays.getLast().toString());
        Toast.makeText(this, selectedDays.getFirst().toString() + " --> " + selectedDays.getLast().toString(), Toast.LENGTH_SHORT).show();
    }
}
