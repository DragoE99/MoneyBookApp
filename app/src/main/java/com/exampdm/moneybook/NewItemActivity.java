package com.exampdm.moneybook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.exampdm.moneybook.db.entity.MoneyEntity;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;

public class NewItemActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mEditAmount;
    private EditText mEditDescription;
    private EditText mEditTags;
    private EditText mEditDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        mEditAmount = findViewById(R.id.insert_amountTextView);
        mEditDescription = findViewById(R.id.insert_item_description);
        mEditTags= findViewById(R.id.insertTagsTextView);
        mEditDate= findViewById(R.id.itemDate);

        final Button button = findViewById(R.id.saveItemButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent= new Intent();
                if (TextUtils.isEmpty(mEditAmount.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                }else{

                    replyIntent.putExtra(EXTRA_REPLY, getAmount());
                    setResult(RESULT_OK, replyIntent);
                }
                finish();

            }
        });
    }



    private double getAmount(){
        double current= Double.parseDouble(String.valueOf(mEditAmount.getText()));
      /*  try {
            current= NumberFormat
                    .getInstance()
                    .parse(String.valueOf(mEditAmount.getText())).doubleValue();

        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        return current;
    }

    private String[] getTags(){
        String temp= mEditTags.getText().toString();
        String[] tags = temp.split("\\s");
        return tags;
    }

    private Date getItemDate() throws ParseException {

        Date temp = DateFormat.getDateInstance().parse(mEditDate.getText().toString());
        return temp;
    }

    private String getDescription(){
        return mEditDescription.getText().toString();
    }

}
