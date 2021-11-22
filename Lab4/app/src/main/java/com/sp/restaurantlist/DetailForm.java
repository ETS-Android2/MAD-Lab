package com.sp.restaurantlist;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class DetailForm extends AppCompatActivity {
    private EditText restaurantName;
    private EditText restaurantAddress;
    private EditText restaurantTel;
    private RadioGroup restaurantTypes;
    private Button buttonSave;

    private RestaurantHelper helper = null;
    private String restaurantID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_form);
        restaurantName = findViewById(R.id.restaurant_name);
        restaurantAddress = findViewById(R.id.restaurant_address);
        restaurantTel = findViewById(R.id.restaurant_tel);
        restaurantTypes = findViewById(R.id.restaurant_types);

        buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(onSave);
        helper = new RestaurantHelper(this);

        restaurantID = getIntent().getStringExtra("ID");
        if (restaurantID != null) {
            load();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }

    private void load() {
        Cursor c = helper.getById(restaurantID);
        c.moveToFirst();
        restaurantName.setText(helper.getRestaurantName(c));
        restaurantAddress.setText(helper.getRestaurantAddress(c));
        restaurantTel.setText(helper.getRestaurantTel(c));

        if (helper.getRestaurantType(c).equals("Chinese")) {
            restaurantTypes.check(R.id.chinese);
        } else if (helper.getRestaurantType(c).equals("Western")) {
           restaurantTypes.check(R.id.western);
        } else if (helper.getRestaurantType(c).equals("Indian")) {
            restaurantTypes.check(R.id.indian);
        } else if (helper.getRestaurantType(c).equals("Indonesian")) {
            restaurantTypes.check(R.id.indonesian);
        } else if (helper.getRestaurantType(c).equals("Korean")) {
            restaurantTypes.check(R.id.korean);
        } else if (helper.getRestaurantType(c).equals("Japanese")) {
            restaurantTypes.check(R.id.japanese);
        } else {
            restaurantTypes.check(R.id.thai);
        }
    }

    View.OnClickListener onSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // To read date from EditText
            String nameStr = restaurantName.getText().toString();
            String addrStr = restaurantAddress.getText().toString();
            String telStr = restaurantTel.getText().toString();
            String restType = "";
            // To read selection of restaurantTypes RadioGroup
            switch (restaurantTypes.getCheckedRadioButtonId()){
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

            if (restaurantID == null) {
                helper.insert(nameStr, addrStr, telStr, restType);
            } else {
                helper.update(restaurantID, nameStr, addrStr, telStr, restType);
            }

            //To close current Activity class and exit
            finish();
        }
    };
}