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
        Button addFood = findViewById(R.id.addnewfoodbutton);
        Button editMeal = findViewById(R.id.editfoodbutton);
        Button deleteMeal = findViewById(R.id.deletefoodbutton);
        Button back = findViewById(R.id.backButton);

        addFood.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addNewFood(v);
            }
        });

        editMeal.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                editMeal(v);
            }
        });

        deleteMeal.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
               deleteMeal(v);
            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                goBack(v);
            }
        });
    }

    //called upon page load, gets the users meal data
    private void loadUser(){ }

    public void goToWater(View view) {
        Intent intent = new Intent(this, MyWater.class);
        startActivity(intent);
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
        //this goes back to homepage
    }

    //called when the user touches the add food button
    public void addNewFood(View view){
        Intent intent = new Intent(this, addFood.class);
        startActivity(intent);
    }

    //called when the user touches the edit food button
    public void editMeal(View view) {
        Intent intent = new Intent(this, EditMeal.class);
        startActivity(intent);
    }

    //called when the user touches the delete food button
    public void deleteMeal(View view) {
        Intent intent = new Intent(this, deleteMeal.class);
        startActivity(intent);
    }
}