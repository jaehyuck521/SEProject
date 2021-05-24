package gachon.mpclass.setermtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    ImageButton btn_sign_up;
    EditText cid;
    EditText cpw;
    EditText cphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        cid=(EditText)findViewById(R.id.edit_id);
        cpw=(EditText)findViewById(R.id.editPassword);
        cphone=(EditText)findViewById(R.id.edit_phone);

        btn_sign_up = findViewById(R.id.btn_sign_up);
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String crid=cid.getText().toString();
                String crpw=cpw.getText().toString();
                String ph=cphone.getText().toString();
                Datadao doli=new Datadao();
                Datadto dt=new Datadto(crid, crpw,"",0,0,"",0,ph,"","","",0,"",7,7,7,3);
                if(doli.enroll(getApplicationContext(),dt)) {
                    Intent intent = new Intent(getApplicationContext(), Main.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"ID already exists\n",Toast.LENGTH_LONG).show();                }
            }
        });
    }
}