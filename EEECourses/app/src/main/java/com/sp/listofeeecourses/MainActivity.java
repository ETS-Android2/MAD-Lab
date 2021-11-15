package com.sp.listofeeecourses;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Course> model = new ArrayList<Course>();
    private courseAdapter adapter = null;
    private ListView list;
    private static final String TAG = "Lab1c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        list = findViewById(R.id.courses); // problematic statement
        adapter = new courseAdapter();
        list.setAdapter(adapter);
        // Problematic statements:
        Course dase = new Course("Aerospace Electronics (S90)", "Highly recognised Aerospace Electronics diploma course to develop future-ready engineers for growing Singapore as a Smart Aviation Hub ");
        adapter.add(dase);
        Log.d(TAG, dase.getCourseID());
        Log.d(TAG, "onCreaete complete");
    }


    static class courseHolder {
        private TextView courseName = null;
        private TextView desc = null;
        private ImageView icon = null;
        courseHolder(View row) {
            courseName = row.findViewById(R.id.name);
            desc = row.findViewById(R.id.description);
            icon = row.findViewById(R.id.icon);
            Log.d(TAG, "defining stuff in holder");
        }
        void populateFrom(Course r) {
            courseName.setText(r.getName());
            desc.setText(r.getDesc());
            Log.d(TAG, "TextView assignment");
            if (r.getCourseID().equals("S90")) {
                icon.setImageResource(R.drawable.dase); //potentially problematic
            } else if (r.getCourseID().equals("S53")) {
                icon.setImageResource(R.drawable.dcpe);
            } else if (r.getCourseID().equals("S99")) {
                icon.setImageResource(R.drawable.deee);
            } else {
                icon.setImageResource(R.drawable.deb);
            }
        }
    }

    class courseAdapter extends ArrayAdapter<Course> {
        courseAdapter() { super(MainActivity.this,R.layout.row, model); } // super ==> parent constructor

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d(TAG, "adapter start");
            View row = convertView;
            courseHolder holder;
            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();
                /* The LayoutInflater class is used to instantiate the contents of layout XML files into their corresponding View objects.
                In other words, it takes an XML file as input and builds the View objects from it.      */
                row = inflater.inflate(R.layout.row, parent, false);
                holder = new courseHolder(row);
                row.setTag(holder);
                Log.d(TAG, "row created");
            } else {
                holder = (courseHolder) row.getTag(); //Narrowing Casting (Explicit) –
                Log.d(TAG, "already got row");
            }
            holder.populateFrom(model.get(position));
            Log.d(TAG, "returned");
            return (row);
        }
    }
}