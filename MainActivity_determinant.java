package com.example.personal_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.personal_project.R;

public class MainActivity_determinant extends AppCompatActivity {

    private EditText editTextRows;
    private EditText editTextColumns;
    private LinearLayout matrixLayout;
    private Button buttonGenerateMatrices;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_determinant);

        // Get references to views
        editTextRows = findViewById(R.id.editTextRows);
        editTextColumns = findViewById(R.id.editTextColumns);
        matrixLayout = findViewById(R.id.matrixLayout);

        // Set click listener for "Generate Matrix" button
        Button buttonGenerateMatrix = findViewById(R.id.buttonGenerateMatrix);
        buttonGenerateMatrix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateMatrix();
            }
        });
        Button buttonCalculateDeterminant = findViewById(R.id.buttonCalculateDeterminant);
        buttonCalculateDeterminant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateDeterminant();
            }
        });
    }

    private void generateMatrix() {
        // Read user input for rows and columns
        int rows = Integer.parseInt(editTextRows.getText().toString());
        int columns = Integer.parseInt(editTextColumns.getText().toString());

        // Clear the existing matrix layout, if any
        matrixLayout.removeAllViews();

        // Generate the matrix grid
        for (int i = 0; i < rows; i++) {
            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            for (int j = 0; j < columns; j++) {
                EditText editText = new EditText(this);
                editText.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                rowLayout.addView(editText);
            }
            matrixLayout.addView(rowLayout);
        }
    }

    private void calculateDeterminant() {
        // Retrieve the matrix from the dynamically generated EditText fields
        int rows = Integer.parseInt(editTextRows.getText().toString());
        int columns = Integer.parseInt(editTextColumns.getText().toString());
        float[][] matrix = new float[rows][columns];

        for (int i = 0; i < rows; i++) {
            LinearLayout rowLayout = (LinearLayout) matrixLayout.getChildAt(i);
            for (int j = 0; j < columns; j++) {
                EditText editText = (EditText) rowLayout.getChildAt(j);
                String inputText = editText.getText().toString();
                if (isValidNumericFloat(inputText)) {

                    float value = Float.parseFloat(editText.getText().toString());
                    matrix[i][j] = value;
                }
                else{
                    Toast.makeText(this, "please enter only number", Toast.LENGTH_SHORT).show();
                }
            }

            // Calculate the determinant
            double determinant = getDeterminant(matrix);

            // Display the determinant using a Toast
            Toast.makeText(this, "Determinant: " + determinant, Toast.LENGTH_SHORT).show();
        }
    }

    private double getDeterminant(float[][] matrix) {
        // ... (previously defined determinant logic)
        // You can keep the determinant logic as it is
        int rows = matrix.length;
        int columns = matrix[0].length;

        if (rows != columns) {
            // Show a Toast message to the user
            Toast.makeText(this, "Determinant does not exist for a non-square matrix.", Toast.LENGTH_SHORT).show();
            return 0; // Return 0 as there is no valid determinant for non-square matrices.
        }

        double result = 0;

        if (rows == 2) {
            result = matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
            return result;
        } else {
            for (int i = 0; i < columns; i++) {
                result = result + Math.pow(-1, i) * matrix[0][i] * getDeterminant(submatrix(rows, 0, i, matrix));
            }
            return result;
        }
    }

    private float[][] submatrix(int n, int i, int j, float[][] matrix) {
        int a = 0;
        int b = 0;
        float[][] d = new float[n - 1][n - 1];
        for (int p = 0; p < n; p++) {
            if (p != i) {
                for (int q = 0; q < n; q++) {
                    if (q != j) {
                        d[a][b] = matrix[p][q];
                        b++;
                    }
                }
                b = 0;
                a++;
            }
        }
        return d;
    }


    private boolean isValidNumericFloat(String input) {
        try {
            Float.parseFloat(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    }
