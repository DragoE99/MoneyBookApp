package com.exampdm.moneybook.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.exampdm.moneybook.db.entity.MoneyEntity;

import java.util.List;

@Dao
public interface MoneyDAO {

    @Query("SELECT * FROM money_item ORDER BY itemDate")
    LiveData<List<MoneyEntity>> getAllMoney();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllItems(List<MoneyEntity> items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAnItem(MoneyEntity item);

    @Delete
    void deleteAnItem(MoneyEntity item);

    @Update
    void updateItem(MoneyEntity... items);

    @Query("DELETE FROM MONEY_ITEM")
    void deleteAllItem();
}
