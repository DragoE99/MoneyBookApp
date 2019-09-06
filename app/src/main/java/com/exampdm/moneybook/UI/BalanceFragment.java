package com.exampdm.moneybook.UI;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.exampdm.moneybook.R;
import com.exampdm.moneybook.db.entity.MoneyEntity;
import com.exampdm.moneybook.viewmodel.StatisticViewModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class BalanceFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private boolean fromToDate = true;
    private TextView dateFromView;
    private TextView dateToView;
    private TextView incomeTextView;
    private TextView expensesTextView;
    private TextView totalTextView;
    private FragmentManager fm;
    private List<MoneyEntity> itemInRange;
    private double income;
    private double expenses;

    private StatisticViewModel moneyViewModel;

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
        dateFromView = view.findViewById(R.id.span_start);
        dateToView = view.findViewById(R.id.span_end);
        incomeTextView = view.findViewById(R.id.income_textView);
        expensesTextView = view.findViewById(R.id.expanses_textView);
        totalTextView = view.findViewById(R.id.total_textView);

        // Inflate the layout for this fragment
        fm = (Objects.requireNonNull(getActivity())).getSupportFragmentManager();

        moneyViewModel = ViewModelProviders.of(getActivity()).get(StatisticViewModel.class);




        moneyViewModel.getFromDate().observe(this, new Observer<Date>() {
            @Override
            public void onChanged(Date date) {
                moneyViewModel.setMutableItemInRange();
                dateFromView.setText(dateToString(date));
                moneyViewModel.setMutableItemInRange();
            }
        });

        moneyViewModel.getToDate().observe(this, new Observer<Date>() {
            @Override
            public void onChanged(Date date) {
                moneyViewModel.setMutableItemInRange();
                dateToView.setText(dateToString(date));
                moneyViewModel.setMutableItemInRange();

            }
        });


        moneyViewModel.getItemBeetweenDate().observe(this, new Observer<List<MoneyEntity>>() {
            @Override
            public void onChanged(List<MoneyEntity> moneyEntities) {
                if(moneyEntities!=null) {
                    moneyViewModel.setItemeInRange(moneyEntities);
                    itemInRange = moneyEntities;
                }
            }
        });

        moneyViewModel.getIncome().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                income=aDouble;
                incomeTextView.setText(NumberFormat.getInstance().format(income));
            }
        });

        moneyViewModel.getExpenses().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                expenses=aDouble;
                expensesTextView.setText(NumberFormat.getInstance().format(expenses));
                totalTextView.setText(NumberFormat.getInstance().format(income+expenses));
            }
        });


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

    private void updtElement(){
        moneyViewModel.getItemBeetweenDate().observe(this, new Observer<List<MoneyEntity>>() {
            @Override
            public void onChanged(List<MoneyEntity> moneyEntities) {
                moneyViewModel.setItemeInRange(moneyEntities);
                itemInRange= moneyEntities;
            }
        });
    }

    private void showDatePickerDialog(View v) {
        fromToDate = v == dateFromView;

        DialogFragment newFragment = new StatisticDatePicker();
        newFragment.setTargetFragment(BalanceFragment.this, 0);
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

        String temp = dateToString(utilDate);

        if (fromToDate) {
            dateFromView.setText(temp);
            moneyViewModel.setFromDate(utilDate);

        } else {
            dateToView.setText(temp);
            moneyViewModel.setToDate(utilDate);
        }
        setBalanceFrg();
        moneyViewModel.setBalance();

    }

    private String dateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy",
                Locale.ITALIAN);
        return dateFormat.format(date);
    }

    private void setBalanceFrg(){
        double positive=(double)0;
        double negative= (double)0;
        if(itemInRange!=null) {
            for (MoneyEntity item : itemInRange
            ) {
                if (item.getAmount() > 0) {
                    positive = positive + item.getAmount();
                } else {
                    negative += item.getAmount();
                }
                moneyViewModel.setIncome(positive);
                moneyViewModel.setExpenses(negative);

            }
        }
        moneyViewModel.setBalance();
    }

}
