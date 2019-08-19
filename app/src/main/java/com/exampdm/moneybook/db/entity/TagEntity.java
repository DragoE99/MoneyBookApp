package com.exampdm.moneybook.db.entity;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.exampdm.moneybook.model.ItemTag;

import java.util.List;

@Entity(tableName = "item_tag",
indices = {@Index(value = {"tag"},unique=true)})
public class TagEntity implements ItemTag {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "tag")
    String tag;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTag() {
        return tag;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public TagEntity(){

    }
    @Ignore
    public TagEntity(String tag) {
        this.tag = tag;
    }

}
