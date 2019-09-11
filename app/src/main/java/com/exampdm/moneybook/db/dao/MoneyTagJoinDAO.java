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


    /*@Insert
    void insertAll(List<MoneyTagJoin> itemTags);*/


    @Query("DELETE FROM item_tag_join")
    void deleteAllItemTags();

    @Query("DELETE FROM item_tag_join WHERE itemId= :currentId")
    void clearItemTags(long currentId);

    @Query("SELECT DISTINCT * FROM item_tag_join WHERE itemId NOT IN(SELECT id FROM money_item)")
    List<MoneyTagJoin> getOldItemTags();


    @Query("SELECT* FROM ITEM_TAG_JOIN")
    LiveData<List<MoneyTagJoin>> getItemsTags();

    @Query("SELECT tag FROM item_tag JOIN item_tag_join ON item_tag.tag = item_tag_join.tagId WHERE item_tag_join.itemId = :currentItemId")
    List<String> getTagForItem(final long currentItemId);

    /*@Query("SELECT * FROM item_tag_join WHERE tagId=:selectedTag")
    List<MoneyTagJoin> getItemForTag(String selectedTag);*/


}
