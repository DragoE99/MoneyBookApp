package com.exampdm.moneybook.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.exampdm.moneybook.db.entity.MoneyTagJoin;

import java.util.List;

@Dao
public interface MoneyTagJoinDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItemTag(MoneyTagJoin moneyTagJoin);


  /*  @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM money_item INNER JOIN item_tag_join ON money_item.id = item_tag_join.itemId WHERE item_tag_join.tagId = :tagId")
    List<MoneyEntity> getMoneyForTag(final int tagId);*/

    @Query("DELETE FROM item_tag_join")
    void deleteAllItemTags();

    @Query("DELETE FROM item_tag_join WHERE id= :currentId")
    void clearItemTags(int currentId);
    @Query("SELECT * FROM item_tag_join WHERE itemId NOT IN(SELECT id FROM money_item)")
    List<MoneyTagJoin> getOldItemTags();


    @Query("SELECT* FROM ITEM_TAG_JOIN")
    LiveData<List<MoneyTagJoin>> getItemsTags();

    @Query("SELECT tag FROM item_tag JOIN item_tag_join ON item_tag.tag = item_tag_join.tagId WHERE item_tag_join.itemId = :currentItemId")
    List<String> getTagForItem(final long currentItemId);

    //TODO inserire query per join tra money e tag
}
