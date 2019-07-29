package com.exampdm.moneybook.db.entity;

import com.exampdm.moneybook.model.MoneyItem;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@Entity(tableName = "money_item")
public class MoneyEntity implements MoneyItem {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private double amount;
    private Date itemDate;
    private String description;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public Date getItemDate() {
        return itemDate;
    }
    public void setItemDate(Date itemDate) {
        this.itemDate = itemDate;
    }

    @Override
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Ignore
    public String getStringAmount(){
        return NumberFormat.getInstance().format(this.amount);
    }

    @Ignore
    public String getStringDate(){
        DateFormat dateFormat = new SimpleDateFormat ("dd/MM/yyyy",
        Locale.ITALIAN);
        return dateFormat.format(this.itemDate);
    }

    @Ignore
    public MoneyEntity(double amount, Date itemDate, String description) {

        this.amount = amount;
        this.itemDate = itemDate;
        this.description = description;
    }

    @Ignore
    public  MoneyEntity(double newAmount){
        this.amount= newAmount;
        this.itemDate= new Date();

    }
   public MoneyEntity(MoneyItem item){

       this.id = item.getId();
       this.amount = item.getAmount();
       this.itemDate = item.getItemDate();
       this.description = item.getDescription();
   }

   public MoneyEntity(){

   }
}