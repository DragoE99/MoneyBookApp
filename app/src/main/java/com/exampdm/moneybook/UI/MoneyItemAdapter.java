package com.exampdm.moneybook.UI;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.exampdm.moneybook.MainActivity;
import com.exampdm.moneybook.R;
import com.exampdm.moneybook.db.entity.MoneyEntity;
import com.exampdm.moneybook.db.entity.MoneyTagJoin;
import com.exampdm.moneybook.db.entity.TagEntity;
import com.exampdm.moneybook.viewmodel.MoneyViewModel;
import com.google.android.material.snackbar.Snackbar;


import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MoneyItemAdapter extends RecyclerView.Adapter<MoneyItemAdapter.MoneyViewHolder> {

    class MoneyViewHolder extends RecyclerView.ViewHolder {

        private final TextView moneyItemView;
        private final TextView dateItemView;
        private final TextView descItemView;
        private final TextView tagsView;


        private MoneyViewHolder(View itemView) {
            super(itemView);
            moneyItemView = itemView.findViewById(R.id.itemAmountNumber);
            dateItemView = itemView.findViewById(R.id.itemDate);
            descItemView = itemView.findViewById(R.id.itemDescription);
            tagsView = itemView.findViewById(R.id.itemTagText);
        }
    }


    private final LayoutInflater mInflater;
    private List<MoneyEntity> mMoney = Collections.emptyList();
    private List<TagEntity> mTags = Collections.emptyList();
    private List<MoneyTagJoin> mAllMjT = Collections.emptyList();
    private View mItemView;

    private MoneyEntity recentlyDeletedItem;
    private int mRecentlyDeletedItemPosition;
    private MoneyViewModel mMoneyViewModel;


    public MoneyItemAdapter(Context context, MoneyViewModel moneyViewModel) {
        mInflater = LayoutInflater.from(context);
        mMoneyViewModel = moneyViewModel;
    }

    @NonNull
    @Override
    public MoneyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.movement_view, parent, false);
        mItemView=itemView;
        return new MoneyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MoneyViewHolder holder, int position) {
        MoneyEntity current = mMoney.get(position);
        holder.tagsView.setText(tagToString(current));
        holder.moneyItemView.setText(current.getStringAmount());
        holder.dateItemView.setText(current.getStringDate());
        holder.descItemView.setText(current.getDescription());
    }

    private String tagToString(MoneyEntity current) {
        String stringOfTags = "";

        for (MoneyTagJoin itemTag: mAllMjT
             ) {
            if(itemTag.getItemId()==current.getId()){
                stringOfTags= stringOfTags.concat(itemTag.getTagId()+" ");
            }

        }

       /* List<String> itemTags= mMoneyViewModel.getItemTags(mMoney.get(current));
        if(itemTags==null){return "itemTags adpt null";

        }else if (itemTags.isEmpty()) {
            return "itemtags empty";
        } else {
             stringOfTags = "";
            for (int i = 0; i < itemTags.size(); i++) {
                String temp = itemTags.get(i) + " ";
                stringOfTags = stringOfTags.concat(temp);
            }
            return stringOfTags;
        }*/
        return stringOfTags;
    }

    public void setMoney(List<MoneyEntity> money) {
        mMoney = money;
        notifyDataSetChanged();
    }

    public void setTags(List<TagEntity> tags) {
        mTags = tags;
    }

    public void setMjT(List<MoneyTagJoin> itemsTags){mAllMjT = itemsTags;}

    @Override
    public int getItemCount() {
        return mMoney.size();
    }


    public void deleteItem(int position) {

        mRecentlyDeletedItemPosition=position;
        recentlyDeletedItem = mMoney.get(position);
        mMoneyViewModel.delete(mMoney.get(position));
        mMoney.remove(position);
        notifyItemRemoved(position);
        showUndoSnackbar();
    }

    private void showUndoSnackbar() {
        View view = mItemView.findViewById(R.id.linear_layout_movment_view);
        Snackbar snackbar = Snackbar.make(view, R.string.snack_bar_text,
                Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_bar_undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoneyItemAdapter.this.undoDelete();
            }
        });
        snackbar.show();
    }

    private void undoDelete() {
        mMoney.add(recentlyDeletedItem);
        mMoneyViewModel.insert(recentlyDeletedItem);
        notifyItemInserted(mRecentlyDeletedItemPosition);
    }



}

