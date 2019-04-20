package com.example.kidfinance;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class AchievementFragment extends Fragment {
    GridView mainGrid;
    ArrayList<AchievementModel> achievements_list = new ArrayList<AchievementModel>();
    String achievement_file_name = "achievement_list.txt";

    @Override
    // Aim: To initialize achievements_list
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (fileExists(getContext(), achievement_file_name) == true) {
            // If achievement_list.txt exists in local storage:
            // Load the achievement information into achievements_list one by one
            String json = loadTextFile(achievement_file_name);
            JsonArray arr = new JsonParser().parse(json).getAsJsonArray();

            for (JsonElement je : arr) {
                JsonObject all = je.getAsJsonObject();
                int card_logo = all.get("achievement_logo").getAsInt();
                String achievement_title = all.get("achievement_title").getAsString();
                String achievement_description = all.get("achievement_description").getAsString();
                int achievement_success = all.get("achievement_success").getAsInt();

                AchievementModel current_achievement = new AchievementModel(card_logo, achievement_title, achievement_description, achievement_success);
                achievements_list.add(current_achievement);
            }

            Toast.makeText(getContext(),"Achievement File Loaded Successfully!", Toast.LENGTH_LONG).show();
        }
        else {
            // If achievement_list.txt does not exist in local storage:
            // Declare arrays to store achievement info + Store the information to achievements_list
            int[] achievement_logo = {R.drawable.achievement_calendar, R.drawable.achievement_calendar, R.drawable.achievement_calendar,
                    R.drawable.achievement_family_time, R.drawable.achievement_family_time, R.drawable.achievement_family_time,
                    R.drawable.achievement_friends, R.drawable.achievement_friends, R.drawable.achievement_friends};
            String[] achievement_title = {"Achiever (LV1)", "Achiever (LV2)", "Achiever (LV3)", "Fighter (LV1)", "Fighter (LV2)", "Fighter (LV3)",
                    "General (LV1)", "General (LV2)", "General (LV3)"};
            String[] achievement_description = {"Achiever (LV1)", "Achiever (LV2)", "Achiever (LV3)", "Fighter (LV1)", "Successfully Saved $10000!", "Fighter (LV3)",
                    " General (LV1)", "General (LV2)", "General (LV3)"};
            int[] achievement_success = {0, 0, 0, 0, 1, 0, 0, 0, 0};
            int achievement_length = achievement_logo.length;

            for (int i = 0; i < achievement_length; i++) {
                AchievementModel current_achievement = new AchievementModel(achievement_logo[i], achievement_title[i], achievement_description[i], achievement_success[i]);
                achievements_list.add(current_achievement);
            }

            // Create a default achievement file in local storage
            String listSerializedToJson = new Gson().toJson(achievements_list);
            writeToFile(listSerializedToJson, getContext(), achievement_file_name);

            Toast.makeText(getContext(), "A default achievement file is created!", Toast.LENGTH_LONG).show();
        }
    }

    @Nullable
    @Override
    // Aim: To set UI of achievement page
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        // Inflate fragment_achievement.xml
        View view_achievement = inflater.inflate(R.layout.fragment_achievement, container, false);

        // Get gridview object from xml file + Set custom adapter (AchievementGridAdapter) to mainGrid
        mainGrid = (GridView) view_achievement.findViewById(R.id.achievement_mainGrid);
        mainGrid.setAdapter(new AchievementGridAdapter(getContext(), achievements_list));

        return view_achievement;
    }

    // Aim: To set functionality of achievements' buttons click
    public void onViewCreated(View view_achievement, Bundle savedInstanceState) {
        // Set item click listener to each button
        mainGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle info = new Bundle();
                info.putInt("logo", achievements_list.get(position).getAchievementLogo());
                info.putString("achievement_title", achievements_list.get(position).getAchievementTitle());
                info.putString("achievement_description", achievements_list.get(position).getAchievementDescription());
                info.putInt("achievement_completion", achievements_list.get(position).getAchievementSuccess());

                Fragment achievement_details = new AchievementDetailsFragment();
                achievement_details.setArguments(info);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, achievement_details, null)
                        .addToBackStack(null)
                        .commit();
            }
        });
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
            while((length = inStream.read(buffer))!=-1) {
                stream.write(buffer,0,length);
            }
            stream.close();
            inStream.close();
            text = stream.toString();
        }
        catch (FileNotFoundException e) {
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
}
