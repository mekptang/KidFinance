package com.example.kidfinance;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExpenseData {
    String date;
    String amount;
    String remark;
    String expenseType;


    ExpenseData(String amount, String expenseType, String remark){

        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        this.date = formatter.format(date);
        this.amount = amount;
        this.expenseType = expenseType;
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

    public String getExpenseType() {
        return expenseType;
    }
}
