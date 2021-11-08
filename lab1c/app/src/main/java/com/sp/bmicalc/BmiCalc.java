package com.sp.bmicalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BmiCalc extends AppCompatActivity {
    private EditText weight_obj;
    private EditText height_obj;
    private Button submit;
    private static final String TAG = "Lab1c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        weight_obj = findViewById(R.id.weight);
        height_obj = findViewById(R.id.height);
        submit = findViewById(R.id.button);
        submit.setOnClickListener(onSave);
    }

    private View.OnClickListener onSave = new View.OnClickListener() {
        @Override
        public void onClick(View V) {
            // To read data
            String weightStr = weight_obj.getText().toString();
            String heightStr = height_obj.getText().toString();
            final TextView text = (TextView) findViewById(R.id.result);
            double height = Double.parseDouble(heightStr);
            double weight = Double.parseDouble(weightStr);
            double bmi = weight/(height*height);
            String finalStr = "BMI - ";
            String status = "NA";

            Log.d(TAG, "Before formatting bmiStr");
            String bmiStr = String.format("%.2f", bmi);
            Log.d(TAG, "After formating string");

            if (bmi < 18.5)
                status = "Underweight";
            else if (bmi < 24.9)
                status = "Healthy";
            else if (bmi < 29.9)
                status = "Overweight";
            else if (bmi > 30)
                status = "Obese";

            finalStr = finalStr + bmiStr + " " + status;
            text.setText(finalStr);
        }
    };
}