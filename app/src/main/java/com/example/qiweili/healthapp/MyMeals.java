package com.example.qiweili.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MyMeals extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_meals);
        Toolbar toolbar = findViewById(R.id.toolbar);
        loadUser();
        setSupportActionBar(toolbar);
        final Button addFood = findViewById(R.id.addnewfoodbutton);
        Button editFood = findViewById(R.id.editfoodbutton);
        final Button deleteFood = findViewById(R.id.deletefoodbutton);

        addFood.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addNewFood(v);
            }
        });

        editFood.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                editFood(v);
            }
        });

        deleteFood.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
               deleteFood(v);
            }
        });
    }

    //called upon page load, gets the users meal data
    private void loadUser(){

    }


    //called when the user touches the add food button
    public void addNewFood(View view){
        Intent intent = new Intent(this, addFood.class);
        startActivity(intent);
    }

    //called when the user touches the edit food button
    public void editFood(View view) {
        Intent intent = new Intent(this, EditMeal.class);
        startActivity(intent);
    }

    //called when the user touches the delete food button
    public void deleteFood(View view) {
        Intent intent = new Intent(this, deleteFood.class);
        startActivity(intent);
    }
}