package com.exampdm.moneybook.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.exampdm.moneybook.MBRepository;
import com.exampdm.moneybook.db.entity.MoneyEntity;

import java.util.List;

public class MoneyViewModel extends AndroidViewModel{
    private MBRepository mRepository;
    private LiveData<List<MoneyEntity>> mAllMoney;

    public MoneyViewModel(@NonNull Application application) {
        super(application);
        mRepository= new MBRepository(application);
        mAllMoney= mRepository.getAllMoney();
    }

    public LiveData<List<MoneyEntity>> getAllMoney(){
        return mAllMoney;
    }
    public void insert(MoneyEntity money){
        mRepository.insert(money);
    }
    public void delete(MoneyEntity money){
        mRepository.delete(money);
    }

    public void deleteAllMoney(){
        mRepository.deleteAllMoney();
    }
}
