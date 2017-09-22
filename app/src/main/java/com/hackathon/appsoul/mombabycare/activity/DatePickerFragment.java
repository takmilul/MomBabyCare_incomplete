package com.hackathon.appsoul.mombabycare.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    private TextView datePicker;
    private String date1;
    public Calendar calendar;
    public SimpleDateFormat formatDate;

    public DatePickerFragment(){}
    public DatePickerFragment(TextView datePicker) {
        this.datePicker = datePicker;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar justDate = Calendar.getInstance();
        int year = justDate.get(Calendar.YEAR);
        int month = justDate.get(Calendar.MONTH);
        int day = justDate.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        calendar = Calendar.getInstance();
        calendar.set(year,monthOfYear,dayOfMonth);
        date1 = formatDate.format(calendar.getTime());
        datePicker.setText(date1);
    }
}

