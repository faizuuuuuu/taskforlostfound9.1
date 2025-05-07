package com.example.lostfoundapp;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class PostAdvertActivity extends AppCompatActivity {

    EditText etName, etPhone, etDescription, etDate, etLocation;
    RadioGroup radioGroupType;
    Button btnSave;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_advert);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etDescription = findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        etLocation = findViewById(R.id.etLocation);
        radioGroupType = findViewById(R.id.radioGroupType);
        btnSave = findViewById(R.id.btnSave);

        dbHelper = new DatabaseHelper(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                String description = etDescription.getText().toString();
                String date = etDate.getText().toString();
                String location = etLocation.getText().toString();

                String type = ((RadioButton)findViewById(radioGroupType.getCheckedRadioButtonId()))
                        .getText().toString();

                if (name.isEmpty() || phone.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty()) {
                    Toast.makeText(PostAdvertActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    boolean inserted = dbHelper.insertAdvert(name, phone, description, date, location, type);
                    if (inserted) {
                        Toast.makeText(PostAdvertActivity.this, "Advert saved successfully", Toast.LENGTH_SHORT).show();
                        finish(); // go back to main
                    } else {
                        Toast.makeText(PostAdvertActivity.this, "Failed to save advert", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
