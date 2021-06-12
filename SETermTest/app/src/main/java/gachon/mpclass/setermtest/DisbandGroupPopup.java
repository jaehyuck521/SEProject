package gachon.mpclass.setermtest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisbandGroupPopup extends Activity {

    ImageButton btn_yes;
    ImageButton btn_no;
    FirebaseDatabase database;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_disband_group_popup);
        //Call the sqlite manager for use of sqlite
        SqliteManager sqm = new SqliteManager(this, "kang.db");
        database = FirebaseDatabase.getInstance();
        database.getReference().child("idlist").addValueEventListener(new ValueEventListener() {
            //Getting read from server and save the information to sqlite database
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {//if server database changes, listener gets data from server
                sqm.delete();//initialize sqlite database
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Datadto dt = dataSnapshot.getValue(Datadto.class);
                    sqm.insert(dt); //insert data from server to sqlite , synchronization.
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        btn_yes = findViewById(R.id.btn_yes);
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Datadao doli=new Datadao();
                preferences = getSharedPreferences("id", MODE_PRIVATE);
                Datadto dt=new Datadto();
                dt=sqm.getCurrentUser(preferences.getString("id", "null"));
                //get the current user information and disband group method execute
                //for using organization name
                //the group disband
                doli.disbandGroup(getApplicationContext(),dt.organ);
            }
        });
        btn_no = findViewById(R.id.btn_no);
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //확인 버튼 클릭
    public void mOnClose(View v) {
        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}