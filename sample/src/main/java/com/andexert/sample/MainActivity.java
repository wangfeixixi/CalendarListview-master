package com.andexert.sample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.andexert.calendarlistview.library.DayPickerView;
import com.andexert.calendarlistview.library.SimpleMonthAdapter;


public class MainActivity extends Activity implements com.andexert.calendarlistview.library.DatePickerController {

    private DayPickerView dayPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dayPickerView = (DayPickerView) findViewById(R.id.pickerView);
        dayPickerView.setController(this);
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {
        Log.e("aaaaDay Selected", day + " / " + month + " / " + year);
//        Toast.makeText(this, day + " / " + month + " / " + year, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {
        Log.e("aaaaDate range selected", selectedDays.getFirst().toString() + " --> " + selectedDays.getLast().toString());
        Toast.makeText(this, selectedDays.getFirst().toString() + " --> " + selectedDays.getLast().toString(), Toast.LENGTH_SHORT).show();
    }
}
