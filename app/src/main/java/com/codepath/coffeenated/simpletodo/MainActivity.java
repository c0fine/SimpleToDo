package com.codepath.coffeenated.simpletodo;

import static org.apache.commons.io.FileUtils.readLines;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    List<String> items;
    Button buttonAdd;
    EditText editItems;
    RecyclerView recylerViewItems;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAdd  = findViewById(R.id.buttonAdd);
        editItems = findViewById(R.id.editItems);
        recylerViewItems = findViewById(R.id.recylerViewItems);




        loadItems();




        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener(){

            @Override
            public void onItemLongClicked(int position) {
                //Delete the item from the model
                items.remove(position);
                //notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Item was removed",Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };


        itemsAdapter = new ItemsAdapter(items, onLongClickListener);


        recylerViewItems.setAdapter(itemsAdapter);
        recylerViewItems.setLayoutManager(new LinearLayoutManager( this));
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = editItems.getText().toString();
                //add item to the model
                items.add(todoItem);
                //tell adapter that item is inserted
                itemsAdapter.notifyItemInserted(items.size() -1);
                editItems.setText("");
                Toast.makeText(getApplicationContext(),"Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }
    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }
    //Thsi function will load items by reading every line of the data file
    private void loadItems() {
        try{
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "error reading items", e);
            items = new ArrayList<>();

        }
    //This functions will save items by writing them into the data file
    }
    private void saveItems(){
        try{
            FileUtils.writeLines(getDataFile(), items);

        }catch (IOException e){
            Log.e("MainActivity","Error writing items", e);
        }
    }
}