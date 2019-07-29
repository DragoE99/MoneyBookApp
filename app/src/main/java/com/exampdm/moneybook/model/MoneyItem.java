package com.exampdm.moneybook.model;

import java.util.Date;

public interface MoneyItem {

    int getId();
    double getAmount();
    Date getItemDate();
    String getDescription();

}
