package com.example.kidfinance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class SetTargetActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManger;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_target);
        ArrayList<ItemListSample> itemListSample = new ArrayList<>();

        //itemListSample.add(new ItemListSample(R.drawable.ic_menu_camera, "ok"));

        mRecyclerView = findViewById(R.id.target_item_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManger = new LinearLayoutManager(this);
        mAdapter = new ItemListAdapter(itemListSample);

        mRecyclerView.setLayoutManager(mLayoutManger);
        mRecyclerView.setAdapter(mAdapter);
    }
}
