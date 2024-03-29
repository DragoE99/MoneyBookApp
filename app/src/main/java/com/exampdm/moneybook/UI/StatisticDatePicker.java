package com.exampdm.moneybook.UI;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;


import java.util.Calendar;

public class StatisticDatePicker extends DialogFragment {
    private DatePickerDialog.OnDateSetListener dateSetListener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c =Calendar.getInstance();
        int year= c.get(Calendar.YEAR);
        int month= c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        dateSetListener = (DatePickerDialog.OnDateSetListener)getTargetFragment();

        return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
    }
}
