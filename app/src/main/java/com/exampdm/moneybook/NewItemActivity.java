package com.exampdm.moneybook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.exampdm.moneybook.db.entity.MoneyEntity;

import java.text.NumberFormat;
import java.text.ParseException;

public class NewItemActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mEditAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        mEditAmount = findViewById(R.id.insert_amountTextView);

        final Button button = findViewById(R.id.saveItemButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent= new Intent();
                if (TextUtils.isEmpty(mEditAmount.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                }else{
                    double current=0;
                    Long temp;
                    try {
                         temp= (Long) NumberFormat
                                 .getInstance()
                                 .parse(String.valueOf(mEditAmount.getText()));
                        current= temp.doubleValue();

                        
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    replyIntent.putExtra(EXTRA_REPLY, current);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();

            }
        });
    }
}
