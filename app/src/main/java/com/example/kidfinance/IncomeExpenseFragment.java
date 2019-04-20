package com.example.kidfinance;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

//File used in this fragment:
//kf_saving_money_config.txt
//expense_record.txt
//income_record.txt

public class IncomeExpenseFragment extends Fragment {

    Button btn_income;
    Button btn_exp;
    Button btn_confirm;

    RadioGroup radGp_exp_type;

    EditText input_amount;
    EditText input_remarks;

    boolean record_exp = true;
    String expenseType;

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

        btn_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_exp.setBackgroundColor(Color.GREEN);
                btn_income.setBackgroundColor(Color.GRAY);
                radGp_exp_type.setVisibility(View.VISIBLE);
                record_exp = true;
            }
        });

        btn_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_income.setBackgroundColor(Color.GREEN);
                btn_exp.setBackgroundColor(Color.GRAY);
                radGp_exp_type.setVisibility(View.GONE);
                record_exp = false;
            }
        });

        radGp_exp_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.tbtn_exp_living){
                    expenseType = "Living";
                }else if(checkedId == R.id.tbtn_exp_transport){
                    expenseType = "Transport";
                }else if(checkedId == R.id.tbtn_exp_study){
                    expenseType = "Study";
                }else if(checkedId == R.id.tbtn_exp_entertainment){
                    expenseType = "Entertainment";
                }else if(checkedId == R.id.tbtn_exp_other){
                    expenseType = "Others";
                }
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            String confirmMessage = "";
            @Override
            public void onClick(View v) {
                if(record_exp && checkExpenseInput()){
                    confirmMessage = "Expense: $" + input_amount.getText().toString();
                    confirmPopUp(confirmMessage, -1 * Float.valueOf(input_amount.getText().toString()));
                }else if(!record_exp && checkIncomeInput()){
                    confirmMessage = "Income: $" + input_amount.getText().toString();
                    confirmPopUp(confirmMessage, Float.valueOf(input_amount.getText().toString()));
                }

            }
        });
        return view;
    }

    private boolean checkExpenseInput(){
        if(radGp_exp_type.getCheckedRadioButtonId() == -1){
            Toast.makeText(getContext(), "Type is empty!", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            if(input_amount.getText().toString() ==""){
                Toast.makeText(getContext(), "Amount is empty!", Toast.LENGTH_SHORT).show();
                return false;
            }else if (Integer.parseInt(input_amount.getText().toString()) <= 0){
                Toast.makeText(getContext(), "Expense should not be zero or negative!", Toast.LENGTH_SHORT).show();
                return false;
            }else{
                return true;
            }
        }
    }

    private boolean checkIncomeInput(){
        if(input_amount.getText().toString() ==""){
            Toast.makeText(getContext(), "Amount is empty!", Toast.LENGTH_SHORT).show();
            return false;
        }else if (Integer.parseInt(input_amount.getText().toString()) <= 0){
            Toast.makeText(getContext(), "Income should not be zero or negative!", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    private void confirmPopUp(String msg, final float value){
        new AlertDialog.Builder(getContext())
                .setTitle("Confirm")
                .setMessage(msg)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Done!", Toast.LENGTH_SHORT).show();
                        String tempSavingVal = loadTextFile("kf_saving_money_config.txt");
                        if(tempSavingVal == ""){
                            tempSavingVal = "0";
                        }
                        float savingVal = Float.parseFloat(tempSavingVal) + value;
                        tempSavingVal = String.valueOf(savingVal);
                        writeToFile(tempSavingVal, getContext(), "kf_saving_money_config.txt");

                        if(record_exp){
                            touchFile("expense_record.txt");

                            String json = loadTextFile("expense_record.txt");
                            if(json == ""){
                                json = "[]";
                            }
                            JsonArray jsonArray = new JsonParser().parse(json).getAsJsonArray();

                            Type listType = new TypeToken<ArrayList<ExpenseData>>(){}.getType();
                            ArrayList<ExpenseData> expenseDataList = new Gson().fromJson(jsonArray, listType);

                            ExpenseData ed = new ExpenseData(Float.toString(value), expenseType, input_remarks.getText().toString());
                            expenseDataList.add(ed);

                            String listSerializedToJson = new Gson().toJson(expenseDataList);
                            Toast.makeText(getContext(),listSerializedToJson,Toast.LENGTH_LONG).show();
                            writeToFile(listSerializedToJson, getContext(), "expense_record.txt");

                            FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.fragment_container, new AchievementFragment());
                            ft.commit();
                        }else{
                            touchFile("income_record.txt");

                            String json = loadTextFile("income_record.txt");
                            if(json == ""){
                                json = "[]";
                            }
                            JsonArray jsonArray = new JsonParser().parse(json).getAsJsonArray();

                            Type listType = new TypeToken<ArrayList<IncomeData>>(){}.getType();
                            ArrayList<IncomeData> incomeDataList = new Gson().fromJson(jsonArray, listType);

                            IncomeData id = new IncomeData(Float.toString(value), input_remarks.getText().toString());
                            incomeDataList.add(id);

                            String listSerializedToJson = new Gson().toJson(incomeDataList);
                            Toast.makeText(getContext(),listSerializedToJson,Toast.LENGTH_LONG).show();
                            writeToFile(listSerializedToJson, getContext(), "income_record.txt");

                            FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.fragment_container, new AchievementFragment());
                            ft.commit();
                        }
                    }
                }).setNegativeButton(android.R.string.no, null).show();

    }

    public String loadTextFile(String fileName) {
        String text = "";
        try {
            FileInputStream inStream = getContext().openFileInput(fileName);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = -1;
            while((length = inStream.read(buffer))!=-1) {
                stream.write(buffer,0,length);
            }
            stream.close();
            inStream.close();
            text = stream.toString();
            Toast.makeText(getContext(),"Loaded",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            return e.toString();
        }
        return text;
    }

    private void writeToFile(String data, Context context, String fileName) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void touchFile(String file){
        File f = new File(file);
        if(!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


