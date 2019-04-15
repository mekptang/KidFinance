package com.example.kidfinance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SetTargetFragment extends Fragment {

    static public ArrayList<String> i_name = new ArrayList<String>();
    static public ArrayList<String> i_image_url = new ArrayList<String>();
    EditText item_name;
    ImageButton add_award;
    ImageButton set_next;
    MyListAdapter myListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_target, container, false);

        add_award = (ImageButton)view.findViewById(R.id.add_award);
        set_next = (ImageButton)view.findViewById(R.id.set_next);
        item_name = (EditText)view.findViewById(R.id.item_name);

        ListView lv = (ListView) view.findViewById(R.id.target_item_list);
        //generateListContent();
        myListAdapter = new MyListAdapter(getContext(), R.layout.item_list_set_target, i_name);
        lv.setAdapter(myListAdapter);

        add_award.setOnClickListener(new View.OnClickListener() {
            //add list item, move to next activity
            @Override
            public void onClick(View v) {
                if(item_name.getText().toString().matches("")){
                    Toast.makeText(getContext(), "Name Cannot Be Empty", Toast.LENGTH_SHORT).show();
                }else{
                    i_name.add(item_name.getText().toString());
                    myListAdapter.notifyDataSetChanged();
                }
            }
        });

        set_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Pass  item data to Award
                Toast.makeText(getContext(), "set_next", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    //NOTICE
//    private void generateListContent() {
//        for(int i = 0; i < 10; i++){
//            i_name.add("this is row number " + i);
//        }
//    }

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
