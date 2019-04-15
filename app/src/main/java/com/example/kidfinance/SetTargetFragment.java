package com.example.kidfinance;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SetTargetFragment extends Fragment {

    private ArrayList<String> data = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_target, container, false);
        ListView lv = (ListView) view.findViewById(R.id.target_item_list);
        generateListContent();
        lv.setAdapter(new MyListAdapter(getContext(), R.layout.item_list_set_target, data));
        return view;
    }

    private void generateListContent() {
        for(int i = 0; i < 10; i++){
            data.add("this is row number " + i);
        }
    }

    private class MyListAdapter extends ArrayAdapter<String>{
        private int layout;
        private MyListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.item_logo = (ImageView) convertView.findViewById(R.id.item_logo);
                viewHolder.item_count = (TextView) convertView.findViewById(R.id.item_count);
                viewHolder.plus_item = (ImageButton) convertView.findViewById(R.id.plus_item);
                viewHolder.minus_item = (ImageButton) convertView.findViewById(R.id.minus_item);
                viewHolder.item_cancel = (ImageButton) convertView.findViewById(R.id.item_cancel);

                viewHolder.plus_item.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Toast.makeText(getContext(), "+", Toast.LENGTH_SHORT).show();
                    }
                });
                viewHolder.minus_item.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Toast.makeText(getContext(), "-", Toast.LENGTH_SHORT).show();
                    }
                });
                viewHolder.item_cancel.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Toast.makeText(getContext(), "x", Toast.LENGTH_SHORT).show();
                    }
                });

                convertView.setTag(viewHolder);
            }

            return convertView;
        }
    }
    public class ViewHolder{
        ImageView item_logo;
        TextView item_count;
        ImageButton plus_item;
        ImageButton minus_item;
        ImageButton item_cancel;
    }
}
