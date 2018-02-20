package com.example.qiweili.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class deleteMeal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_meal);

        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        goBack(v);
                    }
                });
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, MyMeals.class);
        startActivity(intent);
    }
}