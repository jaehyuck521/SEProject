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

public class Calendar extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private BottomBar bottomBar;
    private FragmentTransaction transaction;
    FirebaseDatabase database;
    SqliteManager sqm;
    SharedPreferences preferences;
    String[] schedule;
    TextView textPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        sqm = new SqliteManager(getApplicationContext(), "kang.db");
        database = FirebaseDatabase.getInstance();
        database.getReference().child("Groupschedule").addValueEventListener(new ValueEventListener() {//서버에서 계속 데이터를 읽어들인다.
            //서버에서 읽어와서 로컬 저장소에 저장.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sqm.deleteGroup();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Gdatadto dt = dataSnapshot.getValue(Gdatadto.class);
                    sqm.insertGroup(dt);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // id값으로 객체 구별
        preferences = getSharedPreferences("id", MODE_PRIVATE);

        sqm = new SqliteManager(getApplicationContext(), "kang.db");
        Datadto dt = new Datadto();
        dt = sqm.getCurrentUser(preferences.getString("id", "null"));
        Datadao da = new Datadao();

        String groupName = dt.getOrgan();
        String str;

        if(sqm.getSchedule(groupName) != null) {
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
            Log.i("db1", "dt getname - " + dt.getName());
            Log.i("db1", "schedule[2] - " + schedule[2]);

            int cnt = 0; // for caculate pay

            String name = dt.getName() + " ";
            for (int i = 0; i < 168; i++) {
                if (name.equals(schedule[i])) {
                    TextView textview = (TextView) findViewById(id[i]);
                    textview.setText(dt.getOrgan());
                    textview.setBackgroundColor(getResources().getColor(R.color.table));
                    cnt += 1;
                }
            }

            // Caculate pay and set
            long pay = dt.salary * cnt;
            textPay = findViewById(R.id.textPay);
            textPay.setText(String.valueOf(pay));
        }

        // bottom bar
        fragmentManager = getSupportFragmentManager();
        bottomBar = new BottomBar();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, bottomBar).commitAllowingStateLoss();

    }
}