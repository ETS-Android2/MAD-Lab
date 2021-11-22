package com.sp.restaurantlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class RestaurantList extends AppCompatActivity { // extends = son of
    private EditText restaurantName;
    private Button buttonSave;
    private EditText restaurantAddress;
    private EditText restaurantTel;
    private RadioGroup restaurantTypes;

    private List<Restaurant> model = new ArrayList<Restaurant>();
    private ArrayAdapter<Restaurant> adapter = null;
    private ListView list;

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

        list = findViewById(R.id.restaurants);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,model);
        list.setAdapter(adapter);
    }

    private View.OnClickListener onSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // To read data from restaurantName EditText
            String nameStr = restaurantName.getText().toString();  //convert the returned CharSequence into a String
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
            // Create Restaurant Data model
            Restaurant restaurant = new Restaurant();
            // Update the Data model
            restaurant.setName(nameStr);
            restaurant.setAddress(addrStr);
            restaurant.setTelephone(telStr);
            restaurant.setRestaurantType(restType);
            // Pass the record to ArrayAdapter.
            // It will update the ListArray and the ListView
            adapter.add(restaurant);
        }
    };
}