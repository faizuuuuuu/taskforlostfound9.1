package com.example.lostfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {

    TextView tvDescription, tvDate, tvLocation;
    Button btnRemove;
    String advertId;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        tvDescription = findViewById(R.id.tvDescription);
        tvDate = findViewById(R.id.tvDate);
        tvLocation = findViewById(R.id.tvLocation);
        btnRemove = findViewById(R.id.btnRemove);

        dbHelper = new DatabaseHelper(this);

        // Get data from intent
        advertId = getIntent().getStringExtra("id");
        String description = getIntent().getStringExtra("description");
        String date = getIntent().getStringExtra("date");
        String location = getIntent().getStringExtra("location");

        tvDescription.setText("Description: " + description);
        tvDate.setText("Date: " + date);
        tvLocation.setText("Location: " + location);

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean deleted = dbHelper.deleteAdvert(advertId);
                if (deleted) {
                    Toast.makeText(ItemDetailActivity.this, "Item removed successfully", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);  // this will send the  success to parent activity
                    finish();  // return
                } else {
                    Toast.makeText(ItemDetailActivity.this, "Failed to remove item", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
