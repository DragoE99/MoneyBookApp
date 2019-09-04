package com.exampdm.moneybook.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.exampdm.moneybook.R;

public class StatisicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statisic);
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ListItemFragment fragment =new ListItemFragment();
        fragmentTransaction.add(R.id.fragment_list, fragment);
        fragmentTransaction.commit();*/
    }
}
