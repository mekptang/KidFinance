package com.example.kidfinance;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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

import static android.app.Activity.RESULT_OK;

public class SetTargetFragment extends Fragment {
    private static final int PICK_IMAGE = 100;
    private static final int PICK_TARGET = 200;
    private static final int PICK_DESCRIPTION = 300;
    private static int pos;
    public Uri imageUri;
    ArrayList<ItemListSample> itemListSample;
    ArrayList<AwardModel> awardList = null;
    private RecyclerView mRecyclerView;
    private ItemListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManger;
    private View view;
    private ImageButton item_insert;
    private TextView item;
    private ImageButton select;
    private ImageView item_preview;
    private Button target_button;
    private ImageButton next;
    private String imagePath;
    private String target = "0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        view = inflater.inflate(R.layout.fragment_target, container, false);

        createItemListSample();
        buildRecyclerView();
        setButtons();

        return view;
    }

    public void insertItem(String item) {
        itemListSample.add(new ItemListSample(imagePath, item));
        mAdapter.notifyDataSetChanged();
    }

    public void removeItem(int position) {
        itemListSample.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    public void addNumItem(int position) {
        itemListSample.get(position).addNumberOfItem();
        mAdapter.notifyDataSetChanged();
    }

    public void dropNumItem(int position) {
        itemListSample.get(position).dropNumberOfItem();
        mAdapter.notifyDataSetChanged();
    }

    public void changeItemDescription(int position, String description) {
        itemListSample.get(position).setDescription(description);
        // Toast.makeText(getContext(), itemListSample.get(position).getDescription(), Toast.LENGTH_SHORT).show();
    }

    public void createItemListSample() {
        itemListSample = new ArrayList<>();
        //itemListSample.add(new ItemListSample(R.drawable.ic_menu_camera, "apple"));
    }

    public void getItemPosition(int position) {
        pos = position;
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    public void buildRecyclerView() {
        mRecyclerView = view.findViewById(R.id.target_item_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManger = new LinearLayoutManager(getContext());
        mAdapter = new ItemListAdapter(itemListSample);

        mRecyclerView.setLayoutManager(mLayoutManger);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ItemListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
                getItemPosition(position);
                Intent in = new Intent(getActivity(), ReturnDescriptionActivity.class);
                startActivityForResult(in, PICK_DESCRIPTION);
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

    public void setButtons() {
        item_insert = view.findViewById(R.id.item_insert);
        item = view.findViewById(R.id.item);
        select = view.findViewById(R.id.select_item);
        item_preview = view.findViewById(R.id.item_preview);
        target_button = view.findViewById(R.id.target);
        next = view.findViewById(R.id.set_next);

        item_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check = item.getText().toString();
                if (!check.equals("") && (imagePath != null)) {
                    insertItem(item.getText().toString());
                    item.setText("");
                } else {
                    Toast.makeText(getContext(), "Please Input Item Name or Select Image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        target_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float current_balance = 0;
                if (fileExists(getContext(), "kf_saving_money_config.txt") == true) {
                    current_balance = Float.parseFloat(loadTextFile("kf_saving_money_config.txt"));
                }

                Intent in = new Intent(getActivity(), ReturnTargetActivity.class);
                in.putExtra("current_balance", current_balance);
                startActivityForResult(in, PICK_TARGET);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtain current balance and current target
                float current_balance = 0;
                float current_target = 0;
                if (fileExists(getContext(), "kf_saving_money_config.txt") == true) {
                    current_balance = Float.parseFloat(loadTextFile("kf_saving_money_config.txt"));
                }
                if (fileExists(getContext(), "kf_target_money_config.txt") == true) {
                    current_target = Float.parseFloat(loadTextFile("kf_target_money_config.txt"));
                }

                // Only allow the user to set target when the old target is finished + The Present List is empty
                if (current_balance >= current_target || current_target == 0) {
                    String json = loadTextFile("kf_target_awardListJSON_config.txt");
                    if (json == "") {
                        json = "[]";
                    }
                    try {
                        JsonArray arr = new JsonParser().parse(json).getAsJsonArray();

                        for (JsonElement je : arr) {
                            Gson gson = new Gson();
                            AwardModel am = gson.fromJson(je,  AwardModel.class);
                            awardList.add(am);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (awardList == null) {
                        if ((itemListSample != null) && (target.equals("0") == false)) {
                            String listSerializedToJson = new Gson().toJson(itemListSample);
                            writeToFile(listSerializedToJson, getContext(), "kf_target_awardListJSON_config.txt");

                            // plain text for saving and target money
                            writeToFile(target, getContext(), "kf_target_money_config.txt");
                            // writeToFile("0", getContext(), "kf_saving_money_config.txt");

                            Toast.makeText(getContext(), "The New Target is " + target, Toast.LENGTH_LONG).show();

                            FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProgessFragment());
                            ft.commit();
                        }
                        else {
                            Toast.makeText(getContext(), "Please set BOTH the Target and Award before continue!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Please take one present before setting a new target!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Please finish the current target before setting a new one!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imagePath = getPath(imageUri);
            item_preview.setImageURI(imageUri);
        } else if (resultCode == RESULT_OK && requestCode == PICK_TARGET) {
            String result = data.getExtras().getString("result");
            target = result;
            target_button.setText("Target:\n$" + target);
        } else if (resultCode == RESULT_OK && requestCode == PICK_DESCRIPTION) {
            String result = data.getExtras().getString("result");
            changeItemDescription(pos, result);
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

    private void writeToFile(String data, Context context, String fileName) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
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
