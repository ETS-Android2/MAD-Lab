package com.sp.restaurantlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class RestaurantList extends AppCompatActivity { // extends = son of
    private EditText restaurantName;
    private RadioGroup restaurantTypes;
    private Button buttonSave;
    private EditText restaurantAddress;
    private EditText restaurantTel;

    private Cursor model = null;
    private RestaurantAdapter adapter = null;
    private ListView list;
    private RestaurantHelper helper = null;
    private TabHost host;
    private boolean showMenu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //onCreate is used to start an activity
        super.onCreate(savedInstanceState);             //super is used to call the **parent class constructor**
        setContentView(R.layout.main);                  //setContentView is used to set the xml

        restaurantName = findViewById(R.id.restaurant_name);
        restaurantTypes = findViewById(R.id.restaurant_types);
        buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(onSave);
        restaurantAddress = findViewById(R.id.restaurant_address);
        restaurantTel = findViewById(R.id.restaurant_tel);

        helper = new RestaurantHelper(this);
        list = findViewById(R.id.restaurants);
        model = helper.getAll();
        adapter = new RestaurantAdapter(this, model, 0);
        list.setAdapter(adapter);

        host = findViewById(R.id.tabHost);
        host.setup();

        // Tab 1
        TabHost.TabSpec spec = host.newTabSpec("List");
        spec.setContent(R.id.restaurants_tab);
        spec.setIndicator("List");
        host.addTab(spec);

        // Tab 2
        spec = host.newTabSpec("Details");
        spec.setContent(R.id.details_tab);
        spec.setIndicator("Details");
        host.addTab(spec);
        host.setCurrentTab(1);      // it will set second tab as default selected tab
        list.setOnItemClickListener(onListClick);

        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                invalidateOptionsMenu();
            }
        });
    }

    @Override
    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        invalidateOptionsMenu();
        super.onStart();
    }

    @Override
    public void invalidateOptionsMenu() {
        if (host.getCurrentTab() == 0) {
            showMenu = false;
        } else if (host.getCurrentTab() == 1) {
            showMenu = true;
        }
        super.invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        if (showMenu == true)
            return true;
        else
            return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
       switch (item.getItemId()) {
           case (R.id.about):
               Toast.makeText (this,"Restaurant List - version 1.0", Toast.LENGTH_LONG).show();
               break;
       }
       return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener onSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // To read data from restaurantName EditText
            String nameStr = restaurantName.getText().toString();
            String telStr = restaurantTel.getText().toString();
            if (telStr.length() < 8) {
                Toast error = Toast.makeText(getApplicationContext(), "Invalid telephone number", Toast.LENGTH_LONG);
                error.show();
                return;
            }
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
            // Insert record into SQLite table
            helper.insert(nameStr, addrStr, telStr, restType);

            model = helper.getAll();    // update Cursor after new record is added
            adapter.swapCursor(model);
            host.setCurrentTab(0);
        }
    };

    static class RestaurantHolder {
        private TextView restName = null;
        private TextView addr = null;
        private ImageView icon = null;
        RestaurantHolder(View row) {
            restName = row.findViewById(R.id.restName);
            addr = row.findViewById(R.id.restAddr);
            icon = row.findViewById(R.id.icon);
        }
        void populateFrom(Cursor c, RestaurantHelper helper) {
            restName.setText(helper.getRestaurantName(c));
            String temp = helper.getRestaurantAddress(c) + ", " + helper.getRestaurantTel(c);
            addr.setText(temp);

            if (helper.getRestaurantType(c).equals("Chinese")) {
                icon.setImageResource(R.drawable.ball_red);
            } else if (helper.getRestaurantType(c).equals("Western")) {
                icon.setImageResource(R.drawable.ball_yellow);
            } else {
                icon.setImageResource(R.drawable.ball_green);
            }
        }
    }

    class RestaurantAdapter extends CursorAdapter {
        RestaurantAdapter(Context context, Cursor cursor, int flags) {
            super(context, cursor, flags);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            RestaurantHolder holder = (RestaurantHolder) view.getTag();
            holder.populateFrom(cursor, helper);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);
            RestaurantHolder holder = new RestaurantHolder(row);
            row.setTag(holder);
            return (row);
        }
    }

    AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            model.moveToPosition(position);
            restaurantName.setText(helper.getRestaurantName(model));
            restaurantAddress.setText(helper.getRestaurantAddress(model));
            restaurantTel.setText(helper.getRestaurantTel(model));

            if (helper.getRestaurantType(model).equals("Chinese")) {
                restaurantTypes.check(R.id.chinese);
            } else if (helper.getRestaurantType(model).equals("Western")) {
                restaurantTypes.check(R.id.western);
            } else if (helper.getRestaurantType(model).equals("Indian")) {
                restaurantTypes.check(R.id.indian);
            } else if (helper.getRestaurantType(model).equals("Indonesian")) {
                restaurantTypes.check(R.id.indonesian);
            } else if (helper.getRestaurantTySQLiteDatabasepe(model).equals("Korean")) {
                restaurantTypes.check(R.id.korean);
            } else if (helper.getRestaurantType(model).equals("Japanese")) {
                restaurantTypes.check(R.id.japanese);
            } else {
                restaurantTypes.check(R.id.thai);
            }
            host.setCurrentTab(1);
        }
    };
}