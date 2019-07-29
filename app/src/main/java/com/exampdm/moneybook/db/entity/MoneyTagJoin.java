package com.exampdm.moneybook.db.entity;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "item_tag_join",
        primaryKeys = {"itemId", "tagId"},
        foreignKeys = {
            @ForeignKey(
                    entity = MoneyEntity.class,
                    parentColumns = "id",
                    childColumns = "itemId"),
            @ForeignKey(
                entity= TagEntity.class,
                parentColumns = "id",
                childColumns = "tagId")},
indices = {@Index("itemId"),
            @Index("tagId")})
public class MoneyTagJoin {


    public final int itemId;
    public final int tagId;

    public MoneyTagJoin(final int itemId, final int tagId){

        this.itemId= itemId;
        this.tagId= tagId;
    }

}
