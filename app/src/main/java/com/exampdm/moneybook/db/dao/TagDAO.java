package com.exampdm.moneybook.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.exampdm.moneybook.db.entity.TagEntity;

import java.util.List;

@Dao
public interface TagDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTag(TagEntity tagItem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllTags(TagEntity[] tagsList);
    @Delete
    void deleteTag(TagEntity tagItem);

    @Query("SELECT *FROM item_tag")
    LiveData<List<TagEntity>> getAllTag();

   @Query("SELECT * FROM item_tag WHERE item_tag.tag= :tagName")
    TagEntity getTagFromName(final String tagName);
   @Query("SELECT tag FROM item_tag")
    List<String> getTagList();
}
