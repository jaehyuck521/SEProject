package gachon.mpclass.setermtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateGroup extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private BottomBar bottomBar;
    private FragmentTransaction transaction;
    ImageButton btn_create_group;
    EditText gname;
    EditText hc;
    EditText discript;
    FirebaseDatabase database;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        gname=(EditText)findViewById(R.id.edit_group_name);
        hc=(EditText)findViewById(R.id.edit_headcount);
        discript=(EditText)findViewById(R.id.edit_description);
        btn_create_group = findViewById(R.id.btn_create_group);
        SqliteManager sqm = new SqliteManager(this, "kang.db");

        // id값으로 객체 구별
        preferences = getSharedPreferences("id", MODE_PRIVATE);
        Datadto dt=new Datadto();
        dt=sqm.getCurrentUser(preferences.getString("id", "null"));

        database = FirebaseDatabase.getInstance();
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
        btn_create_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getSharedPreferences("id", MODE_PRIVATE);
                Datadto dt = new Datadto();
                dt=sqm.getCurrentUser(preferences.getString("id", "null"));
                String gr=gname.getText().toString();
                String hcc=hc.getText().toString();
                String dis=discript.getText().toString();
                int hci=Integer.parseInt(hcc);
                Datadao doli=new Datadao();
                doli.setLeader(getApplicationContext(),dt.id, gr, hci, dis); //id 넣어줘야 한다
                //////
                // make bundle
//                String a = "1";
//                Bundle bun = new Bundle();
//                bun.putString("true", a);

                Intent intent1 = new Intent(getApplicationContext(), Group.class);
                //intent1.putExtras(bun);
                //startActivityForResult(intent1,1);
                setResult(RESULT_OK);
                finish();

            }
        });
        fragmentManager = getSupportFragmentManager();
        bottomBar = new BottomBar();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, bottomBar).commitAllowingStateLoss();
    }
}