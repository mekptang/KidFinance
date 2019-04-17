package com.example.kidfinance;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import static android.app.Activity.RESULT_OK;

public class SetTargetFragment extends Fragment {
    ArrayList<ItemListSample> itemListSample;

    private RecyclerView mRecyclerView;
    private ItemListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManger;
    private View view;


    private ImageButton item_insert;
    private TextView item;
    private Button select;
    private ImageView item_preview;
    private ImageButton next;

    private static final int PICK_IMAGE = 100;
    public Uri imageUri;
    private String imagePath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.fragment_target, container, false);

        createItemListSample();
        buildRecyclerView();
        setButtons();

        return view;
    }
    public void insertItem(String item){
        itemListSample.add(new ItemListSample(imagePath, item));
        mAdapter.notifyDataSetChanged();
    }

    public void removeItem(int position){
        itemListSample.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    public void addNumItem(int position){
        itemListSample.get(position).addNumberOfItem();
        mAdapter.notifyDataSetChanged();
    }

    public void dropNumItem(int position){
        itemListSample.get(position).dropNumberOfItem();
        mAdapter.notifyDataSetChanged();
    }

    public void createItemListSample(){
        itemListSample = new ArrayList<>();

        //itemListSample.add(new ItemListSample(R.drawable.ic_menu_camera, "apple"));
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void buildRecyclerView(){
        mRecyclerView = view.findViewById(R.id.target_item_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManger = new LinearLayoutManager(getContext());
        mAdapter = new ItemListAdapter(itemListSample);

        mRecyclerView.setLayoutManager(mLayoutManger);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ItemListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //no use
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }

            @Override
            public void onAddItemClick(int position) {
                addNumItem(position);
            }

            @Override
            public void onDropItemClick(int position) {
                dropNumItem(position);
            }
        });
    }

    public void setButtons(){
        item_insert = view.findViewById(R.id.item_insert);
        item = view.findViewById(R.id.item);
        select = view.findViewById(R.id.select_item);
        item_preview = view.findViewById(R.id.item_preview);
        next = view.findViewById(R.id.set_next);

        item_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check = item.getText().toString();
                if(!check.equals("")){
                    insertItem(item.getText().toString());
                    item.setText("");
                }else{
                    Toast.makeText(getContext(),"Please Input Item Name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String listSerializedToJson = new Gson().toJson(itemListSample);
                Toast.makeText(getContext(),listSerializedToJson, Toast.LENGTH_SHORT).show();
//                intent.putExtra("LIST_OF_OBJECTS", listSerializedToJson);
//                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imagePath = getPath(imageUri);
            item_preview.setImageURI(imageUri);
        }
    }
}
