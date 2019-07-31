package com.exampdm.moneybook.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.exampdm.moneybook.MainActivity;
import com.exampdm.moneybook.R;
import com.exampdm.moneybook.db.entity.MoneyEntity;
import com.exampdm.moneybook.viewmodel.MoneyViewModel;


import java.util.Collections;
import java.util.List;

public class MoneyItemAdapter extends RecyclerView.Adapter<MoneyItemAdapter.MoneyViewHolder> {

    class MoneyViewHolder extends RecyclerView.ViewHolder{

        private final TextView moneyItemView;
        private final  TextView dateItemView;

        private MoneyViewHolder(View itemView){
            super(itemView);
            moneyItemView = itemView.findViewById(R.id.itemAmountNumber);
            dateItemView= itemView.findViewById(R.id.itemDate);
        }
    }


    private final LayoutInflater mInflater;
    private List<MoneyEntity> mMoney = Collections.emptyList();
    private MoneyViewModel mMoneyViewModel;



    public MoneyItemAdapter(Context context, MoneyViewModel moneyViewModel){
        mInflater = LayoutInflater.from(context);
        mMoneyViewModel= moneyViewModel;}

    @NonNull
    @Override
    public MoneyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.movement_view, parent, false);
        return new MoneyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoneyViewHolder holder, int position) {
        MoneyEntity current = mMoney.get(position);
        holder.moneyItemView.setText(current.getStringAmount());
        holder.dateItemView.setText(current.getStringDate());

    }

    public void setMoney(List<MoneyEntity> money){
        mMoney= money;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mMoney.size();
    }


    public void deleteItem(int position){

        mMoneyViewModel.delete(mMoney.get(position));
        mMoney.remove(position);
        notifyItemRemoved(position);
    }

}

