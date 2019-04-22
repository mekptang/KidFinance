package com.example.kidfinance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
// import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class RecordFragmentExpense extends Fragment {

    public RecordFragmentExpense() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayList<ExpenseData> expense_list = new ArrayList<ExpenseData>();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_record_blank, container, false);

        String json = loadTextFile("expense_record.txt");

        if (json == "") {
            json = "[]";
        }
        JsonArray jsonArray = new JsonParser().parse(json).getAsJsonArray();

        for (JsonElement je : jsonArray) {
            Gson gson = new Gson();
            ExpenseData current_expense_item = gson.fromJson(je, ExpenseData.class);
            expense_list.add(current_expense_item);
        }

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);
        RecordExpenseAdapter adapter = new RecordExpenseAdapter(expense_list);
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

    public String loadTextFile(String fileName) {
        String text = "";
        try {
            FileInputStream inStream = getContext().openFileInput(fileName);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = inStream.read(buffer)) != -1) {
                stream.write(buffer, 0, length);
            }
            stream.close();
            inStream.close();
            text = stream.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            return e.toString();
        }
        return text;
    }
}