package gachon.mpclass.setermtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class GroupPage extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private BottomBar bottomBar;
    private FragmentTransaction transaction;

    ImageButton btn_managementGroup;
    ImageButton btn_viewNotice;
    ImageButton btn_viewWorkSchedule;
    SharedPreferences preferences;

    TextView text_name1;
    TextView text_id1;
    TextView text_phone1;
    TextView text_name2;
    TextView text_id2;
    TextView text_phone2;
    TextView text_name3;
    TextView text_id3;
    TextView text_phone3;
    TextView text_name4;
    TextView text_id4;
    TextView text_phone4;
    TextView group_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);

        text_name1 = findViewById(R.id.text_name);
        text_id1 = findViewById(R.id.text_id);
        text_phone1 = findViewById(R.id.text_phone);

        text_name2 = findViewById(R.id.text_name2);
        text_id2 = findViewById(R.id.text_id2);
        text_phone2 = findViewById(R.id.text_phone2);

        text_name3 = findViewById(R.id.text_name3);
        text_id3 = findViewById(R.id.text_id3);
        text_phone3 = findViewById(R.id.text_phone3);

        text_name4 = findViewById(R.id.text_name4);
        text_id4 = findViewById(R.id.text_id4);
        text_phone4 = findViewById(R.id.text_phone4);
        //call the sqlite manager for use of sqlite
        SqliteManager sqm = new SqliteManager(getApplicationContext(), "kang.db");
        ArrayList<Datadto> list=new ArrayList<Datadto>();
        preferences=getSharedPreferences("id",MODE_PRIVATE);
        Datadto dt=new Datadto();
        dt=sqm.getCurrentUser(preferences.getString("id","null"));
        //get the current user information
        //get the organization name
        list=sqm.getList(dt.organ); //get the group member list from sqlite
        Log.i("db1",dt.organ);

        //set group name
        String groupName = dt.getOrgan();
        group_name = findViewById(R.id.group_name);
        group_name.setText(groupName);

        String [] name=new String[list.size()];
        String [] id=new String[list.size()];
        String [] phone=new String[list.size()];
        for(int i=0;i<list.size();i++) {
            // It can be expressed by putting it in an array.
            // The bottom is an image view. For now, hold
            name[i] = list.get(i).name;
            id[i] = list.get(i).id;
            phone[i] = list.get(i).phonenum;
            Log.i("db1",name[i]);
        }

        if (list.size() == 0) {
            text_name1.setText(" ");
            text_id1.setText(" ");
            text_phone1.setText(" ");
            //
            text_name2.setText(" ");
            text_id2.setText(" ");
            text_phone2.setText(" ");
            //
            text_name3.setText(" ");
            text_id3.setText(" ");
            text_phone3.setText(" ");
            //
            text_name4.setText(" ");
            text_id4.setText(" ");
            text_phone4.setText(" ");

        }
        if (list.size() == 1) {
            text_name1.setText(String.valueOf(name[0]));
            text_id1.setText(String.valueOf(id[0]));
            text_phone1.setText(String.valueOf(phone[0]));
            //
            text_name2.setText(" ");
            text_id2.setText(" ");
            text_phone2.setText(" ");
            //
            text_name3.setText(" ");
            text_id3.setText(" ");
            text_phone3.setText(" ");
            //
            text_name4.setText(" ");
            text_id4.setText(" ");
            text_phone4.setText(" ");
        }
        if (list.size() == 2) {
            text_name1.setText(String.valueOf(name[0]));
            text_id1.setText(String.valueOf(id[0]));
            text_phone1.setText(String.valueOf(phone[0]));
            //
            text_name2.setText(String.valueOf(name[1]));
            text_id2.setText(String.valueOf(id[1]));
            text_phone2.setText(String.valueOf(phone[1]));
            //
            text_name3.setText(" ");
            text_id3.setText(" ");
            text_phone3.setText(" ");
            //
            text_name4.setText(" ");
            text_id4.setText(" ");
            text_phone4.setText(" ");
        }
        if (list.size() == 3) {
            text_name1.setText(String.valueOf(name[0]));
            text_id1.setText(String.valueOf(id[0]));
            text_phone1.setText(String.valueOf(phone[0]));
            //
            text_name2.setText(String.valueOf(name[1]));
            text_id2.setText(String.valueOf(id[1]));
            text_phone2.setText(String.valueOf(phone[1]));
            //
            text_name3.setText(String.valueOf(name[2]));
            text_id3.setText(String.valueOf(id[2]));
            text_phone3.setText(String.valueOf(phone[2]));
            //
            text_name4.setText(" ");
            text_id4.setText(" ");
            text_phone4.setText(" ");
        }
        if (list.size() == 4) {
            text_name1.setText(String.valueOf(name[0]));
            text_id1.setText(String.valueOf(id[0]));
            text_phone1.setText(String.valueOf(phone[0]));
            //
            text_name2.setText(String.valueOf(name[1]));
            text_id2.setText(String.valueOf(id[1]));
            text_phone2.setText(String.valueOf(phone[1]));
            //
            text_name3.setText(String.valueOf(name[2]));
            text_id3.setText(String.valueOf(id[2]));
            text_phone3.setText(String.valueOf(phone[2]));
            //
            text_name4.setText(String.valueOf(name[3]));
            text_id4.setText(String.valueOf(id[3]));
            text_phone4.setText(String.valueOf(phone[3]));
        }

//        //Values ​​expressed in UI
//        text_name1 = findViewById(R.id.text_name);
//        text_id1 = findViewById(R.id.text_id);
//        text_phone1 = findViewById(R.id.text_phone);
//        Log.i("db1",name[0]);
//
//        text_name1.setText(name[0]);
//        text_id1.setText(id[0]);
//        text_phone1.setText(phone[0]);
//
//        text_name2 = findViewById(R.id.text_name2);
//        text_id2 = findViewById(R.id.text_id2);
//        text_phone2 = findViewById(R.id.text_phone2);
//
//        text_name2.setText(String.valueOf(name[1]));
//        text_id2.setText(String.valueOf(id[1]));
//        text_phone2.setText(String.valueOf(phone[1]));
//
//        text_name3 = findViewById(R.id.text_name3);
//        text_id3 = findViewById(R.id.text_id3);
//        text_phone3 = findViewById(R.id.text_phone3);
//
//        text_name3.setText(String.valueOf(name[2]));
//        text_id3.setText(String.valueOf(id[2]));
//        text_phone3.setText(String.valueOf(phone[2]));

        ///////////////////////////////////////

        btn_managementGroup = findViewById(R.id.btn_managementGroup);
        btn_managementGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GroupManagement.class);
                startActivity(intent);
            }
        });

        btn_viewNotice = findViewById(R.id.btn_viewNotice);
        btn_viewNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Notice_member.class);
                startActivity(intent);
            }
        });

        // View all members and my schedule
        btn_viewWorkSchedule = findViewById(R.id.btn_viewWorkSchedule);
        btn_viewWorkSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Schedule_member.class);
                startActivity(intent);

            }
        });

        //bottom bar work
        fragmentManager = getSupportFragmentManager();
        bottomBar = new BottomBar();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, bottomBar).commitAllowingStateLoss();
    }

}