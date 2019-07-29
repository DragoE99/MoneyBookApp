package com.exampdm.moneybook.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.exampdm.moneybook.db.entity.TagEntity;

import java.util.List;

@Dao
public interface TagDAO {
    @Insert
    void insertTag(TagEntity tagItem);
    @Delete
    void deleteTag(TagEntity tagItem);

    @Query("SELECT *FROM item_tag")
    LiveData<List<TagEntity>> getAllTag();
}
