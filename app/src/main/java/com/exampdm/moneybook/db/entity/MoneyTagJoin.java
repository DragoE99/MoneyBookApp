package com.exampdm.moneybook.db.entity;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "item_tag_join"/*,
        foreignKeys = {
            @ForeignKey(
                    entity = MoneyEntity.class,
                    parentColumns = "id",
                    childColumns = "itemId"),
            @ForeignKey(
                entity= TagEntity.class,
                parentColumns = "tag",
                childColumns = "tagId")},
indices = {@Index(value = {"itemId","tagId"})}*/)
public class MoneyTagJoin {
/*primaryKeys = {"itemId", "tagId"},*/

    @PrimaryKey(autoGenerate = true)
    private int id;


    public long itemId;
    public  String tagId;

    public long getItemId() {
        return itemId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MoneyTagJoin() {
    }

    @Ignore
    public MoneyTagJoin(MoneyEntity item, TagEntity tag){
        this.itemId= item.getId();
        this.tagId= tag.getTag();
    }

}
