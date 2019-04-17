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

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProgessFragment extends Fragment {

    static int targetValue;
    TextView month;
    TextView target;
    TextView message;
    TextView current;
    ProgressBar bar;
    int currentSaving;
    int percent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState) {
        targetValue = 6000;
        currentSaving = 2500;

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
        if (percent < 100)
            bar.setProgress(percent);
        else bar.setProgress(100);

        message.setText("You reached " + percent + "% of your target! Add oil!");

        super.onViewCreated(view, savedInstanceState);
    }
}
