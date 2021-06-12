package gachon.mpclass.setermtest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Schedule_member extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private BottomBar bottomBar;
    private FragmentTransaction transaction;
    FirebaseDatabase database;
    SharedPreferences preferences;
    String[] schedule;
    SqliteManager sqm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_member);
        sqm = new SqliteManager(getApplicationContext(), "kang.db");
        database = FirebaseDatabase.getInstance();
        database.getReference().child("Groupschedule").addValueEventListener(new ValueEventListener() {
            //Getting read from server and save the information to sqlite database
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { //if server database changes, listener gets data from server
                sqm.deleteGroup(); //initialize sqlite database
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Gdatadto dt = dataSnapshot.getValue(Gdatadto.class);
                    sqm.insertGroup(dt); //insert data from server to sqlite , synchronization.
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        preferences = getSharedPreferences("id", MODE_PRIVATE);
        sqm = new SqliteManager(getApplicationContext(), "kang.db");
        Datadto dt = new Datadto();
        dt = sqm.getCurrentUser(preferences.getString("id", "null"));
        Datadao da = new Datadao();

        String groupName = dt.getOrgan();
        String str;

        if(sqm.getSchedule(groupName)!=null) {
            str = sqm.getSchedule(groupName);
            schedule = str.split(",");
            Log.i("db1", "schedule 0:" + schedule[0]);
            Log.i("db1", "schedule 5:" + schedule[5]);

            String[] sunid = new String[24];
            String[] monid = new String[24];
            String[] tueid = new String[24];
            String[] wenid = new String[24];
            String[] thuid = new String[24];
            String[] friid = new String[24];
            String[] satid = new String[24];

            int id[] = new int[168];
            String[] day = {"sun", "mon", "tue", "wen", "thu", "fri", "sat"};

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

            String type = "id";
            String pack = getPackageName();

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

            Log.i("db1", "id 167 - " + id[167]);

            for (int i = 0; i < 168; i++) {
                TextView textview = (TextView) findViewById(id[i]);
                textview.setText(schedule[i]);
            }
        }

        fragmentManager = getSupportFragmentManager();
        bottomBar = new BottomBar();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, bottomBar).commitAllowingStateLoss();
    }
}
