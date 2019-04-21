package com.example.kidfinance;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IncomeData {
    String date;
    String amount;
    String remark;
    String incomeType;

    IncomeData(String amount, String incomeType, String remark){
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        this.date = formatter.format(date);
        this.amount = amount;
        this.incomeType = incomeType;
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public String getAmount() {
        return amount;
    }

    public String getRemark() {
        return remark;
    }

    public String getIncomeType() {
        return incomeType;
    }
}
