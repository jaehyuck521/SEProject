package gachon.mpclass.setermtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

public class Group extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private BottomBar bottomBar;
    private FragmentTransaction transaction;
    FirebaseDatabase database;
    SharedPreferences preferences;

    ImageButton btn_create_group;
    ImageButton btn_check;
    TextView text_groupname;
    TextView text_leader;
    TextView text_headcount;
    LinearLayout groupLayout;
    Datadto dt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        database = FirebaseDatabase.getInstance();
        SqliteManager sqm = new SqliteManager(getApplicationContext(), "kang.db");

        //layout 안보이게
        groupLayout = (LinearLayout) findViewById(R.id.groupLayout);
        groupLayout.setVisibility(View.INVISIBLE);

        // id값으로 객체 구별
        preferences = getSharedPreferences("id", MODE_PRIVATE);
        dt = new Datadto();
        dt = sqm.getCurrentUser(preferences.getString("id", "null"));

        // 이미 속한 그룹이 있다면 보이게
        if(dt.organ!="")
            groupLayout.setVisibility(View.VISIBLE);

        // dt에서 가져온 값들 UI에 표현
        text_groupname = findViewById(R.id.text_groupname);
        text_leader = findViewById(R.id.text_leader);
        text_headcount = findViewById(R.id.text_headcount);

        text_groupname.setText(String.valueOf(dt.organ));
        text_leader.setText(String.valueOf(dt.groupLeadname));
        text_headcount.setText(String.valueOf(dt.organnum));

        database.getReference().child("idlist").addValueEventListener(new ValueEventListener() {//서버에서 계속 데이터를 읽어들인다.
            //서버에서 읽어와서 로컬 저장소에 저장.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sqm.delete();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Datadto dt = dataSnapshot.getValue(Datadto.class);
                    sqm.insert(dt);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        Datadto curuser = new Datadto();
        //curuser=sqm.getCurrentUser(id); id값을 이용해 자신의 정보를 가져온다
        //curuser.organ, curuser.groupLeadname, curuser.organnum
        //그룹의 정보들을 가져온다. (UI에 표시 필요)
        btn_create_group = findViewById(R.id.btn_create_group);
        btn_check = findViewById(R.id.btn_check);
        btn_create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////////////////////////////////////

                //Intent intent = new Intent(getApplicationContext(), CreateGroup.class);
                //startActivity(intent);
                startActivityForResult(new Intent(Group.this, CreateGroup.class), 0);

            }

        });


        //groupLayout.setVisibility(View.VISIBLE);
        ///////////////////////////
        //intent
//        Intent intent2 = getIntent();
//        if(intent2 != null){
//            Bundle bun = new Bundle();
//            bun = intent2.getExtras();
//
//            String get_true = bun.getString("true");
//
//            if(get_true=="1")
//                groupLayout.setVisibility(View.VISIBLE);
//
//
//        }


        ///////////////////////////

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GroupPage.class);
                startActivity(intent);
            }
        });

        fragmentManager = getSupportFragmentManager();
        bottomBar = new BottomBar();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, bottomBar).commitAllowingStateLoss();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //Toast.makeText(MainActivity.this, "result ok!", Toast.LENGTH_SHORT).show();
            }
//                else{
//                    Toast.makeText(MainActivity.this, "result cancle!", Toast.LENGTH_SHORT).show();
//                }
        }
    }
}