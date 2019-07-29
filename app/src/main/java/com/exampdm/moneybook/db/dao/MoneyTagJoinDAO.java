package com.exampdm.moneybook.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;

import com.exampdm.moneybook.db.entity.MoneyEntity;
import com.exampdm.moneybook.db.entity.MoneyTagJoin;

import java.util.List;

@Dao
public interface MoneyTagJoinDAO {
    @Insert
    void insert(MoneyTagJoin moneyTagJoin);

  /*  @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM money_item INNER JOIN item_tag_join ON money_item.id = item_tag_join.itemId WHERE item_tag_join.tagId = tagId")
    List<MoneyEntity> getMoneyFortag(final int tagId);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM item_tag INNER JOIN item_tag_join ON item_tag.id = item_tag_join.tagId WHERE item_tag_join.itemId = itemId")
    List<MoneyEntity> getTagForMoney(final int itemId);*/
    //TODO inserire query per join tra money e tag
}
