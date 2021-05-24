package gachon.mpclass.setermtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InviteMembers extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private BottomBar bottomBar;
    private FragmentTransaction transaction;
    private List<String> id_list;
    private EditText editSearch;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_members);

        // id값으로 객체 구별
        SqliteManager sqm = new SqliteManager(getApplicationContext(), "kang.db");
        id_list = new ArrayList<String>();
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("idlist");
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    String id = String.valueOf(messageData.getKey());
                    id_list.add(id);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        editSearch = (EditText) findViewById(R.id.editText);

        Button btn_invite = findViewById(R.id.btn_invite);
        btn_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String invivted_id = String.valueOf(editSearch.getText());
                preferences = getSharedPreferences("id", MODE_PRIVATE);
                Datadto dt=new Datadto();
                dt=sqm.getCurrentUser(preferences.getString("id", "null"));
                // 초대 함수
                Datadao doli=new Datadao();
                doli.inviteGroupcomponent(getApplicationContext(),invivted_id,dt.organ,dt.organnum,dt.groupLeadname,dt.discription,dt.g_info,dt.groupNotice,dt.salary);
                // 초대가 완료되면
                editSearch.setText("");
                Toast.makeText(getApplicationContext(), "Member invitation is complete", Toast.LENGTH_LONG).show();
            }
        });
        fragmentManager = getSupportFragmentManager();
        bottomBar = new BottomBar();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, bottomBar).commitAllowingStateLoss();
    }
}