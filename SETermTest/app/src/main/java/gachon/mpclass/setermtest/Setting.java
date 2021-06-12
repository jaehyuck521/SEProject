package gachon.mpclass.setermtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Setting extends AppCompatActivity implements TimePicker.OnTimeChangedListener{

    private FragmentManager fragmentManager;
    private BottomBar bottomBar;
    private FragmentTransaction transaction;
    static final String[] LIST_DAY = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"} ;
    static final String[] prefer_part = {"Part1", "Part2", "Part3"} ;

    Spinner spinner, spinner2, spinner3, spinner4;
    ArrayAdapter adapter;
    ImageButton btn_save;
    long pref=3;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        long [] rest= new long[3];

        spinner = (Spinner) findViewById(R.id.spinner_holiday1) ;
        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, LIST_DAY);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String holiday1 = LIST_DAY[position];
                switch(holiday1)
                {
                    case "Monday":
                        rest[0]=1;
                        break;
                    case "Tuesday":
                        rest[0]=2;
                        break;
                    case "Wednesday":
                        rest[0]=3;
                        break;
                    case "Thursday":
                        rest[0]=4;
                        break;
                    case "Friday":
                        rest[0]=5;
                        break;
                    case "Saturday":
                        rest[0]=6;
                        break;
                    case "Sunday":
                        rest[0]=0;
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Null", Toast.LENGTH_LONG).show();
            }
        });

        //choose holiday2
        spinner2 = (Spinner) findViewById(R.id.spinner_holiday2) ;
        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, LIST_DAY);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String holiday2 = LIST_DAY[position];
                switch(holiday2)
                {
                    case "Monday":
                        rest[1]=1;
                        break;
                    case "Tuesday":
                        rest[1]=2;
                        break;
                    case "Wednesday":
                        rest[1]=3;
                        break;
                    case "Thursday":
                        rest[1]=4;
                        break;
                    case "Friday":
                        rest[1]=5;
                        break;
                    case "Saturday":
                        rest[1]=6;
                        break;
                    case "Sunday":
                        rest[1]=0;
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Null", Toast.LENGTH_LONG).show();
            }
        });

        //choose holiday3
        spinner3 = (Spinner) findViewById(R.id.spinner_holiday3) ;
        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, LIST_DAY);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String holiday3 = LIST_DAY[position];
                Log.i("db1",holiday3);
                switch(holiday3)
                {
                    case "Monday":
                        rest[2]=1;
                        break;
                    case "Tuesday":
                        rest[2]=2;
                        break;
                    case "Wednesday":
                        rest[2]=3;
                        break;
                    case "Thursday":
                        rest[2]=4;
                        break;
                    case "Friday":
                        rest[2]=5;
                        break;
                    case "Saturday":
                        rest[2]=6;
                        break;
                    case "Sunday":
                        rest[2]=0;
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Null", Toast.LENGTH_LONG).show();
            }
        });
        ///////////////////////////////////////


        //choose part 1 2 3
        spinner4 = (Spinner) findViewById(R.id.spinner_part) ;
        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, prefer_part);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter);
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String prefer = prefer_part[position];
                switch(prefer)
                {
                    case "Part1":
                        pref=0;
                        break;
                    case "Part2":
                        pref=1;
                        break;
                    case "Part3":
                        pref=2;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Null", Toast.LENGTH_LONG).show();
            }
        });
        ///////////////////////////////////////

        // save
        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save the prefer time and rest day to server
                //call the sqlite manager for use of sqlite
                SqliteManager sqm=new SqliteManager(getApplicationContext(),"kang.db");
                preferences = getSharedPreferences("id", MODE_PRIVATE);
                Datadto cur = new Datadto();
                cur=sqm.getCurrentUser(preferences.getString("id", "null"));
                // get the current user information
                Datadao doli=new Datadao();
                Log.i("db1",cur.getId()+rest[0]+rest[1]+rest[2]);
                doli.updateSchedule(getApplicationContext(),cur.getId(),rest[0],rest[1],rest[2],pref);
                //set the each user's prefer time and rest days.

                Toast.makeText(getApplicationContext(), "Has been saved", Toast.LENGTH_LONG).show();
            }
        });


        ////////////////////////////////////////////////////////////////////////////
//        String[] listItems = {"one", "two", "three", "four", "five"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(Setting.this);
//        builder.setTitle("Choose items");
//
//        boolean[] checkedItems = new boolean[]{true, false, true, false, true}; //this will checked the items when user open the dialog
//        builder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//                Toast.makeText(Setting.this, "Position: " + which + " Value: " + listItems[which] + " State: " + (isChecked ? "checked" : "unchecked"), Toast.LENGTH_LONG).show();
//            }
//        });
//
//        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
        ////////////////////////////////////////////////////////////////////////////
//        spinner = (Spinner) findViewById(R.id.spinner) ;
//        adapter = new ArrayAdapter<String>(
//                this, android.R.layout.simple_spinner_item, LIST_DAY);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);

//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), LIST_DAY[position], Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                Toast.makeText(getApplicationContext(), "선택되지 않음", Toast.LENGTH_LONG).show();
//            }
//        });

//        fromTimePicker = (TimePicker) findViewById(R.id.fromTimePicker);
//        fromTimePicker.setOnTimeChangedListener(this);
//
//        toTimePicker = findViewById(R.id.toTimePicker);
//        toTimePicker.setOnTimeChangedListener(this);
//
//        btn_save = findViewById(R.id.btn_save);
//        btn_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // save
//                Toast.makeText(getApplicationContext(), "Has been saved", Toast.LENGTH_LONG).show();
//            }
//        });

        // 시간 체크하러 이동하기
        Button btn_check_time = findViewById(R.id.btn_check_time);
        btn_check_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Notice_member.class);
                startActivity(intent);
            }
        });

        fragmentManager = getSupportFragmentManager();
        bottomBar = new BottomBar();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, bottomBar).commitAllowingStateLoss();
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        // 시간 설정값이 바뀌었을 때
    }

}