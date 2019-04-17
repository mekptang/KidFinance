package com.example.kidfinance;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>{
    private ArrayList<ItemListSample> mItemList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onAddItemClick(int position);
        void onDropItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class ItemListViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageButton mDeleteImage;
        public ImageButton mAddItem;
        public ImageButton mDropItem;

        public ItemListViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.item_logo);
            mTextView1 = itemView.findViewById(R.id.item_name);
            mTextView2 = itemView.findViewById(R.id.item);
            mDeleteImage = itemView.findViewById(R.id.item_cancel);
            mAddItem = itemView.findViewById(R.id.plus_item);
            mDropItem = itemView.findViewById(R.id.minus_item);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            mAddItem.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onAddItemClick(position);
                        }
                    }
                }
            });

            mDropItem.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onDropItemClick(position);
                        }
                    }
                }
            });

        }
    }

    public ItemListAdapter(ArrayList<ItemListSample> itemList){
        mItemList = itemList;
    }

    @Override
    public ItemListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_set_target, viewGroup, false);
        ItemListViewHolder lvh = new ItemListViewHolder(v, mListener);
        return lvh;
    }

    @Override
    public void onBindViewHolder(ItemListViewHolder itemListViewHolder, int i) {
        ItemListSample currentItem = mItemList.get(i);
        itemListViewHolder.mImageView.setImageURI(Uri.parse(currentItem.getImageResource()));
        itemListViewHolder.mTextView1.setText(currentItem.getItemName());
        itemListViewHolder.mTextView2.setText(String.valueOf(currentItem.getNumberOfItem()));
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
