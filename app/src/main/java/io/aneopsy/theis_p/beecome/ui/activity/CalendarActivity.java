package io.aneopsy.theis_p.beecome.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.aneopsy.theis_p.beecome.R;
import io.aneopsy.theis_p.beecome.ui.view.RevealBackgroundView;

public class CalendarActivity extends BaseDrawerActivity implements RevealBackgroundView.OnStateChangeListener, OnDateSelectedListener, OnMonthChangedListener {
    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    @Bind(R.id.calendarView)
    MaterialCalendarView widget;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.vRevealBackground)
    RevealBackgroundView vRevealBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        setupRevealBackground(savedInstanceState);
        ButterKnife.bind(this);
        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);
        textView.setText(getSelectedDatesString());
    }
    private void setupRevealBackground(Bundle savedInstanceState) {
        vRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else {
            vRevealBackground.setToFinishedFrame();
        }
    }
    public static void startUserProfileFromLocation(int[] startingLocation, Activity startingActivity) {
        Intent intent = new Intent(startingActivity, CalendarActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActivity.startActivity(intent);
    }
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date, boolean selected) {
        textView.setText(getSelectedDatesString());
    }
    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        getSupportActionBar().setTitle(FORMATTER.format(date.getDate()));
    }
    private String getSelectedDatesString() {
        CalendarDay date = widget.getSelectedDate();
        if (date == null) {
            return " ";
        }
        return FORMATTER.format(date.getDate());
    }
    @Override
    public void onStateChange(int state) {}
}