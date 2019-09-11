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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.exampdm.moneybook.R;
import com.exampdm.moneybook.db.entity.MoneyEntity;
import com.exampdm.moneybook.db.entity.MoneyTagJoin;
import com.exampdm.moneybook.db.entity.TagEntity;
import com.exampdm.moneybook.viewmodel.MoneyViewModel;
import com.exampdm.moneybook.viewmodel.StatisticViewModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class BalanceFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TagPickerFragment.tagSelectedListener{

    private boolean fromToDate = true;
    private TextView dateFromView;
    private TextView dateToView;
    private TextView incomeTextView;
    private TextView expensesTextView;
    private TextView totalTextView;
    private TextView tagTextview;
    private FragmentManager fm;
    private List<MoneyEntity> itemInRange;
    private double income;
    private double expenses;
    private TagEntity selectedTag;



    private StatisticViewModel statisticViewModel;
    private MoneyViewModel moneyViewModel;

    public BalanceFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        dateFromView = view.findViewById(R.id.span_start);
        dateToView = view.findViewById(R.id.span_end);
        incomeTextView = view.findViewById(R.id.income_textView);
        expensesTextView = view.findViewById(R.id.expanses_textView);
        totalTextView = view.findViewById(R.id.total_textView);
        tagTextview = view.findViewById(R.id.tag_textview_frg);

        Button resetTagButton= view.findViewById(R.id.reset_tag_button);
        Button tag_Button = view.findViewById(R.id.show_tag_btn);

        // Inflate the layout for this fragment
        fm = (Objects.requireNonNull(getActivity())).getSupportFragmentManager();

        moneyViewModel= ViewModelProviders.of(getActivity()).get(MoneyViewModel.class);
        statisticViewModel = ViewModelProviders.of(getActivity()).get(StatisticViewModel.class);
        statisticViewModel.getFromDate().observe(this, new Observer<Date>() {
            @Override
            public void onChanged(Date date) {
                dateFromView.setText(dateToString(date));
            }
        });

        statisticViewModel.getToDate().observe(this, new Observer<Date>() {
            @Override
            public void onChanged(Date date) {
                dateToView.setText(dateToString(date));
            }
        });

        moneyViewModel.getAllMoney().observe(this, new Observer<List<MoneyEntity>>() {
            @Override
            public void onChanged(List<MoneyEntity> moneys) {
                statisticViewModel.setAllItems(moneys);
                if(moneys!=null){
                    statisticViewModel.setMutableItemInRange();
                }
            }
        });
        statisticViewModel.getAllMjT().observe(this, new Observer<List<MoneyTagJoin>>() {
            @Override
            public void onChanged(List<MoneyTagJoin> moneyTagJoins) {
                statisticViewModel.setMoneyTagJoins(moneyTagJoins);
            }
        });

      statisticViewModel.getMutableItemInRange().observe(this, new Observer<List<MoneyEntity>>() {
            @Override
            public void onChanged(List<MoneyEntity> moneyEntities) {
                if(moneyEntities!=null) {
                    itemInRange = moneyEntities;
                }
            }
        });

        statisticViewModel.getIncome().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                income=aDouble;
                incomeTextView.setText(NumberFormat.getInstance().format(income));
            }
        });

        statisticViewModel.getExpenses().observe(this, new Observer<Double>() {
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

        tag_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTagPickerFragment(v);
            }
        });

        resetTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagTextview.setText("");
                statisticViewModel.resetTag();
            }
        });

        return view;
    }



    private void showDatePickerDialog(View v) {
        fromToDate = v == dateFromView;

        DialogFragment newFragment = new StatisticDatePicker();
        newFragment.setTargetFragment(BalanceFragment.this, 0);
        newFragment.show(fm, "datePicker");

    }

    private void showTagPickerFragment(View v){
        fm.executePendingTransactions();
        DialogFragment newFragment = new TagPickerFragment();
        newFragment.setTargetFragment(BalanceFragment.this,0);
        assert getFragmentManager() != null;
        newFragment.showNow(getFragmentManager(), "tagPicker");
        //newFragment.show(fm, "tagPicker");
        if(fm.executePendingTransactions()){
            newFragment.show(fm, "tagPicker");
        }
    }

    @Override
    public void getTagSelected(TagEntity tag) {
        selectedTag=tag;
        tagTextview.append(" "+tag.getTag());
        statisticViewModel.setTagFilter(tag);


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
            statisticViewModel.setFromDate(utilDate);

        } else {
            dateToView.setText(temp);
            statisticViewModel.setToDate(utilDate);
        }
        //setBalanceFrg();
        //statisticViewModel.setBalance(itemInRange);
        statisticViewModel.setMutableItemInRange();

    }

    private String dateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy",
                Locale.ITALIAN);
        return dateFormat.format(date);
    }



}
