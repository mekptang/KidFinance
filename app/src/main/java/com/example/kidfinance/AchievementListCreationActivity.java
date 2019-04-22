package com.example.kidfinance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
// import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class AchievementListCreationActivity extends AppCompatActivity {
    ArrayList<AchievementModel> achievements_list = new ArrayList<AchievementModel>();
    String achievement_file_name = "achievement_list.txt";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (fileExists(getApplicationContext(), achievement_file_name) == false) {
            // If achievement_list.txt does not exist in local storage:
            // Declare arrays to store achievement info + Store the information to achievements_list
            int[] achievement_logo = {R.drawable.achievement_calendar, R.drawable.achievement_calendar, R.drawable.achievement_calendar,
                    R.drawable.achievement_family_time, R.drawable.achievement_family_time, R.drawable.achievement_family_time,
                    R.drawable.achievement_friends, R.drawable.achievement_friends, R.drawable.achievement_friends};
            String[] achievement_title = {"有米 (LV1)", "有米 (LV2)", "有米 (LV3)", "土豪 (LV1)", "土豪 (LV2)", "土豪 (LV3)",
                    "父幹 (LV1)", "父幹 (LV2)", "父幹 (LV3)"};
            String[] achievement_description = {"Maximum Account Balance reached $500!  So lucky!", "Maximum Account Balance reached $2000!  So lucky!", "Maximum Account Balance reached $5000!  So lucky!",
                    "Your spendings reached $500! So rich!", "Your spendings reached $2000! So rich!", "Your spendings reached $5000! So rich!",
                    "You got $500 from parents in total!  So lucky!", "You got $2000 from parents in total!  So lucky!", "You got $5000 from parents in total!  So lucky!"};
            int[] achievement_success = {0, 0, 0, 0, 0, 0, 0, 0, 0};
            float[] achievement_amount = {500, 2000, 5000, 500, 2000, 5000, 500, 2000, 5000};
            int achievement_length = achievement_logo.length;

            for (int i = 0; i < achievement_length; i++) {
                AchievementModel current_achievement = new AchievementModel(achievement_logo[i], achievement_title[i], achievement_description[i], achievement_amount[i], achievement_success[i]);
                achievements_list.add(current_achievement);
            }

            // Create a default achievement file in local storage
            String listSerializedToJson = new Gson().toJson(achievements_list);
            writeToFile(listSerializedToJson, getApplicationContext(), achievement_file_name);

            // Return to the original Activity/Fragment
            Intent intent = new Intent();
            AchievementListCreationActivity.this.setResult(RESULT_OK, intent);
            AchievementListCreationActivity.this.finish();
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
            FileInputStream inStream = getApplicationContext().openFileInput(fileName);
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




