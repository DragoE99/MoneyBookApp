package com.exampdm.moneybook.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.exampdm.moneybook.db.entity.TagEntity;

import java.util.List;

@Dao
public interface TagDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTag(TagEntity tagItem);

   /* @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllTags(TagEntity[] tagsList);*/

    @Query("DELETE FROM item_tag")
    void deleteAllTag();

    @Query("DELETE FROM item_tag WHERE tag NOT IN (SELECT DISTINCT item_tag_join.tagId FROM item_tag_join )")
    void deleteUnusedTag();

    @Query("SELECT *FROM item_tag ORDER BY tag")
    LiveData<List<TagEntity>> getAllTag();

  /* @Query("SELECT * FROM item_tag WHERE item_tag.tag= :tagName")
    TagEntity getTagFromName(final String tagName);*/

   @Query("SELECT tag FROM item_tag")
    List<String> getTagList();
}
