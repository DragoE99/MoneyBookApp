package com.exampdm.moneybook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.exampdm.moneybook.UI.MoneyItemAdapter;
import com.exampdm.moneybook.UI.SwipeToDeleteCallback;
import com.exampdm.moneybook.db.entity.MoneyEntity;
import com.exampdm.moneybook.db.entity.MoneyTagJoin;
import com.exampdm.moneybook.db.entity.TagEntity;
import com.exampdm.moneybook.viewmodel.MoneyViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MoneyViewModel mMoneyViewModel;

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mMoneyViewModel = ViewModelProviders.of(this).get(MoneyViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final MoneyItemAdapter adapter = new MoneyItemAdapter(this, mMoneyViewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);



        mMoneyViewModel.getAllMoney().observe(this, new Observer<List<MoneyEntity>>() {
            @Override
            public void onChanged(List<MoneyEntity> moneys) {
                adapter.setMoney(moneys);
            }
        });
        mMoneyViewModel.getAllTags().observe(this, new Observer<List<TagEntity>>() {
            @Override
            public void onChanged(List<TagEntity> tags) {
                adapter.setTags(tags);
                mMoneyViewModel.updtTagList();
            }
        });
        mMoneyViewModel.getAllMjT().observe(this, new Observer<List<MoneyTagJoin>>() {
            @Override
            public void onChanged(List<MoneyTagJoin> moneyTagJoins) {
                adapter.setMjT(moneyTagJoins);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewItemActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, "Clearing the data...",
                    Toast.LENGTH_SHORT).show();

            // Delete the existing data
            mMoneyViewModel.clearAllData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        if(requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK ){
           // MoneyEntity moneyEntity= new MoneyEntity(data.getDoubleExtra(NewItemActivity.EXTRA_REPLY,0));
            //mMoneyViewModel.insert(moneyEntity);
        }else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        mMoneyViewModel.clearItemTags();
    }

}
