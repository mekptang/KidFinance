package com.example.kidfinance;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecordIncomeAdapter extends RecyclerView.Adapter<RecordIncomeAdapter.MyViewHolder> {

    private ArrayList<IncomeData> income_list;

    public RecordIncomeAdapter(ArrayList<IncomeData> list) {
        income_list = list;
    }

    @Override
    public RecordIncomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_card_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final int[] MY_COLORS = {Color.rgb(0, 200, 255), Color.rgb(255, 0, 0), Color.rgb(255, 190, 0),
                Color.rgb(146, 208, 80), Color.rgb(127, 127, 127)};

        if (income_list.get(position).getIncomeType().equals("Pocket Money") == true) {
            holder.record_type_image.setBackgroundColor(MY_COLORS[0]);
        } else if (income_list.get(position).getIncomeType().equals("Red Packets") == true) {
            holder.record_type_image.setBackgroundColor(MY_COLORS[1]);
        } else if (income_list.get(position).getIncomeType().equals("Work") == true) {
            holder.record_type_image.setBackgroundColor(MY_COLORS[2]);
        } else if (income_list.get(position).getIncomeType().equals("Reward") == true) {
            holder.record_type_image.setBackgroundColor(MY_COLORS[3]);
        } else if (income_list.get(position).getIncomeType().equals("Others") == true) {
            holder.record_type_image.setBackgroundColor(MY_COLORS[4]);
        }

        holder.record_type.setText(income_list.get(position).getIncomeType());
        holder.record_remark.setText(income_list.get(position).getRemark());
        holder.record_date.setText(income_list.get(position).getDate());
        holder.record_amount.setText(income_list.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return income_list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public ImageView record_type_image;
        public TextView record_type;
        public TextView record_remark;
        public TextView record_date;
        public TextView record_amount;

        public MyViewHolder(View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view);
            record_type_image = (ImageView) v.findViewById(R.id.record_category_image);
            record_type = (TextView) v.findViewById(R.id.record_category);
            record_remark = (TextView) v.findViewById(R.id.record_remarks);
            record_date = (TextView) v.findViewById(R.id.record_date);
            record_amount = (TextView) v.findViewById(R.id.record_amount);
        }
    }
}
