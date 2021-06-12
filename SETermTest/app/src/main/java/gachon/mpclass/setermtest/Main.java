package gachon.mpclass.setermtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main extends AppCompatActivity {

    ImageButton btn_sign_up;
    ImageButton btn_log_in;
    ImageButton btn_create_account;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SqliteManager sqm = new SqliteManager(this, "kang.db");
        database = FirebaseDatabase.getInstance();
        database.getReference().child("idlist").addValueEventListener(new ValueEventListener() {
            //Getting read from server and save the information to sqlite database
            @Override
            //if server database changes, listener gets data from server
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sqm.delete();//initialize sqlite database
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Datadto dt = dataSnapshot.getValue(Datadto.class);
                    sqm.insert(dt);
                    //insert data from server to sqlite , synchronization.
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        database = FirebaseDatabase.getInstance();
        database.getReference().child("Groupschedule").addValueEventListener(new ValueEventListener() {//서버에서 계속 데이터를 읽어들인다.
            //Getting read from server and save the information to sqlite database
            @Override
            //if server database changes, listener gets data from server
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sqm.deleteGroup();//initialize sqlite database
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Gdatadto dt = dataSnapshot.getValue(Gdatadto.class);
                    sqm.insertGroup(dt);
                    //insert data from server to sqlite , synchronization.
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        btn_sign_up = findViewById(R.id.btn_sign_up);
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });
        btn_log_in = findViewById(R.id.btn_log_in);
        btn_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogIn.class);
                startActivity(intent);
            }
        });
        btn_create_account = findViewById(R.id.btn_create_account);
        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });

    }
}