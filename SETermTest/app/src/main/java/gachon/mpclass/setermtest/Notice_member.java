package gachon.mpclass.setermtest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Notice_member extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private BottomBar bottomBar;
    private FragmentTransaction transaction;
    TextView work;
    TextView notice;
    TextView sal;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_member);
        work=(TextView)findViewById(R.id.edit_working_info);
        notice=(TextView)findViewById(R.id.edit_additional_notice);
        sal=(TextView) findViewById(R.id.edit_salary_info);

        // id값으로 객체 구별
        SqliteManager sqm = new SqliteManager(getApplicationContext(), "kang.db");
        preferences = getSharedPreferences("id", MODE_PRIVATE);
        Datadto cur = new Datadto();
        cur=sqm.getCurrentUser(preferences.getString("id", "null"));

        work.setText(cur.g_info);
        sal.setText(String.valueOf(cur.salary));
        notice.setText(cur.groupNotice);

        fragmentManager = getSupportFragmentManager();
        bottomBar = new BottomBar();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, bottomBar).commitAllowingStateLoss();
    }
}