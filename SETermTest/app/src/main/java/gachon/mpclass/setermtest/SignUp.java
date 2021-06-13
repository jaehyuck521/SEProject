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
            // When the member sign-up button is pressed,
            // the DB value is compared with the ID, password,
            // and mobile phone number entered by the user.
            @Override
            public void onClick(View v) {
                String crid=cid.getText().toString(); //get thd id text
                String crpw=cpw.getText().toString(); //get the password check
                String ph=cphone.getText().toString();//get the phone check
                Datadao doli=new Datadao();
                Datadto dt=new Datadto(crid, crpw,"",0,0,"",0,ph,"","","",0,"",7,7,7,3);
                if(doli.enroll(getApplicationContext(),dt)) { //enroll the user to server if success, go to next layout
                    Intent intent = new Intent(getApplicationContext(), Main.class);
                    startActivity(intent);
                }
                else { //if the id already exists,
                    Toast.makeText(getApplicationContext(),"ID already exists\n",Toast.LENGTH_LONG).show();                }
            }
        });
    }
}