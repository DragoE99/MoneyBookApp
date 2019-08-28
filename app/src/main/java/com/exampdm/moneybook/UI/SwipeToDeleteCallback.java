package com.exampdm.moneybook.UI;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.exampdm.moneybook.R;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private MoneyItemAdapter mAdapter;
    public SwipeToDeleteCallback( MoneyItemAdapter adapter){
        super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter=adapter;
    }


    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction){
        int position=viewHolder.getAdapterPosition();
        if(direction==ItemTouchHelper.LEFT){
            mAdapter.updtItem(position);
        }else {
            mAdapter.deleteItem(position);
        }

    }
}
