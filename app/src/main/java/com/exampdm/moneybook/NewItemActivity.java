package com.exampdm.moneybook;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.exampdm.moneybook.UI.DatePickerFragment;
import com.exampdm.moneybook.UI.MoneyItemAdapter;
import com.exampdm.moneybook.db.entity.MoneyEntity;
import com.exampdm.moneybook.db.entity.MoneyTagJoin;
import com.exampdm.moneybook.db.entity.TagEntity;
import com.exampdm.moneybook.viewmodel.MoneyViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class NewItemActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mEditAmount;
    private EditText mEditDescription;
    private EditText mEditTags;
    private TextView mEditDate;
    private Date itemDate = new Date();
    private TagEntity[] myTags;
    private MoneyEntity item;
    private MoneyTagJoin[] itemTags;
    private MoneyEntity updtItem;
    private List<String> updtItemTags;
    private MoneyViewModel mMoneyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        mEditAmount = findViewById(R.id.insert_amountTextView);
        mEditDescription = findViewById(R.id.insert_item_description);
        mEditTags = findViewById(R.id.insertTagsTextView);
        mEditDate = findViewById(R.id.insertDateView);

        Intent i = getIntent();
        if(i.getStringExtra(MoneyItemAdapter.EXTRA_AMOUNT)!=null){

            String tempAmount=i.getStringExtra(MoneyItemAdapter.EXTRA_AMOUNT);

            for(int j=3; (tempAmount.length()-j)>0; j=j+3){
            if(tempAmount.contains(".")){
                StringBuilder build = new StringBuilder(tempAmount);
                build.deleteCharAt(tempAmount.indexOf('.'));
                tempAmount=build.toString();
            }
            }

            mEditAmount.setText(tempAmount);
            mEditDate.setText(i.getStringExtra(MoneyItemAdapter.EXTRA_DATE));
            mEditTags.setText(i.getStringExtra(MoneyItemAdapter.EXTRA_TAGS));
            mEditDescription.setText(i.getStringExtra(MoneyItemAdapter.EXTRA_DESCRIPTION));

            try {
                itemDate =  new SimpleDateFormat ("dd MMM yyyy",
                        Locale.getDefault()).parse(i.getStringExtra(MoneyItemAdapter.EXTRA_DATE));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        mMoneyViewModel = ViewModelProviders.of(this).get(MoneyViewModel.class);


        final Button button = findViewById(R.id.saveItemButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButtonClicked();
            }
        });
    }
    @Override
    public void onBackPressed(){
        saveButtonClicked();
    }

    private void saveButtonClicked(){
        Intent replyIntent = new Intent();

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);

        if (TextUtils.isEmpty(mEditAmount.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {

            item = createItem();
            getTags();
            //new MoneyEntity(getAmount(), getDescription());
            if (myTags.length != 0) {
                mMoneyViewModel.insertAllTags(myTags);
            }
            if(itemTags.length!=0){
                mMoneyViewModel.insertItemTags(itemTags);
            }
            mMoneyViewModel.insert(item);

            setResult(RESULT_OK, replyIntent);
        }
        finish();

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void setItemDate(Date userDate) {
        itemDate = userDate;
    }

    private void setTextDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy",
                Locale.ITALIAN);
        String temp = dateFormat.format(itemDate);
        mEditDate.setText(temp);
    }


    private double getAmount() {
        double current;
        String temp=mEditAmount.getText().toString().replace(',','.');

        if (TextUtils.isEmpty(mEditAmount.getText())) {
            current = 0;
        } else current = Double.parseDouble(temp);
      /*  try {
            current= NumberFormat
                    .getInstance()
                    .parse(String.valueOf(mEditAmount.getText())).doubleValue();

        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        return current;
    }

    public void getTags() {
        String[] tags;
        if (TextUtils.isEmpty(mEditTags.getText())) {
            myTags = new TagEntity[0];
            itemTags=new MoneyTagJoin[0];
        } else {
            String temp = mEditTags.getText().toString();
            tags = temp.split("\\s");
            myTags = arrayTags(tags);
            mEditTags.setText(myTags[0].getTag());
        }
    }

    private TagEntity[] arrayTags(String[] tags) {
        TagEntity[] myTags = new TagEntity[tags.length];
        itemTags=new MoneyTagJoin[tags.length];
        for (int i = 0; i < tags.length; i++) {
            myTags[i] = new TagEntity(tags[i]);
            itemTags[i] = new MoneyTagJoin(item, myTags[i]);
        }
        return myTags;
    }

    private Date getItemDate() {
        return itemDate;
    }

    private String getDescription() {
        String descriptionText;
        if (TextUtils.isEmpty(mEditDescription.getText())) {
            descriptionText = " ";
        } else {

            descriptionText = mEditDescription.getText().toString();
        }
        return descriptionText;
    }

    private MoneyEntity createItem() {
        return new MoneyEntity(getAmount(), getItemDate(), getDescription());
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
        setItemDate(utilDate);
        setTextDate();

    }

}
