package gachon.mpclass.setermtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogIn extends AppCompatActivity {

    ImageButton btn_log_in, btn_create_account;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    EditText pid;
    EditText ppw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        preferences = getSharedPreferences("id", MODE_PRIVATE);
        editor = preferences.edit();
        pid=(EditText)findViewById(R.id.edit_id);
        ppw=(EditText)findViewById(R.id.editPassword);
        btn_log_in  = findViewById(R.id.btn_log_in);
        btn_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chid=pid.getText().toString();
                String chpw=ppw.getText().toString();
                Datadao doli=new Datadao();
                if(doli.login(getApplicationContext(),chid, chpw)) {
                    editor.putString("id", chid);
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), UserInfo.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid",Toast.LENGTH_LONG).show();                }
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