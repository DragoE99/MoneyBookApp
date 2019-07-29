package com.exampdm.moneybook.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.exampdm.moneybook.model.ItemTag;

@Entity(tableName = "item_tag")
public class TagEntity implements ItemTag {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
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
    public TagEntity( String tag) {
        this.tag = tag;

    }
}
