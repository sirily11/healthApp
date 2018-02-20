package com.example.qiweili.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class EditMeal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                goBack(v);
            }
        });
    }

    public void goBack(View v) {
        Intent intent = new Intent(this, MyMeals.class);
        startActivity(intent);
    }
}