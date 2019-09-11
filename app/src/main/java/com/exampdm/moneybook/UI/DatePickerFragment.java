package com.exampdm.moneybook.UI;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.exampdm.moneybook.NewItemActivity;

import java.util.Calendar;
import java.util.Objects;

public class DatePickerFragment extends DialogFragment {
    // implements DatePickerDialog.OnDateSetListener

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c =Calendar.getInstance();
        int year= c.get(Calendar.YEAR);
        int month= c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(Objects.requireNonNull(getActivity()), (NewItemActivity)getActivity(), year, month, day);
    }



}
