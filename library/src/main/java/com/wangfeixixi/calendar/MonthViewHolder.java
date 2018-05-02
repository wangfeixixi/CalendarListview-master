package com.wangfeixixi.calendar;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

public class MonthViewHolder extends RecyclerView.ViewHolder {
    final MonthView monthView;

    public MonthViewHolder(View itemView, OnDayClickListener onDayClickListener) {
        super(itemView);
        monthView = (MonthView) itemView;
        monthView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        monthView.setClickable(true);
        monthView.setOnDayClickListener(onDayClickListener);
    }
}