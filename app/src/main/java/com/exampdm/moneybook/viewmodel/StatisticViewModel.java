package com.exampdm.moneybook.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.exampdm.moneybook.MBRepository;
import com.exampdm.moneybook.db.entity.MoneyEntity;
import com.exampdm.moneybook.db.entity.MoneyTagJoin;
import com.exampdm.moneybook.db.entity.TagEntity;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StatisticViewModel extends AndroidViewModel {
    private MBRepository mRepository;
    private MutableLiveData<Date> fromDate;
    private MutableLiveData<Date> toDate;
    private List<MoneyEntity> allItems;
    private MutableLiveData<List<MoneyEntity>> mutableItemInRange;
    private LiveData<List<MoneyTagJoin>> mAllMjT;
    private List<MoneyTagJoin> moneyTagJoins;
    private List<MoneyEntity> items;
    private MutableLiveData<Double> income;
    private MutableLiveData<Double> expenses;

    public StatisticViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MBRepository(application);
        mAllMjT = mRepository.getAllMjT();

    }

    public LiveData<List<MoneyTagJoin>> getAllMjT() {
        return mAllMjT;
    }
    public void setMutableItemInRange() {
        List<MoneyEntity> items = new ArrayList<>();
        if (getItems() != null) {
            for (MoneyEntity item : getItems()
            ) {
                if (item.getItemDate().after(getStartFromDate())
                        && item.getItemDate().before(getFinishToDate())) {
                    items.add(item);
                }
            }

            setBalance(items);
            mutableItemInRange.setValue(items);
        }
    }

    public MutableLiveData<List<MoneyEntity>> getMutableItemInRange() {
        if (mutableItemInRange == null) {
            mutableItemInRange = new MutableLiveData<>();
        }
        return mutableItemInRange;
    }

    public MutableLiveData<Date> getFromDate() {
        if (fromDate == null) {
            fromDate = new MutableLiveData<>();
            fromDate.setValue(lastMonth());
        }
        return fromDate;
    }

    public MutableLiveData<Date> getToDate() {
        if (toDate == null) {
            toDate = new MutableLiveData<>();
            toDate.setValue(new Date());
        }
        return toDate;
    }

    public void setFromDate(Date date) {
        fromDate.setValue(date);
    }

    public void setToDate(Date date) {
        toDate.setValue(date);
    }


    public void setAllItems(List<MoneyEntity> items) {
        this.allItems = items;
        this.items=items;
    }

    public MutableLiveData<Double> getIncome() {
        if (income == null) {
            income = new MutableLiveData<>();
        }
        return income;
    }

    public MutableLiveData<Double> getExpenses() {
        if (expenses == null) {
            expenses = new MutableLiveData<>();
        }
        return expenses;
    }

    public void setBalance(List<MoneyEntity> items) {
        double positive = (double) 0;
        double negative = (double) 0;
        if (items != null) {
            for (MoneyEntity item : items
            ) {
                if (item.getAmount() > 0) {
                    positive = positive + item.getAmount();
                } else {
                    negative += item.getAmount();
                }
                income.postValue(positive);
                expenses.postValue(negative);
            }
        }
    }

    public void setExpenses(double expenses) {
        this.expenses.postValue(expenses);
    }

    public void setIncome(double income) {
        this.income.postValue(income);
    }

    public void setMoneyTagJoins(List<MoneyTagJoin> itemTags){
        moneyTagJoins=itemTags;
    }
    public void resetTag(){
        setItems(allItems);
    }
    public void setTagFilter(TagEntity tag) {
        List<MoneyEntity> itemList= new ArrayList<>();
        List<MoneyTagJoin> itemTagList = new ArrayList<>();
        for (MoneyTagJoin currentTag:
                moneyTagJoins) {

            if(currentTag.tagId.equals(tag.getTag())){
                itemTagList.add(currentTag);
                for (MoneyEntity item:
                        items ) {
                    if(item.getId()==currentTag.getItemId()){
                        itemList.add(item);
                    }
                }
            }
        }
        if(!itemList.isEmpty()){
            setItems(itemList);
        }

    }



    /*********private methods************/
    private Date lastMonth() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    private Date getStartFromDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate.getValue());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    private Date getFinishToDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(toDate.getValue());
        cal.add(Calendar.DAY_OF_MONTH, +1);
        return cal.getTime();
    }

    private void setItems(List<MoneyEntity> currentList){
        if (currentList != null) {
            items=currentList;
            setMutableItemInRange();
        }

    }

    private List<MoneyEntity> getItems(){return items;}
}
