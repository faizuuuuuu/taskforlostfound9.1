package com.example.lostfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

public class ListItemsActivity extends AppCompatActivity {

    ListView listViewItems;
    ArrayAdapter<String> adapter;
    List<HashMap<String, String>> itemList;
    List<String> itemTitles;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        listViewItems = findViewById(R.id.listViewItems);
        dbHelper = new DatabaseHelper(this);
        loadList();

        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> selectedItem = itemList.get(position);
                Intent intent = new Intent(ListItemsActivity.this, ItemDetailActivity.class);
                intent.putExtra("id", selectedItem.get("id"));
                intent.putExtra("description", selectedItem.get("description"));
                intent.putExtra("date", selectedItem.get("date"));
                intent.putExtra("location", selectedItem.get("location"));
                startActivityForResult(intent, 1);
            }
        });
    }

    // Method to load data into list
    private void loadList() {
        itemList = dbHelper.getAllAdverts();
        itemTitles = new ArrayList<>();

        for (HashMap<String, String> item : itemList) {
            String title = item.get("type") + ": " + item.get("description");
            itemTitles.add(title);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemTitles);
        listViewItems.setAdapter(adapter);
    }

    // Refresh list if item was removed
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            loadList();
            adapter.notifyDataSetChanged();
        }
    }
}
