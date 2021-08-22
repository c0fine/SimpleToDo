package com.codepath.coffeenated.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//displays the data from the model into a row in the recycler view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public interface OnLongClickListener {
        void onItemLongClicked(int position);
    }
    List<String> items;
    OnLongClickListener longClickListener;
    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //uses layout inflator to inflate the view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);

        //wraps it inside a view holder and return it
        return new ViewHolder(todoView);
    }
    //binds data to a view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get item at pos
        String item = items.get(position);
        //bind the item into the viewholder
        holder.bind(item);
    }

    //Tells the recycler view how many items are in the list
    @Override
    public int getItemCount() {
        return items.size();
    }

    //Container to give easy access to view holder
    //provide access to vies that represent each row in list
    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }
        //Update view inside of view holder with this data
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //Tell which item was longpressed
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }

}
