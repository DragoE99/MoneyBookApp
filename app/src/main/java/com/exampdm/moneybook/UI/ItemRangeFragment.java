package com.exampdm.moneybook.UI;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exampdm.moneybook.R;
import com.exampdm.moneybook.db.entity.MoneyEntity;
import com.exampdm.moneybook.db.entity.MoneyTagJoin;
import com.exampdm.moneybook.db.entity.TagEntity;
import com.exampdm.moneybook.viewmodel.MoneyViewModel;
import com.exampdm.moneybook.viewmodel.StatisticViewModel;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemRangeFragment extends Fragment {

    private StatisticViewModel statisticViewModel;
    private MoneyViewModel mMoneyViewModel;

    public ItemRangeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_range, container, false);

        mMoneyViewModel = ViewModelProviders.of(this).get(MoneyViewModel.class);
        statisticViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(StatisticViewModel.class);
        RecyclerView recyclerView= view.findViewById(R.id.item_range_recycler);
        final MoneyItemAdapter adapter = new MoneyItemAdapter(getContext(), mMoneyViewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        /*statisticViewModel.getItemBeetweenDate().observe(this, new Observer<List<MoneyEntity>>() {
            @Override
            public void onChanged(List<MoneyEntity> moneyEntities) {
                adapter.setMoney(moneyEntities);
            }
        });*/
        statisticViewModel.getMutableItemInRange().observe(this, new Observer<List<MoneyEntity>>() {
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
        return view;
    }

}
