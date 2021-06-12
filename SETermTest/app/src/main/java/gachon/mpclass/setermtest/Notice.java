package gachon.mpclass.setermtest;

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

public class Notice extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private BottomBar bottomBar;
    private FragmentTransaction transaction;
    EditText workinginfo;
    EditText salary;
    EditText notice;
    ImageButton bts;
    FirebaseDatabase database;
    SharedPreferences preferences, preferences2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        database = FirebaseDatabase.getInstance();
        SqliteManager sqm = new SqliteManager(getApplicationContext(), "kang.db");


        preferences = getSharedPreferences("id", MODE_PRIVATE);

        // get part time info
        preferences2 = getSharedPreferences("noticeInfo", MODE_PRIVATE);

        database.getReference().child("idlist").addValueEventListener(new ValueEventListener() {
            //Getting read from server and save the information to sqlite database
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //if server database changes, listener gets data from server
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
        workinginfo=(EditText)findViewById(R.id.edit_working_info);
        salary=(EditText)findViewById(R.id.edit_salary_info);
        notice=(EditText)findViewById(R.id.edit_additional_notice);
        bts=(ImageButton)findViewById(R.id.imageButton);
        bts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Datadto dt=new Datadto();
                dt=sqm.getCurrentUser(preferences.getString("id", "null"));
                String info=workinginfo.getText().toString();
                info = info + preferences2.getString("partInfo", "");
                String sal=salary.getText().toString();
                String not=notice.getText().toString();
                int sala=Integer.parseInt(sal);
                Datadao doli=new Datadao();
                doli.setNotice(getApplicationContext(),dt.organ,info,sala, not); //저장. 조직 이름 넣으면, 해당 조직원들 모두, 데이터 업데이트
            }
        });
        fragmentManager = getSupportFragmentManager();
        bottomBar = new BottomBar();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, bottomBar).commitAllowingStateLoss();
    }
}
