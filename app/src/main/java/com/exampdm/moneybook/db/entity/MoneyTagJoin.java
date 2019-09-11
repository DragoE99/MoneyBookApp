package com.exampdm.moneybook.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "item_tag_join",
        primaryKeys = {"itemId", "tagId"})
public class MoneyTagJoin {

    @NonNull
    public long itemId;
    @NonNull
    public  String tagId;

    public long getItemId() {
        return itemId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }


    public MoneyTagJoin() {
    }

    @Ignore
    public MoneyTagJoin(MoneyEntity item, TagEntity tag){
        this.itemId= item.getId();
        this.tagId= tag.getTag();
    }

}
