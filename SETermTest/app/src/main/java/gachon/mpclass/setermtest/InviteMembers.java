package gachon.mpclass.setermtest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

        //call the sqlite manager for use of sqlite

        SqliteManager sqm = new SqliteManager(getApplicationContext(), "kang.db");
        id_list = new ArrayList<String>();
        mDatabase = FirebaseDatabase.getInstance();
        //get the firebase data from idlist
        mReference = mDatabase.getReference().child("idlist");
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //if firebase database changes, it reads the database

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    String id = String.valueOf(messageData.getKey());
                    id_list.add(id); //get the all idlist id data
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
                Datadto dt=new Datadto(); //for the current user object
                dt=sqm.getCurrentUser(preferences.getString("id", "null"));
                //declare the object for server access
                Datadao doli=new Datadao();
                //invite the member method
                //invite success
                if(dt.leader==1) { //if the group leader,
                    if (doli.inviteGroupcomponent(getApplicationContext(), invivted_id, dt.organ, dt.organnum, dt.groupLeadname, dt.discription, dt.g_info, dt.groupNotice, dt.salary)) {
                        editSearch.setText("");
                        //the member invitation complete toast message
                        Toast.makeText(getApplicationContext(), "Member invitation is complete", Toast.LENGTH_LONG).show();
                    }
                    //invite fails
                    else {
                        Toast.makeText(getApplicationContext(), "Member invitation fail, do it again", Toast.LENGTH_LONG).show();
                    }
                }
                else //if the user isn't group leader
                {
                    Toast.makeText(getApplicationContext(), "You are not group leader", Toast.LENGTH_LONG).show();
                }
            }
        });
        // bottom bar work
        fragmentManager = getSupportFragmentManager();
        bottomBar = new BottomBar();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, bottomBar).commitAllowingStateLoss();
    }
}