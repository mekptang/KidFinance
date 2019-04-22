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

public class AchievementFragment extends Fragment {
    private static final int CREATE_ACHIEVEMENT_LIST = 100;
    private static final int OPEN_ACHIEVEMENT_ITEM = 200;
    String achievement_file_name = "achievement_list.txt";

    GridView mainGrid;
    ArrayList<AchievementModel> achievements_list = new ArrayList<AchievementModel>();

    @Override
    // Aim: To initialize achievements_list
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a default achievement_list.txt if not exist
        if (fileExists(getContext(), achievement_file_name) == false) {
            Intent in = new Intent(getActivity(), AchievementListCreationActivity.class);
            startActivityForResult(in, CREATE_ACHIEVEMENT_LIST);
        }
    }

    @Nullable
    @Override
    // Aim: To set UI of achievement page
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
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

        // Inflate fragment_achievement.xml
        View view_achievement = inflater.inflate(R.layout.fragment_achievement, container, false);

        // Get gridview object from xml file + Set custom adapter (AchievementGridAdapter) to mainGrid
        mainGrid = (GridView) view_achievement.findViewById(R.id.achievement_mainGrid);
        mainGrid.setAdapter(new AchievementGridAdapter(getContext(), achievements_list));

        return view_achievement;
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
                in.putExtra("logo", achievements_list.get(position).getAchievementLogo());
                in.putExtra("achievement_title", achievements_list.get(position).getAchievementTitle());
                in.putExtra("achievement_description", achievements_list.get(position).getAchievementDescription());
                in.putExtra("achievement_completion", achievements_list.get(position).getAchievementSuccess());

                startActivityForResult(in, OPEN_ACHIEVEMENT_ITEM);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == CREATE_ACHIEVEMENT_LIST) {
            // Toast.makeText(getContext(), "CREATE_ACHIEVEMENT_LIST Done!", Toast.LENGTH_LONG).show();
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
