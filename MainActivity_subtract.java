package com.example.personal_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity_subtract extends AppCompatActivity {

    private EditText editTextRows;
    private EditText editTextColumns;
    private LinearLayout matrixLayout1;
    private LinearLayout matrixLayout2;
    private Button buttonGenerateMatrices;
    private Button buttoncalculate;
    private LinearLayout sub_result;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_subtract);
        editTextRows = findViewById(R.id.editTextRows);
        editTextColumns = findViewById(R.id.editTextColumns);
        matrixLayout1 = findViewById(R.id.matrixLayout1_s);
        matrixLayout2 = findViewById(R.id.matrixLayout2_s);
        sub_result = findViewById(R.id.sub_result);  // Add this line to initialize sum_result
        buttonGenerateMatrices = findViewById(R.id.buttonGenerateMatrices_s);
        buttonGenerateMatrices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateMatrices();
            }
        });

        buttoncalculate = findViewById(R.id.buttonCalculate_s);
        buttoncalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float[][] l1 = convert_to_float(matrixLayout1);
                float[][] l2 = convert_to_float(matrixLayout2);
                if (l1 != null && l2 != null) {
                    Matrix layout1 = new Matrix(l1);
                    Matrix layout2 =  new Matrix(l2);
                    Matrix result = layout1.subtract(layout2);
                    if (result != null) {
                        generateResultMatrixLayout(result.matrix);
                    } else {
                        Toast.makeText(MainActivity_subtract.this, "Incompatible matrices... Make sure of same dimension", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity_subtract.this, "Please fill in all matrix values", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void generateMatrices() {
        int rows = Integer.parseInt(editTextRows.getText().toString());
        int columns = Integer.parseInt(editTextColumns.getText().toString());

        matrixLayout1.removeAllViews();
        matrixLayout2.removeAllViews();
        generateMatrix(matrixLayout1, rows, columns);
        generateMatrix(matrixLayout2, rows, columns);
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
        int rows = matrixLayout.getChildCount();
        if (rows == 0) {
            return null;
        }
        int columns = ((LinearLayout) matrixLayout.getChildAt(0)).getChildCount();
        float[][] matrixValues = new float[rows][columns];

        for (int i = 0; i < rows; i++) {
            LinearLayout rowLayout = (LinearLayout) matrixLayout.getChildAt(i);
            for (int j = 0; j < columns; j++) {
                EditText editText = (EditText) rowLayout.getChildAt(j);
                String valueStr = editText.getText().toString();
                if (isValidNumericFloat(valueStr)) {
                    matrixValues[i][j] = Float.parseFloat(valueStr);
                } else {
                    Toast.makeText(this, "Please enter only numbers", Toast.LENGTH_SHORT).show();
                    return null;  // Return null if there's an invalid input
                }
            }
        }
        return matrixValues;
    }

    private void generateResultMatrixLayout(float[][] resultMatrix) {
        sub_result.removeAllViews();

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
            sub_result.addView(rowLayout);
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
