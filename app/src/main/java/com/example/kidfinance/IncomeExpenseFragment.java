package com.example.kidfinance;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class IncomeExpenseFragment extends Fragment {

    LinearLayout view_bookKeeping;
    Button btn_income;
    Button btn_exp;
    Button btn_confirm;

    RadioGroup radGp_exp_type;
    RadioButton selected_type;

    EditText input_amount;
    EditText input_remarks;

    // false for income; true for exp
    boolean record_exp;
    float amount;

    String data_in;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);

        btn_income = (Button) view.findViewById(R.id.btn_income);
        btn_exp = (Button) view.findViewById(R.id.btn_exp);
        btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
        radGp_exp_type = (RadioGroup) view.findViewById(R.id.radGp_exp_cat);
        input_amount = (EditText) view.findViewById(R.id.amount);
        input_remarks = (EditText) view.findViewById(R.id.remarks);
        btn_exp.setBackgroundColor(Color.GREEN);
        btn_income.setBackgroundColor(Color.GRAY);
        record_exp = true;
        amount = 0;

        

        return view;
    }
}
