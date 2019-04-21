package com.example.kidfinance;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RecordFragmentIncomeResult extends Fragment {

    public RecordFragmentIncomeResult() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.record_pie_chart, container, false);

        String json = loadTextFile("income_record.txt");
        if(json == "") {
            json = "[]";
        }
        JsonArray jsonArray = new JsonParser().parse(json).getAsJsonArray();
        System.out.println("Here is the jsonarray");
        System.out.println(jsonArray.toString());

        String[] ExpenseTypes = new String[]{"Pocket Money", "Red Packets", "Work", "Reward", "Others"};
        float[] Amounts = new float[5];
        Amounts[0] = 0f;
        Amounts[1] = 0f;
        Amounts[2] = 0f;
        Amounts[3] = 0f;
        Amounts[4] = 0f;

        for (int i = 0; i < jsonArray.size(); ++i) {
            JsonObject rec = jsonArray.get(i).getAsJsonObject();
            float Amount = rec.get("amount").getAsFloat();

            System.out.println("FloatAmount: " + Amount);
            String IncomeType = rec.get("incomeType").getAsString();

            if (IncomeType.equals("Pocket Money")) {
                Amounts[0] += Amount;
            }
            else if (IncomeType.equals("Red Packets")) {
                Amounts[1] += Amount;
            }
            else if (IncomeType.equals("Work")) {
                Amounts[2] += Amount;
            }
            else if (IncomeType.equals("Reward")) {
                Amounts[3] += Amount;
            }
            else if (IncomeType.equals("Others")) {
                Amounts[4] += Amount;
            }
            else {
                System.out.println("fuck my life");
                System.out.println("ExpenseType: " + IncomeType);
            }
        }

        float sum = Amounts[0] + Amounts[1] + Amounts[2] + Amounts[3] + Amounts[4];
        System.out.println("Amount0" + Amounts[0]);
        System.out.println("Amount1" + Amounts[1]);
        System.out.println("Amount2" + Amounts[2]);
        System.out.println("Amount3" + Amounts[3]);
        System.out.println("Amount4" + Amounts[4]);
        System.out.println("Sum: " + sum);

        PieChart pieChart = (PieChart) rootView.findViewById(R.id.pie_chart);
        pieChart.setUsePercentValues(false);
        pieChart.setHoleRadius(10f);
        pieChart.setTransparentCircleRadius(10f);

        List<PieEntry> value = new ArrayList<>();
        value.add(new PieEntry(Amounts[0], "Pocket Money"));
        value.add(new PieEntry(Amounts[1], "Red Packets"));
        value.add(new PieEntry(Amounts[2], "Work"));
        value.add(new PieEntry(Amounts[3], "Reward"));
        value.add(new PieEntry(Amounts[4], "Others"));

        final int[] MY_COLORS = {Color.rgb(0,200,255), Color.rgb(255,0,0), Color.rgb(255,190,0),
                Color.rgb(146,208,80), Color.rgb(127,127,127)};
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c: MY_COLORS) {
            colors.add(c);
        }

        Description desc = new Description();
        desc.setText("");
        desc.setTextSize(60f);
        pieChart.setDescription(desc);

        PieDataSet pieDataSet = new PieDataSet(value, "");
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new MyValueFormatter());
        pieData.setValueTextSize(30f);

        pieChart.setData(pieData);
        pieChart.setDrawEntryLabels(false);
        pieChart.animateXY(1400,1400);
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(30f);
        pieChart.setTransparentCircleRadius(35f);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationAngle(0);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = pieChart.getLegend();
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setTextSize(14f);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setWordWrapEnabled(true);
        l.setDrawInside(false);
        l.getCalculatedLineSizes();
        l.setYOffset(20f);

        // entry label styling
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(12f);

        return rootView;
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
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            return e.toString();
        }
        return text;
    }
}