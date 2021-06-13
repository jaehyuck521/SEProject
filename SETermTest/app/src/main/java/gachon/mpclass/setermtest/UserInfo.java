package gachon.mpclass.setermtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class UserInfo extends AppCompatActivity {

    ImageButton btn_student;
    ImageButton btn_worker;
    EditText wname;
    Datadto cur;
    SharedPreferences preferences;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        //call the manager class for using sqlite
        SqliteManager sqm = new SqliteManager(getApplicationContext(), "kang.db");
        preferences = getSharedPreferences("id", MODE_PRIVATE);
        cur = sqm.getCurrentUser(preferences.getString("id", "null")); //get the current user info
        id = cur.getId();

        wname = (EditText) findViewById(R.id.edit_user_name);

        // If the user is a student, assign a value to the DB
        btn_student = findViewById(R.id.btn_student);
        btn_student.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                String sname = wname.getText().toString();
                Datadao doli = new Datadao();
                doli.setStudent(getApplicationContext(), cur.getId(), sname);
                Intent intent = new Intent(getApplicationContext(), Calendar.class);
                startActivity(intent);
            }
        });

        // If the user is a worker, assign a value to the db
        btn_worker = findViewById(R.id.btn_worker);
        btn_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sname = wname.getText().toString();
                Datadao doli = new Datadao();
                doli.setWorker(getApplicationContext(), cur.getId(), sname);
                Intent intent = new Intent(getApplicationContext(), Calendar.class);
                startActivity(intent);
            }
        });
    }
}