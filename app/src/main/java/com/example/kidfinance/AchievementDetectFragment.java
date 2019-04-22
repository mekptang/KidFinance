package com.example.kidfinance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class AchievementDetectFragment extends Fragment {
    private static final int CREATE_ACHIEVEMENT_LIST = 100;
    private static final int OPEN_ACHIEVEMENT_ITEM = 200;
    String achievement_file_name = "achievement_list.txt";

    ArrayList<AchievementModel> achievements_list = new ArrayList<AchievementModel>();
    ArrayList<AchievementModel> achievements_list_new_success = new ArrayList<AchievementModel>();
    ArrayList<ExpenseData> expense_list = new ArrayList<ExpenseData>();
    ArrayList<IncomeData> income_list = new ArrayList<IncomeData>();

    GridView mainGrid;
    ImageButton next_button;
    float total_balance = 0;
    float total_income = 0;
    float total_expense = 0;
    float total_income_parent = 0;

    Bundle bundle;
    int flag = 0;

    @Override
    // Aim: To initialize achievements_list
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getArguments();
        if (bundle != null) {
            flag = bundle.getInt("flag", 0);
        }

        // Load the achievement information into achievements_list one by one
        String json_achievement = loadTextFile(achievement_file_name);
        if (json_achievement == "") {
            json_achievement = "[]";
        }
        JsonArray arr_achievement = new JsonParser().parse(json_achievement).getAsJsonArray();

        for (JsonElement je : arr_achievement) {
            Gson gson1 = new Gson();
            AchievementModel current_achievement = gson1.fromJson(je,  AchievementModel.class);
            achievements_list.add(current_achievement);
        }

        // Ready necessary parameters
        String json_expense = loadTextFile("expense_record.txt");
        if (json_expense == "") {
            json_expense = "[]";
        }
        JsonArray arr_expense = new JsonParser().parse(json_expense).getAsJsonArray();

        for (JsonElement je : arr_expense) {
            Gson gson2 = new Gson();
            ExpenseData current_expense = gson2.fromJson(je,  ExpenseData.class);
            expense_list.add(current_expense);
        }

        String json_income = loadTextFile("income_record.txt");
        if (json_income  == "") {
            json_income  = "[]";
        }
        JsonArray arr_income = new JsonParser().parse(json_income).getAsJsonArray();

        for (JsonElement je : arr_income) {
            Gson gson3 = new Gson();
            IncomeData current_income = gson3.fromJson(je, IncomeData.class);
            income_list.add(current_income);
        }

        for (int i = 0; i < income_list.size(); i++) {
            total_income += Float.parseFloat(income_list.get(i).amount);
        }

        for (int i = 0; i < expense_list.size(); i++) {
            total_expense += Float.parseFloat(expense_list.get(i).amount);
        }
        total_expense = total_expense * -1;

        for (int i = 0; i < income_list.size(); i++) {
            if (income_list.get(i).getIncomeType().equals("Pocket Money")) {
                total_income_parent += Float.parseFloat(income_list.get(i).amount);
            }
        }

        total_balance = Float.parseFloat(loadTextFile("kf_saving_money_config.txt"));

        // Check if each achievement is reached
        for (int i = 0; i <= 2; i++) {
            if ((total_balance >= achievements_list.get(i).getAchievementAmount()) && (achievements_list.get(i).getAchievementSuccess() == 0)) {
                achievements_list_new_success.add(achievements_list.get(i));
                achievements_list.get(i).setAchievementSuccess(1);
            }
        }

        for (int i = 3; i <= 5; i++) {
            if ((total_expense >= achievements_list.get(i).getAchievementAmount()) && (achievements_list.get(i).getAchievementSuccess() == 0)) {
                achievements_list_new_success.add(achievements_list.get(i));
                achievements_list.get(i).setAchievementSuccess(1);
            }
        }

        for (int i = 6; i <= 8; i++) {
            if ((total_income_parent >= achievements_list.get(i).getAchievementAmount()) && (achievements_list.get(i).getAchievementSuccess() == 0)) {
                achievements_list_new_success.add(achievements_list.get(i));
                achievements_list.get(i).setAchievementSuccess(1);
            }
        }

        String listSerializedToJson = new Gson().toJson(achievements_list);
        writeToFile(listSerializedToJson, getContext(), achievement_file_name);

        if (achievements_list_new_success.size() == 0 && flag == 0) {
            Fragment set_target = new SetTargetFragment();

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, set_target, null)
                    .addToBackStack(null)
                    .commit();

            Toast.makeText(getContext(), "Make a New Target or Continue to Save Money!", Toast.LENGTH_LONG).show();
        }
        else if (achievements_list_new_success.size() == 0 && flag == 1) {
            Fragment progress = new ProgessFragment();

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, progress, null)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        // Inflate fragment_achievement_detect.xml
        View view_achievement_detect = inflater.inflate(R.layout.fragment_achievement_detect, container, false);

        // Change summary text
        TextView achievement_detect_summary = view_achievement_detect.findViewById(R.id.achievement_detect_summary);
        if (achievements_list_new_success.size() > 0) {
            achievement_detect_summary.setText("Good! New Achievements Unlocked:");
        }
        else {
            achievement_detect_summary.setText("No Achievements Attained This Time!");
        }

        // Get gridview object from xml file + Set custom adapter (AchievementGridAdapter) to mainGrid
        mainGrid = (GridView) view_achievement_detect.findViewById(R.id.achievement_detect_mainGrid);
        mainGrid.setAdapter(new AchievementGridAdapter(getContext(), achievements_list_new_success));

        next_button = view_achievement_detect.findViewById(R.id.achievement_detect_next);

        return view_achievement_detect;
    }

    @Nullable
    @Override
    // Aim: To set functionality of achievements' buttons click
    public void onViewCreated(View view_achievement, Bundle savedInstanceState) {
        // Set item click listener to each button
        mainGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(getActivity(), AchievementDetailsActivity.class);
                in.putExtra("logo", achievements_list_new_success.get(position).getAchievementLogo());
                in.putExtra("achievement_title", achievements_list_new_success.get(position).getAchievementTitle());
                in.putExtra("achievement_description", achievements_list_new_success.get(position).getAchievementDescription());
                in.putExtra("achievement_completion", achievements_list_new_success.get(position).getAchievementSuccess());

                startActivityForResult(in, OPEN_ACHIEVEMENT_ITEM);
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 1) {
                    Fragment progress = new ProgessFragment();

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, progress, null)
                            .addToBackStack(null)
                            .commit();
                } else {
                    Fragment set_target = new SetTargetFragment();

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, set_target, null)
                            .addToBackStack(null)
                            .commit();

                    Toast.makeText(getContext(), "Make a New Target or Continue to Save Money!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == CREATE_ACHIEVEMENT_LIST) {
            // Toast.makeText(getContext(), "CREATE_ACHIEVEMENT_LIST Done", Toast.LENGTH_LONG).show();
        }
        else if (resultCode == RESULT_OK && requestCode == OPEN_ACHIEVEMENT_ITEM) {
            // Toast.makeText(getContext(), "OPEN_ACHIEVEMENT_ITEM Done", Toast.LENGTH_LONG).show();
        }
    }

    // File I/O for storing achievement info
    public boolean fileExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);

        if (file == null || !file.exists()) {
            return false;
        }

        return true;
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

    private void writeToFile(String data, Context context, String fileName) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
