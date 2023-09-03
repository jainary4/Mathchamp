package com.example.personal_project;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity_Inverse extends AppCompatActivity {
    private EditText editTextRows;
    private EditText editTextColumns;
    private LinearLayout matrixLayout;
    private Button buttoncalculate;
    private Button buttonGenerateMatrix;
    private LinearLayout inverse_layout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inverse);
        editTextRows = findViewById(R.id.editTextRows);
        editTextColumns = findViewById(R.id.editTextColumns);
        matrixLayout = findViewById(R.id.matrixLayout1_inverse);
        inverse_layout = findViewById(R.id.inverse_layout);
        buttonGenerateMatrix = findViewById(R.id.buttonGenerateMatrices_i);
        buttonGenerateMatrix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateMatrices();
            }
        });
        buttoncalculate = findViewById(R.id.buttonCalculate_inverse);
        buttoncalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float[][] l = convert_to_float(matrixLayout);
                Matrix m = new Matrix(l);
                Matrix inverse = m.getInverse();
                if (inverse != null) {
                    generateResultMatrixLayout(inverse.matrix);
                } else {
                    Toast.makeText(MainActivity_Inverse.this, "Inverse does not exist for this matrix", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void generateMatrices() {

        int rows = Integer.parseInt(editTextRows.getText().toString());
        int columns = Integer.parseInt(editTextColumns.getText().toString());


        matrixLayout.removeAllViews();
        generateMatrix(matrixLayout, rows, columns);
        // after generating the matrix the user fill it with values and after the user clicks
        // after the user clicks the calculate matrix button we calculate the sum and return it to the user.


    }

    private void generateMatrix(LinearLayout l, int rows, int columns) {
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
            l.addView(rowLayout);
        }
    }

    private float[][] convert_to_float(LinearLayout matrixLayout) {
        // Get the number of rows and columns from the first matrix layout
        int rows = matrixLayout.getChildCount();
        int columns = ((LinearLayout) matrixLayout.getChildAt(0)).getChildCount();

        // Initialize two 2D arrays to store the values of the two matrices
        float[][] matrix1Values = new float[rows][columns];

        // Extract the values from the first matrix layout and store them in the matrix1Values array
        for (int i = 0; i < rows; i++) {
            LinearLayout rowLayout = (LinearLayout) matrixLayout.getChildAt(i);
            for (int j = 0; j < columns; j++) {
                EditText editText = (EditText) rowLayout.getChildAt(j);
                String valueStr = editText.getText().toString();
                if (isValidNumericFloat(valueStr)) {
                    matrix1Values[i][j] = Float.parseFloat(valueStr);
                } else {
                    Toast.makeText(this, "Please enter only numbers", Toast.LENGTH_SHORT).show();
                    return null;  // Return null if there's an invalid input
                }
            }
        }
        return matrix1Values;
    }

    private void generateResultMatrixLayout(float[][] resultMatrix) {
        inverse_layout.removeAllViews();

        for (int i = 0; i < resultMatrix.length; i++) {
            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            for (int j = 0; j < resultMatrix[i].length; j++) {
                EditText editText = new EditText(this);
                editText.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                editText.setFocusable(false);
                editText.setClickable(false);
                editText.setText(String.valueOf(resultMatrix[i][j]));
                rowLayout.addView(editText);
            }
            inverse_layout.addView(rowLayout);
        }
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
