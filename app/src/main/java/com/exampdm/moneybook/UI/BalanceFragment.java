package com.exampdm.moneybook.UI;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.exampdm.moneybook.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;


public class BalanceFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    private boolean fromTodate=true;
    private TextView dateFromView;
    private TextView dateToView;
    private FragmentManager fm;
    public static final int REQUEST_CODE = 11;
    public BalanceFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        dateFromView= view.findViewById(R.id.span_start);
        dateToView= view.findViewById(R.id.span_end);
        // Inflate the layout for this fragment
       fm = ((AppCompatActivity)getActivity()).getSupportFragmentManager();

       dateFromView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               showDatePickerDialog(v);
           }
       });

        dateToView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });


        return view;
    }

    public void showDatePickerDialog(View v) {
        fromTodate = v == dateFromView;

        DialogFragment newFragment = new StatisticDatePicker();
        newFragment.setTargetFragment(BalanceFragment.this,0);
        newFragment.show(fm, "datePicker");
    }

    /*cattura la data selezionata con il datePicker*/
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.clear();

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, dayOfMonth);

        java.util.Date utilDate = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy",
                Locale.ITALIAN);

        String temp = dateFormat.format(utilDate);

        if(fromTodate) dateFromView.setText(temp);
        else dateToView.setText(temp);


    }



}
