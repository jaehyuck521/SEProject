package gachon.mpclass.setermtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class GroupManagement extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private BottomBar bottomBar;
    private FragmentTransaction transaction;

    ImageButton btn_changeNotice;
    ImageButton btn_disbandGroup;
    ImageButton btn_createWorkSchedule;
    ImageButton btn_inviteMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_management);

        // notice board
        btn_changeNotice = findViewById(R.id.btn_changeNotice);
        btn_changeNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Notice.class);
                startActivity(intent);
            }
        });

        // group disbandment
        btn_disbandGroup = findViewById(R.id.btn_disbandGroup);
        btn_disbandGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DisbandGroupPopup.class);
                startActivity(intent);
            }
        });

        // member invitation
        btn_inviteMembers = findViewById(R.id.btn_inviteMembers);
        btn_inviteMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InviteMembers.class);
                startActivity(intent);
            }
        });

        // timetable change
        btn_createWorkSchedule = findViewById(R.id.btn_createWorkSchedule);
        btn_createWorkSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateWorkSchedule.class);
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
