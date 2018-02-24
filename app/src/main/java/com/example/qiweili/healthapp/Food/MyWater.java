package com.example.qiweili.healthapp.Food;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.qiweili.healthapp.R;

public class MyWater extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_water);

        Button back = findViewById(R.id.back);

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
