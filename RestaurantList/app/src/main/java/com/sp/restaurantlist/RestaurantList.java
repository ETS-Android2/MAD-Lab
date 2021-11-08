// Lab 1a
package com.sp.restaurantlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RestaurantList extends AppCompatActivity { // extends = son of
    private EditText restaurantName;
    private Button buttonSave;
    private EditText restaurantAddress;
    private EditText restaurantTel;
    private RadioGroup restaurantTypes;
    private static final String TAG = "Lab1a";

    @Override
    protected void onCreate(Bundle savedInstanceState) { //onCreate is used to start an activity
        super.onCreate(savedInstanceState);             //super is used to call the parent class constructor
        setContentView(R.layout.main);                  //setContentView is used to set the xml
        restaurantName = findViewById(R.id.restaurant_name);
        restaurantTel = findViewById(R.id.restaurant_tel);
        restaurantAddress = findViewById(R.id.restaurant_address);
        restaurantTypes = findViewById(R.id.restaurant_types);
        buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(onSave);

        Log.d(TAG, "OnCreate method called");
    }

    private View.OnClickListener onSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "OnClick method called - start");
            Log.i(TAG, "Save button clicked");
            // To read data from restaurantName EditText
            String nameStr = restaurantName.getText().toString();
            String telStr = restaurantTel.getText().toString();
            String addrStr = restaurantAddress.getText().toString();
            String restType = "";
            // To read selection of restarantTypes RadioGroup
            switch (restaurantTypes.getCheckedRadioButtonId()) {
                case R.id.chinese:
                    restType = "Chinese";
                    break;
                case R.id.western:
                    restType = "Western";
                    break;
                case R.id.indian:
                    restType = "Indian";
                    break;
                case R.id.indonesian:
                    restType = "Indonesian";
                    break;
                case R.id.korean:
                    restType = "Korean";
                    break;
                case R.id.japanese:
                    restType = "Japanese";
                    break;
                case R.id.thai:
                    restType = "Thai";
                    break;
            }
            Log.i(TAG, "Restaurant Name: " + nameStr);
            Log.i(TAG, "Restaurant Address: " + addrStr);
            Log.i(TAG, "Restaurant Telephone: " + telStr);
            Log.i(TAG, "Restaurant Type: " +restType);
            String combineStr = nameStr + "\n" + addrStr + "\n" + telStr + "\n" + restType;
            Toast.makeText(v.getContext(), combineStr, Toast.LENGTH_LONG).show();
            Log.d(TAG, "OnClick method - end");
        }
    };
}