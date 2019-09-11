package com.exampdm.moneybook.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.exampdm.moneybook.MBRepository;
import com.exampdm.moneybook.db.entity.MoneyEntity;
import com.exampdm.moneybook.db.entity.MoneyTagJoin;
import com.exampdm.moneybook.db.entity.TagEntity;

import java.util.Date;
import java.util.List;

public class MoneyViewModel extends AndroidViewModel {
    private MBRepository mRepository;
    private LiveData<List<MoneyEntity>> mAllMoney;
    private LiveData<List<TagEntity>> mAllTags;
    private LiveData<List<MoneyTagJoin>> mAllMjT;
    private List<String> tagListStr;

    public MoneyViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MBRepository(application);
        mAllMoney = mRepository.getAllMoney();
        mAllTags = mRepository.getAllTags();
        mAllMjT = mRepository.getAllMjT();
    }

    public LiveData<List<TagEntity>> getAllTags() {
        return mAllTags;
    }

    public LiveData<List<MoneyEntity>> getAllMoney() {
        return mAllMoney;
    }

    public LiveData<List<MoneyTagJoin>> getAllMjT() {
        return mAllMjT;
    }

    public List<String> getTagList() {
        return mRepository.getTagList();
    }

    public List<String> getItemTags(MoneyEntity item) {
        return mRepository.getItemTags(item);
    }

    public void insert(MoneyEntity money) {
        mRepository.insert(money);
    }

    public void delete(MoneyEntity money) {
        mRepository.delete(money);
    }

    public void deleteAllMoney() {
        mRepository.deleteAllMoney();
    }

    public void clearAllData() {
        mRepository.deleteAllMoney();
        mRepository.deleteAllItemTags();
        mRepository.deleteAllTag();
    }

    public void insertTag(TagEntity tag) {
        tagListStr = getTagList();
        boolean skip = false;
        String tagStr = tag.getTag();

        if (tagListStr != null) {
            for (String s : tagListStr) {
                if (tagStr.equals(s)) {
                    skip = true;
                }
            }
        }
        if (!skip) mRepository.insertTag(tag);
    }

    public void insertAllTags(List<TagEntity> tags) {
        for (TagEntity t : tags) {
            insertTag(t);
        }
    }

    public void insertItemTag(MoneyTagJoin itemTag) {
        mRepository.insertItemTag(itemTag);
    }

    public void insertItemTags(MoneyTagJoin[] itemTags) {
        for (MoneyTagJoin mtj : itemTags) {
            insertItemTag(mtj);
        }
    }
    public void clearItemTags(){
        mRepository.clearItemTags();
    }


    public void updtTagList() {
        tagListStr = getTagList();
    }

}
