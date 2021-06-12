package gachon.mpclass.setermtest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class CreatedSchedule extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private BottomBar bottomBar;
    private FragmentTransaction transaction;
    FirebaseDatabase database;

    int part1_from_hour, part1_to_hour, part2_from_hour, part2_to_hour, part3_from_hour, part3_to_hour;
    SharedPreferences preferences, preferencesList;
    SqliteManager sqm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_schedule);

        /* Receive Data */
        preferences = getSharedPreferences("id", MODE_PRIVATE);
        preferencesList = getSharedPreferences("list", MODE_PRIVATE);

        part1_from_hour = preferencesList.getInt("part1_from_hour", 1);
        part1_to_hour = preferencesList.getInt("part1_to_hour", 3);
        part2_from_hour = preferencesList.getInt("part2_from_hour", 5);
        part2_to_hour = preferencesList.getInt("part2_to_hour", 7);
        part3_from_hour = preferencesList.getInt("part3_from_hour", 9);
        part3_to_hour = preferencesList.getInt("part3_to_hour", 11);
        //Call the sqlite manager for use of sqlite
        sqm = new SqliteManager(getApplicationContext(), "kang.db");
        Datadto dt = new Datadto();
        Gdatadto gdt = new Gdatadto();
        dt = sqm.getCurrentUser(preferences.getString("id", "null"));
        //get the current user information

        String list = except_and_push();

        Datadao da = new Datadao(); //for server access
        da.insertSchedule(dt.organ, list); //insert schedule string to server database
        Toast.makeText(getApplicationContext(), "The work schedule has been created.", Toast.LENGTH_LONG).show();

        database = FirebaseDatabase.getInstance();
        //Getting read from server and save the information to sqlite database
        database.getReference().child("Groupschedule").addValueEventListener(new ValueEventListener() {
            @Override
            //if server database changes, listener gets data from server
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SqliteManager sqm = new SqliteManager(getApplicationContext(), "kang.db");
                sqm.deleteGroup();//initialize sqlite database
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Gdatadto dt = dataSnapshot.getValue(Gdatadto.class);
                    sqm.insertGroup(dt);//insert data from server to sqlite , synchronization.
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        fragmentManager = getSupportFragmentManager();
        bottomBar = new BottomBar();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, bottomBar).commitAllowingStateLoss();
    }

    //schedule algorithm
    public String except_and_push() {

        /* Receive Data */
        sqm = new SqliteManager(getApplicationContext(), "kang.db");
        ArrayList<Datadto> list = new ArrayList<Datadto>();
        preferences = getSharedPreferences("id", MODE_PRIVATE);
        Datadto cur = new Datadto();
        cur = sqm.getCurrentUser(preferences.getString("id", "null"));
        Log.i("db1", cur.id);

        list = sqm.getList(cur.organ);
        Log.i("db1", list.get(1).id);

        String name[] = new String[list.size()];
        int prefer[] = new int[list.size()];
        int rest1[] = new int[list.size()];
        int rest2[] = new int[list.size()];
        int rest3[] = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            name[i] = list.get(i).name;
            prefer[i] = (int) list.get(i).prefer;
            rest1[i] = (int) list.get(i).rest1;
            rest2[i] = (int) list.get(i).rest2;
            rest3[i] = (int) list.get(i).rest3;
            Log.i("db1", "" + name[i]);
        }

//Get group data and make it an array

        int rest[][] = new int[7][list.size()];
        int hours = (part1_to_hour - part1_from_hour) + (part2_to_hour - part2_from_hour) + (part3_to_hour - part3_from_hour); // 하루 총 몇시간 근무인지
        Log.i("db1", "hours : " + hours);

        int[] time_split = new int[3];

        time_split[0] = part1_to_hour - part1_from_hour;
        time_split[1] = part2_to_hour - part2_from_hour;
        time_split[2] = part3_to_hour - part3_from_hour;
        Log.i("db1", "time_split : " + time_split[0]);


//rest array update (horizontal = day, vertical = group member)
// --> 1 = day off, default value 0 = workable day
         for (int i = 0; i < list.size(); i++) {
            switch (rest1[i]) {
                case 7:
                    break;
                case 0:
                    rest[0][i] = 1;
                    break;
                case 1:
                    rest[1][i] = 1;
                    break;
                case 2:
                    rest[2][i] = 1;
                    break;
                case 3:
                    rest[3][i] = 1;
                    break;
                case 4:
                    rest[4][i] = 1;
                    break;
                case 5:
                    rest[5][i] = 1;
                    break;
                case 6:
                    rest[6][i] = 1;
                    break;
            }
            switch (rest2[i]) {
                case 7:
                    break;
                case 0:
                    rest[0][i] = 1;
                    break;
                case 1:
                    rest[1][i] = 1;
                    break;
                case 2:
                    rest[2][i] = 1;
                    break;
                case 3:
                    rest[3][i] = 1;
                    break;
                case 4:
                    rest[4][i] = 1;
                    break;
                case 5:
                    rest[5][i] = 1;
                    break;
                case 6:
                    rest[6][i] = 1;
                    break;
            }
            switch (rest3[i]) {
                case 7:
                    break;
                case 0:
                    rest[0][i] = 1;
                    break;
                case 1:
                    rest[1][i] = 1;
                    break;
                case 2:
                    rest[2][i] = 1;
                    break;
                case 3:
                    rest[3][i] = 1;
                    break;
                case 4:
                    rest[4][i] = 1;
                    break;
                case 5:
                    rest[5][i] = 1;
                    break;
                case 6:
                    rest[6][i] = 1;
                    break;
            }

        }

//Create a list of available people for each day of the week
        ArrayList<String> sun = new ArrayList<>();
        ArrayList<String> mon = new ArrayList<>();
        ArrayList<String> tue = new ArrayList<>();
        ArrayList<String> wen = new ArrayList<>();
        ArrayList<String> thu = new ArrayList<>();
        ArrayList<String> fri = new ArrayList<>();
        ArrayList<String> sat = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (rest[0][i] == 0) {
                sun.add(name[i]);
            }
            if (rest[1][i] == 0) {
                mon.add(name[i]);
            }
            if (rest[2][i] == 0) {
                tue.add(name[i]);
            }
            if (rest[3][i] == 0) {
                wen.add(name[i]);
            }
            if (rest[4][i] == 0) {
                thu.add(name[i]);
            }
            if (rest[5][i] == 0) {
                fri.add(name[i]);
            }
            if (rest[6][i] == 0) {
                sat.add(name[i]);
            }
        }

//Pick 3 people
        sun = (ArrayList<String>) getRandomElement(sun);
        mon = (ArrayList<String>) getRandomElement(mon);
        tue = (ArrayList<String>) getRandomElement(tue);
        wen = (ArrayList<String>) getRandomElement(wen);
        thu = (ArrayList<String>) getRandomElement(thu);
        fri = (ArrayList<String>) getRandomElement(fri);
        sat = (ArrayList<String>) getRandomElement(sat);
        Log.i("db1", "" + sun);
        Log.i("db1", "" + mon);
        Log.i("db1", "" + tue);

//Replace Arraylist into array (3 workers per day)
        String[] sunPeople = sun.toArray(new String[sun.size()]);
        String[] monPeople = mon.toArray(new String[mon.size()]);
        String[] tuePeople = tue.toArray(new String[tue.size()]);
        String[] wenPeople = wen.toArray(new String[wen.size()]);
        String[] thuPeople = thu.toArray(new String[thu.size()]);
        String[] friPeople = fri.toArray(new String[fri.size()]);
        String[] satPeople = sat.toArray(new String[sat.size()]);

//prefer time - worker 0,1,2
        int[] sunPeoplePrefer = new int[3];
        int[] monPeoplePrefer = new int[3];
        int[] tuePeoplePrefer = new int[3];
        int[] wenPeoplePrefer = new int[3];
        int[] thuPeoplePrefer = new int[3];
        int[] friPeoplePrefer = new int[3];
        int[] satPeoplePrefer = new int[3];


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < list.size(); j++) {
                if (sunPeople[i] == list.get(j).name) {
                    sunPeoplePrefer[i] = (int) list.get(j).prefer;
                }
                if (monPeople[i] == list.get(j).name) {
                    monPeoplePrefer[i] = (int) list.get(j).prefer;
                }
                if (tuePeople[i] == list.get(j).name) {
                    tuePeoplePrefer[i] = (int) list.get(j).prefer;
                }
                if (wenPeople[i] == list.get(j).name) {
                    wenPeoplePrefer[i] = (int) list.get(j).prefer;
                }
                if (thuPeople[i] == list.get(j).name) {
                    thuPeoplePrefer[i] = (int) list.get(j).prefer;
                }
                if (friPeople[i] == list.get(j).name) {
                    friPeoplePrefer[i] = (int) list.get(j).prefer;
                }
                if (satPeople[i] == list.get(j).name) {
                    satPeoplePrefer[i] = (int) list.get(j).prefer;
                }


            }
        }

//replace parts according to prefertime (prioritize without considering all)
String temp = "";
        //Sunday
        switch (sunPeoplePrefer[0]) {
            case 0:
                if (sunPeoplePrefer[1] == 2) {
                    temp = sunPeople[1];
                    sunPeople[1] = sunPeople[2];
                    sunPeople[2] = temp;
                }
                break;
            case 1:
                if (sunPeoplePrefer[1] == 2) {
                    temp = sunPeople[0];
                    sunPeople[0] = sunPeople[1];
                    sunPeople[1] = temp;

                    temp = sunPeople[0];
                    sunPeople[0] = sunPeople[2];
                    sunPeople[2] = temp;
                } else {
                    temp = sunPeople[0];
                    sunPeople[0] = sunPeople[1];
                    sunPeople[1] = temp;
                }
                break;
            case 2:
                if (sunPeoplePrefer[1] == 0) {
                    temp = sunPeople[0];
                    sunPeople[0] = sunPeople[2];
                    sunPeople[2] = temp;

                    temp = sunPeople[1];
                    sunPeople[1] = sunPeople[0];
                    sunPeople[0] = temp;
                }
                break;
        }
        Log.i("db1", "prefer : " + sunPeoplePrefer[1]);

        temp = "";
        //Monday
        switch (monPeoplePrefer[0]) {
            case 0:
                if (monPeoplePrefer[1] == 2) {
                    temp = monPeople[1];
                    monPeople[1] = monPeople[2];
                    monPeople[2] = temp;
                }
                break;
            case 1:
                if (monPeoplePrefer[1] == 2) {
                    temp = monPeople[0];
                    monPeople[0] = monPeople[1];
                    monPeople[1] = temp;

                    temp = monPeople[0];
                    monPeople[0] = monPeople[2];
                    monPeople[2] = temp;
                } else {
                    temp = monPeople[0];
                    monPeople[0] = monPeople[1];
                    monPeople[1] = temp;
                }
                break;
            case 2:
                if (sunPeoplePrefer[1] == 0) {
                    temp = sunPeople[0];
                    sunPeople[0] = sunPeople[2];
                    sunPeople[2] = temp;

                    temp = sunPeople[1];
                    sunPeople[1] = sunPeople[0];
                    sunPeople[0] = temp;
                }
                break;
        }
        temp = "";
        //Tuesday
        switch (tuePeoplePrefer[0]) {
            case 0:
                if (tuePeoplePrefer[1] == 2) {
                    temp = tuePeople[1];
                    tuePeople[1] = tuePeople[2];
                    tuePeople[2] = temp;
                }
                break;
            case 1:
                if (tuePeoplePrefer[1] == 2) {
                    temp = tuePeople[0];
                    tuePeople[0] = tuePeople[1];
                    tuePeople[1] = temp;

                    temp = tuePeople[0];
                    tuePeople[0] = tuePeople[2];
                    tuePeople[2] = temp;
                } else {
                    temp = tuePeople[0];
                    tuePeople[0] = tuePeople[1];
                    tuePeople[1] = temp;
                }
                break;
            case 2:
                if (tuePeoplePrefer[1] == 0) {
                    temp = tuePeople[0];
                    tuePeople[0] = tuePeople[2];
                    tuePeople[2] = temp;

                    temp = tuePeople[1];
                    tuePeople[1] = tuePeople[0];
                    tuePeople[0] = temp;
                }
                break;
        }
        temp = "";
        //Wednesday
        switch (wenPeoplePrefer[0]) {
            case 0:
                if (wenPeoplePrefer[1] == 2) {
                    temp = wenPeople[1];
                    wenPeople[1] = wenPeople[2];
                    wenPeople[2] = temp;
                }
                break;
            case 1:
                if (wenPeoplePrefer[1] == 2) {
                    temp = wenPeople[0];
                    wenPeople[0] = wenPeople[1];
                    wenPeople[1] = temp;

                    temp = wenPeople[0];
                    wenPeople[0] = wenPeople[2];
                    wenPeople[2] = temp;
                } else {
                    temp = wenPeople[0];
                    wenPeople[0] = wenPeople[1];
                    wenPeople[1] = temp;
                }
                break;
            case 2:
                if (wenPeoplePrefer[1] == 0) {
                    temp = wenPeople[0];
                    wenPeople[0] = wenPeople[2];
                    wenPeople[2] = temp;

                    temp = wenPeople[1];
                    wenPeople[1] = wenPeople[0];
                    wenPeople[0] = temp;
                }
                break;
        }
        temp = "";
        //Thursday
        switch (thuPeoplePrefer[0]) {
            case 0:
                if (thuPeoplePrefer[1] == 2) {
                    temp = thuPeople[1];
                    thuPeople[1] = thuPeople[2];
                    thuPeople[2] = temp;
                }
                break;
            case 1:
                if (thuPeoplePrefer[1] == 2) {
                    temp = thuPeople[0];
                    thuPeople[0] = thuPeople[1];
                    thuPeople[1] = temp;

                    temp = thuPeople[0];
                    thuPeople[0] = thuPeople[2];
                    thuPeople[2] = temp;
                } else {
                    temp = thuPeople[0];
                    thuPeople[0] = thuPeople[1];
                    thuPeople[1] = temp;
                }
                break;
            case 2:
                if (thuPeoplePrefer[1] == 0) {
                    temp = thuPeople[0];
                    thuPeople[0] = thuPeople[2];
                    thuPeople[2] = temp;

                    temp = thuPeople[1];
                    thuPeople[1] = thuPeople[0];
                    thuPeople[0] = temp;
                }
                break;
        }
        temp = "";
        //Friday
        switch (friPeoplePrefer[0]) {
            case 0:
                if (friPeoplePrefer[1] == 2) {
                    temp = friPeople[1];
                    friPeople[1] = friPeople[2];
                    friPeople[2] = temp;
                }
                break;
            case 1:
                if (friPeoplePrefer[1] == 2) {
                    temp = friPeople[0];
                    friPeople[0] = friPeople[1];
                    friPeople[1] = temp;

                    temp = friPeople[0];
                    friPeople[0] = friPeople[2];
                    friPeople[2] = temp;
                } else {
                    temp = friPeople[0];
                    friPeople[0] = friPeople[1];
                    friPeople[1] = temp;
                }
                break;
            case 2:
                if (friPeoplePrefer[1] == 0) {
                    temp = friPeople[0];
                    friPeople[0] = friPeople[2];
                    friPeople[2] = temp;

                    temp = friPeople[1];
                    friPeople[1] = friPeople[0];
                    friPeople[0] = temp;
                }
                break;
        }
        temp = "";
        //Saturday
        switch (satPeoplePrefer[0]) {
            case 0:
                if (satPeoplePrefer[1] == 2) {
                    temp = satPeople[1];
                    satPeople[1] = satPeople[2];
                    satPeople[2] = temp;
                }
                break;
            case 1:
                if (satPeoplePrefer[1] == 2) {
                    temp = satPeople[0];
                    satPeople[0] = satPeople[1];
                    satPeople[1] = temp;

                    temp = satPeople[0];
                    satPeople[0] = satPeople[2];
                    satPeople[2] = temp;
                } else {
                    temp = satPeople[0];
                    satPeople[0] = satPeople[1];
                    satPeople[1] = temp;
                }
                break;
            case 2:
                if (satPeoplePrefer[1] == 0) {
                    temp = satPeople[0];
                    satPeople[0] = satPeople[2];
                    satPeople[2] = temp;

                    temp = satPeople[1];
                    satPeople[1] = satPeople[0];
                    satPeople[0] = temp;
                }
                break;
        }


//Save text view id to display by day
        String[] sunId = new String[hours];
        String[] monId = new String[hours];
        String[] tueId = new String[hours];
        String[] wenId = new String[hours];
        String[] thuId = new String[hours];
        String[] friId = new String[hours];
        String[] satId = new String[hours];

        String[] day = {"sun", "mon", "tue", "wen", "thu", "fri", "sat"};

        for (int i = 0; i < time_split[0]; i++) {
            sunId[i] = day[0] + String.valueOf(part1_from_hour + i);
            monId[i] = day[1] + String.valueOf(part1_from_hour + i);
            tueId[i] = day[2] + String.valueOf(part1_from_hour + i);
            wenId[i] = day[3] + String.valueOf(part1_from_hour + i);
            thuId[i] = day[4] + String.valueOf(part1_from_hour + i);
            friId[i] = day[5] + String.valueOf(part1_from_hour + i);
            satId[i] = day[6] + String.valueOf(part1_from_hour + i);
            Log.i("db1", sunId[0]);
        }

        for (int i = 0; i < time_split[1]; i++) {
            sunId[time_split[0] + i] = day[0] + String.valueOf(part2_from_hour + i);
            monId[time_split[0] + i] = day[1] + String.valueOf(part2_from_hour + i);
            tueId[time_split[0] + i] = day[2] + String.valueOf(part2_from_hour + i);
            wenId[time_split[0] + i] = day[3] + String.valueOf(part2_from_hour + i);
            thuId[time_split[0] + i] = day[4] + String.valueOf(part2_from_hour + i);
            friId[time_split[0] + i] = day[5] + String.valueOf(part2_from_hour + i);
            satId[time_split[0] + i] = day[6] + String.valueOf(part2_from_hour + i);
        }

        for (int i = 0; i < time_split[2]; i++) {
            sunId[time_split[0] + time_split[1] + i] = day[0] + String.valueOf(part3_from_hour + i);
            monId[time_split[0] + time_split[1] + i] = day[1] + String.valueOf(part3_from_hour + i);
            tueId[time_split[0] + time_split[1] + i] = day[2] + String.valueOf(part3_from_hour + i);
            wenId[time_split[0] + time_split[1] + i] = day[3] + String.valueOf(part3_from_hour + i);
            thuId[time_split[0] + time_split[1] + i] = day[4] + String.valueOf(part3_from_hour + i);
            friId[time_split[0] + time_split[1] + i] = day[5] + String.valueOf(part3_from_hour + i);
            satId[time_split[0] + time_split[1] + i] = day[6] + String.valueOf(part3_from_hour + i);

        }

        String type = "id";
        String pack = getPackageName();

        int[] viewId0 = new int[hours];
        int[] viewId1 = new int[hours];
        int[] viewId2 = new int[hours];
        int[] viewId3 = new int[hours];
        int[] viewId4 = new int[hours];
        int[] viewId5 = new int[hours];
        int[] viewId6 = new int[hours];

        for (int i = 0; i < hours; i++) {
            viewId0[i] = getResources().getIdentifier(sunId[i], type, pack);
            viewId1[i] = getResources().getIdentifier(monId[i], type, pack);
            viewId2[i] = getResources().getIdentifier(tueId[i], type, pack);
            viewId3[i] = getResources().getIdentifier(wenId[i], type, pack);
            viewId4[i] = getResources().getIdentifier(thuId[i], type, pack);
            viewId5[i] = getResources().getIdentifier(friId[i], type, pack);
            viewId6[i] = getResources().getIdentifier(satId[i], type, pack);
            Log.i("db1", " " + viewId0);
        }


        //PART 1
        for (int i = 0; i < time_split[0]; i++) {
            TextView textView0 = (TextView) findViewById(viewId0[i]);
            textView0.setText(sunPeople[0]);

            TextView textView1 = (TextView) findViewById(viewId1[i]);
            textView1.setText(monPeople[0]);

            TextView textView2 = (TextView) findViewById(viewId2[i]);
            textView2.setText(tuePeople[0]);

            TextView textView3 = (TextView) findViewById(viewId3[i]);
            textView3.setText(wenPeople[0]);

            TextView textView4 = (TextView) findViewById(viewId4[i]);
            textView4.setText(thuPeople[0]);

            TextView textView5 = (TextView) findViewById(viewId5[i]);
            textView5.setText(friPeople[0]);

            TextView textView6 = (TextView) findViewById(viewId6[i]);
            textView6.setText(satPeople[0]);
        }

        //PART 2
        for (int i = time_split[0]; i < time_split[0] + time_split[1]; i++) {
            TextView textView0 = (TextView) findViewById(viewId0[i]);
            textView0.setText(sunPeople[1]);

            TextView textView1 = (TextView) findViewById(viewId1[i]);
            textView1.setText(monPeople[1]);

            TextView textView2 = (TextView) findViewById(viewId2[i]);
            textView2.setText(tuePeople[1]);

            TextView textView3 = (TextView) findViewById(viewId3[i]);
            textView3.setText(wenPeople[1]);

            TextView textView4 = (TextView) findViewById(viewId4[i]);
            textView4.setText(thuPeople[1]);

            TextView textView5 = (TextView) findViewById(viewId5[i]);
            textView5.setText(friPeople[1]);

            TextView textView6 = (TextView) findViewById(viewId6[i]);
            textView6.setText(satPeople[1]);

        }
        //PART 3
        for (int i = time_split[1] + time_split[0]; i < hours; i++) {
            TextView textView0 = (TextView) findViewById(viewId0[i]);
            textView0.setText(sunPeople[2]);

            TextView textView1 = (TextView) findViewById(viewId1[i]);
            textView1.setText(monPeople[2]);

            TextView textView2 = (TextView) findViewById(viewId2[i]);
            textView2.setText(tuePeople[2]);

            TextView textView3 = (TextView) findViewById(viewId3[i]);
            textView3.setText(wenPeople[2]);

            TextView textView4 = (TextView) findViewById(viewId4[i]);
            textView4.setText(thuPeople[2]);

            TextView textView5 = (TextView) findViewById(viewId5[i]);
            textView5.setText(friPeople[2]);

            TextView textView6 = (TextView) findViewById(viewId6[i]);
            textView6.setText(satPeople[2]);
        }

        String[] sunid = new String[24];
        String[] monid = new String[24];
        String[] tueid = new String[24];
        String[] wenid = new String[24];
        String[] thuid = new String[24];
        String[] friid = new String[24];
        String[] satid = new String[24];

        int id[] = new int[168];

//        id[0] = getResources().getIdentifier(sun[i], type, pack);
        int k = 6;
        for (int i = 0; i < 24; i++) {
            if (k == 25) {
                k = 1;
            }
            sunid[i] = day[0] + String.valueOf(k);
            monid[i] = day[1] + String.valueOf(k);
            tueid[i] = day[2] + String.valueOf(k);
            wenid[i] = day[3] + String.valueOf(k);
            thuid[i] = day[4] + String.valueOf(k);
            friid[i] = day[5] + String.valueOf(k);
            satid[i] = day[6] + String.valueOf(k);
            k++;
        }
        Log.i("db1", "" + sunid[1]);


        int j = 0;
        for (int i = 0; i < 24; i++) {
            id[j] = getResources().getIdentifier(sunid[i], type, pack);
            j++;
            id[j] = getResources().getIdentifier(monid[i], type, pack);
            j++;
            id[j] = getResources().getIdentifier(tueid[i], type, pack);
            j++;
            id[j] = getResources().getIdentifier(wenid[i], type, pack);
            j++;
            id[j] = getResources().getIdentifier(thuid[i], type, pack);
            j++;
            id[j] = getResources().getIdentifier(friid[i], type, pack);
            j++;
            id[j] = getResources().getIdentifier(satid[i], type, pack);
            j++;
        }

        String schedule = "";
        for (int i = 0; i < 168; i++) {
            if (i == 0) {
                TextView textview = (TextView) findViewById(id[i]);
                if (textview.getText().toString().equals("")) {
                    schedule = " ";
                } else {
                    schedule += textview.getText().toString();
                }
            } else {
                TextView textview = (TextView) findViewById(id[i]);

                if (textview.getText().toString().equals("")) {
                    schedule += " ,";
                } else {
                    schedule += " ," + textview.getText().toString();
                }
            }
        }
        Log.i("db1", schedule);

        return schedule;

    }

    //Three workers at random - Pull from List
    public ArrayList<String> getRandomElement(ArrayList<String> list) {
        Random rand = new Random();
        // create a temporary list for storing
        // selected element
        ArrayList<String> newList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int randomIndex = rand.nextInt(list.size());
            newList.add(list.get(randomIndex));
            list.remove(randomIndex);
        }
        return newList;
    }


}