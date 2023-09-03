package com.example.personal_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_option);
    }
    public void onMatrixDeterminantClick(View view) {
        // Handle the "Matrix Determinant" button click here
        // Navigate to the DeterminantActivity-
        Intent intent = new Intent(MainActivity.this, MainActivity_determinant.class);
        startActivity(intent);
    }
    public void onMatrixInverseClick(View view){
        Intent intent = new Intent(MainActivity.this, MainActivity_Inverse.class);
        startActivity(intent);
    }
    public void onMatrixAdditionClick(View view){
        Intent intent = new Intent(MainActivity.this, MainActivity_Addition.class);
        startActivity(intent);
    }
    public void onMatrixSubtractClick(View view){
        Intent intent = new Intent(MainActivity.this, MainActivity_subtract.class);
        startActivity(intent);
    }
    public void onMatrixMultiplyClick(View view){
        Intent intent = new Intent(MainActivity.this, MainActivity_Multiply.class);
        startActivity(intent);
    }
}
