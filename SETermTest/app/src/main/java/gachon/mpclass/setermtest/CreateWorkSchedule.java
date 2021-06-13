package gachon.mpclass.setermtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateWorkSchedule extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private BottomBar bottomBar;
    private FragmentTransaction transaction;
    SharedPreferences preferences, preferencesId, preferencesList;
    SharedPreferences.Editor editor, editorList;
    ArrayAdapter adapter_headcount;
    ArrayAdapter adapter_from_1, adapter_to_1;
    ArrayAdapter adapter_from_2, adapter_to_2;
    ArrayAdapter adapter_from_3, adapter_to_3;
    int headcount;
    int part1_from_hour, part1_to_hour, part2_from_hour,
            part2_to_hour, part3_from_hour, part3_to_hour;
    String part_info;
    SqliteManager sqm;

    static final String[] LIST_HEADCOUNT = {"1", "2", "3", "4"};

    // The value to put in the spinner to select the time zone.
    static final String[] LIST_HOUR = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
            "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};

    ImageButton btn_create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_work_schedule);

        preferences = getSharedPreferences("noticeInfo", MODE_PRIVATE);
        editor = preferences.edit();

        // headcount
        Spinner spinner = findViewById(R.id.spinner);
        adapter_headcount = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, LIST_HEADCOUNT);
        adapter_headcount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter_headcount);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                headcount = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "None", Toast.LENGTH_LONG).show();
            }
        });

        // part1
        Spinner fromSpinner1 = findViewById(R.id.fromSpinner1);
        Spinner toSpinner1 = findViewById(R.id.toSpinner1);
        // A value is assigned to each adapter.
        adapter_from_1 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, LIST_HOUR);
        adapter_from_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner1.setAdapter(adapter_from_1);
        fromSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                part1_from_hour = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "None", Toast.LENGTH_LONG).show();
            }
        });

        adapter_to_1 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, LIST_HOUR);
        adapter_to_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpinner1.setAdapter(adapter_to_1);
        toSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                part1_to_hour = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "None", Toast.LENGTH_LONG).show();
            }
        });

        // part2
        Spinner fromSpinner2 = findViewById(R.id.fromSpinner2);
        Spinner toSpinner2 = findViewById(R.id.toSpinner2);

        adapter_from_2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, LIST_HOUR);
        adapter_from_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner2.setAdapter(adapter_from_2);
        fromSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                part2_from_hour = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "None", Toast.LENGTH_LONG).show();
            }
        });

        adapter_to_2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, LIST_HOUR);
        adapter_to_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpinner2.setAdapter(adapter_to_2);
        toSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                part2_to_hour = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "None", Toast.LENGTH_LONG).show();
            }
        });

        // part3
        Spinner fromSpinner3 = findViewById(R.id.fromSpinner3);
        Spinner toSpinner3 = findViewById(R.id.toSpinner3);

        adapter_from_3 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, LIST_HOUR);
        adapter_from_3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner3.setAdapter(adapter_from_3);
        fromSpinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                part3_from_hour = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "None", Toast.LENGTH_LONG).show();
            }
        });

        adapter_to_3 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, LIST_HOUR);
        adapter_to_3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpinner3.setAdapter(adapter_to_3);
        toSpinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                part3_to_hour = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "None", Toast.LENGTH_LONG).show();
            }
        });

        //By sending the value to the schedule algorithm, the final schedule can be created through the algorithm.
        btn_create = findViewById(R.id.btn_create);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Store values ​​through sharedPreferences. This goes to the class that creates the schedule.
                part_info = "\n\nPart 1   " + part1_from_hour + ":00 - " + part1_to_hour + ":00" +
                        "\nPart 2   " + part2_from_hour + ":00 - " + part2_to_hour + ":00" +
                        "\nPart 3   " + part3_from_hour + ":00 - " + part3_to_hour + ":00";

                editor.putString("partInfo", part_info);
                editor.commit();

                preferences = getSharedPreferences("id", MODE_PRIVATE);

                preferencesList = getSharedPreferences("list", MODE_PRIVATE);
                editorList = preferencesList.edit();

                editorList.putInt("part1_from_hour", part1_from_hour);
                editorList.putInt("part1_to_hour", part1_to_hour);
                editorList.putInt("part2_from_hour", part2_from_hour);
                editorList.putInt("part2_to_hour", part2_to_hour);
                editorList.putInt("part3_from_hour", part3_from_hour);
                editorList.putInt("part3_to_hour", part3_to_hour);

                editorList.commit();

                Intent intent = new Intent(getApplicationContext(), CreatedSchedule.class);
                startActivity(intent);
            }
        });

        fragmentManager = getSupportFragmentManager();
        bottomBar = new BottomBar();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, bottomBar).commitAllowingStateLoss();
    }

}