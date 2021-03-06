package com.example.kidfinance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
// import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProgessFragment extends Fragment {

    static int targetValue;
    TextView month;
    TextView target;
    TextView message;
    TextView current;
    ProgressBar bar;
    float currentSaving;
    int percent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {

        String tempTargetVal = loadTextFile("kf_target_money_config.txt");
        String tempSavingVal = loadTextFile("kf_saving_money_config.txt");

        if (tempTargetVal != "") {
            targetValue = Integer.parseInt(tempTargetVal);
        } else {
            targetValue = 0;
        }

        if (tempSavingVal != "") {
            currentSaving = Float.parseFloat(tempSavingVal);
        } else {
            currentSaving = 0;
        }

        return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        month = view.findViewById(R.id.progress_monthText);
        target = view.findViewById(R.id.progress_targetText);
        current = view.findViewById(R.id.progress_current);
        message = view.findViewById(R.id.progress_message);
        bar = view.findViewById(R.id.progress_progressBar);

        month.setText(new SimpleDateFormat("MMMM").format(new Date()));
        target.setText("Target:\n$" + targetValue);
        current.setText("Current savings: $" + currentSaving);

        percent = (int) Math.round(100.0 * currentSaving / targetValue);
        if (percent < 100) {
            message.setText("You reached " + percent + "% of your target! Add oil!");
            bar.setProgress(percent);
        } else {
            bar.setProgress(100);
            message.setText("You reached your target already! Yay!");
        }

        super.onViewCreated(view, savedInstanceState);
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
