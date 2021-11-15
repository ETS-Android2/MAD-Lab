package com.sp.p2032203_formula_calculator;

import static java.lang.Double.NaN;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class p2032203_Formula_Calculator extends AppCompatActivity {
    private EditText xObj;
    private EditText yObj;
    private EditText zObj;
    private Button buttonSave;
    private Button buttonReset;
    private TextView outputObj;
    private static final String TAG = "labtest";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        xObj = findViewById(R.id.x);
        yObj = findViewById(R.id.y);
        zObj = findViewById(R.id.z);
        outputObj = findViewById(R.id.output);
        buttonSave = findViewById(R.id.button);
        buttonSave.setOnClickListener(onReset);
        buttonSave = findViewById(R.id.button2);
        buttonSave.setOnClickListener(onSave);
    }

    private View.OnClickListener onSave = new View.OnClickListener() {
       public void onClick(View V){

           if (xObj == null) {
               Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
           } else {
               String xStr = xObj.getText().toString();
               String yStr = yObj.getText().toString();
               String zStr = zObj.getText().toString();

               Double x = Double.parseDouble(xStr);
               Double y = Double.parseDouble(yStr);
               Double z = Double.parseDouble(zStr);
               if (x == 0.00 && y == 0.00 && z == 0.00) {
                   outputObj.setText("Infinity");
                   xObj.setText("1");
                   yObj.setText("1");
                   zObj.setText("1");
               } else {
                   double result = x * z + Math.pow(z, 2) / Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
                   outputObj.setText(String.format("%.2f", result));
               }
           }
       }
    };

    private View.OnClickListener onReset = new View.OnClickListener() {
        public void onClick(View V){
            xObj.setText("0");
            yObj.setText("0");
            zObj.setText("0");
        }
    };
}