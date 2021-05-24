package gachon.mpclass.setermtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MyPage extends AppCompatActivity {

    SharedPreferences preferences;
    private FragmentManager fragmentManager;
    private BottomBar bottomBar;
    private FragmentTransaction transaction;
    ImageButton btn_log_out;
    ImageButton btn_delete_account;
    TextView setid;
    TextView setphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        preferences = getSharedPreferences("id", MODE_PRIVATE);
        setid=(TextView) findViewById(R.id.text_user_name);
        setphone=(TextView) findViewById(R.id.text_phone);
        SqliteManager sqm=new SqliteManager(getApplicationContext(),"kang.db");
        Datadto dt=new Datadto();
        dt=sqm.getCurrentUser(preferences.getString("id", "null")); //id값 넣어줘서 하면 된다
        setid.setText(dt.name);
        setphone.setText(dt.phonenum);
        btn_log_out = findViewById(R.id.btn_create_group);
        btn_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main.class);//처음화면으로 돌아간다
                startActivity(intent);
            }
        });
        btn_delete_account = findViewById(R.id.btn_delete_account);
        btn_delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DeleteAccountPopup.class);
                startActivity(intent);
            }
        });

        fragmentManager = getSupportFragmentManager();
        bottomBar = new BottomBar();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, bottomBar).commitAllowingStateLoss();

    }
}