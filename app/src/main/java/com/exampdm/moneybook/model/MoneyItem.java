package com.exampdm.moneybook.model;

import java.util.Date;

public interface MoneyItem {

    long getId();
    double getAmount();
    Date getItemDate();
    String getDescription();

}
