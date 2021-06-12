package gachon.mpclass.setermtest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class DeleteAccountPopup extends Activity {

    ImageButton btn_yes;
    ImageButton btn_no;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Call the sqlite manager for use of sqlite
        SqliteManager sqm = new SqliteManager(this, "kang.db");

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_delete_account_popup);

        btn_yes = findViewById(R.id.btn_yes);
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences = getSharedPreferences("id", MODE_PRIVATE);
                Datadto dt=new Datadto();
                dt=sqm.getCurrentUser(preferences.getString("id", "null"));
                Datadao doli=new Datadao();
                doli.deleteUser(getApplicationContext(),dt.id);
            }
        });
        btn_no = findViewById(R.id.btn_no);
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //확인 버튼 클릭
    public void mOnClose(View v) {
        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}