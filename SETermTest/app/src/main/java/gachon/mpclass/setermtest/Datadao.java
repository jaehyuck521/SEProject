package gachon.mpclass.setermtest;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Datadao { //서버의 데이터에 접근하기 위한 클래스
    FirebaseDatabase database;

    public void DataDao() {
    }

    public boolean enroll(Context context, Datadto dto) //회원 가입
    {
        SqliteManager sqm = new SqliteManager(context, "kang.db");
        boolean check = sqm.checkId(dto.getId()); //현재 데이터에 있는 것과 같은 지 비교해준다. id중복 체크
        if (check == false) {
            database = FirebaseDatabase.getInstance();
            database.getReference().child("idlist").child(dto.id).setValue(dto); //ID가 없으면 가입성공
            return true;
        } else {
            return false; //ID가 있으면, 가입실패.
        }
    }

    public boolean login(Context context, String id, String password) //로그인
    {
        SqliteManager sqm = new SqliteManager(context, "kang.db");
        boolean idcheck = sqm.checkId(id);
        if (idcheck == true) {
            boolean pwcheck = sqm.checkPassword(id, password);
            if (pwcheck == true) {
                Log.i("db1", "login success");
                return true;
            } else {
                Log.i("db1", "password invalid");
                return false;
            }
        } else {
            Log.i("db1", " " + "id invalid");
            return false;
        }
    }

    // update prefertime, and rest day. 직원들이 선호하는 시간과 휴식시간을 계속 업데이트해줄 수 있게하는 함수
    public boolean updateSchedule(Context context, String id, long rest1, long rest2, long rest3, long prefer) {
        database = FirebaseDatabase.getInstance();
        SqliteManager sqm = new SqliteManager(context, "kang.db");
        if (sqm.checkId(id)) {
            database.getReference().child("idlist").child(id).child("prefer").setValue(prefer); //update
            database.getReference().child("idlist").child(id).child("rest1").setValue(rest1); //update
            database.getReference().child("idlist").child(id).child("rest2").setValue(rest2); //update
            database.getReference().child("idlist").child(id).child("rest3").setValue(rest3); //update
            return true;
        } else {
            return false;
        }
    }

    public boolean updateWage(Context context, String id, long wage) //wage 바꿔주기.
    {
        database = FirebaseDatabase.getInstance();
        SqliteManager sqm = new SqliteManager(context, "kang.db");
        if (sqm.checkId(id)) {
            database.getReference().child("idlist").child(id).child("wage").setValue(wage); //update
            return true;
        } else {
            return false;
        }
    }

    //그룹원을 초대하거나, 그룹을 만들 때, 그룹이름을 ""회원가입 때, 이렇게 저장을 하는데, 그룹명을 세팅해준다.
    public boolean setGroup(Context context, String id, String org) {
        database = FirebaseDatabase.getInstance();
        SqliteManager sqm = new SqliteManager(context, "kang.db");
        if (sqm.checkId(id)) {
            database.getReference().child("idlist").child(id).child("organ").setValue(org); //update
            return true;
        } else {
            return false;
        }
    }

    //리더가 그룹을 만들었을 때, 자신의 leader값을 1로 설정. default값은 0
    public boolean setLeader(Context context, String id, String gname, int gnumber, String dis) {
        database = FirebaseDatabase.getInstance();
        SqliteManager sqm = new SqliteManager(context, "kang.db");
        if (sqm.checkId(id)) {
            database.getReference().child("idlist").child(id).child("leader").setValue(1); //update
            database.getReference().child("idlist").child(id).child("organ").setValue(gname); //update
            database.getReference().child("idlist").child(id).child("groupLeadname").setValue(id); //update
            database.getReference().child("idlist").child(id).child("organnum").setValue(gnumber); //update
            database.getReference().child("idlist").child(id).child("discription").setValue(dis); //update
            return true;
        } else {
            return false;
        }
    }

    //사용자가 학생일 경우, 서버의 데이터베이스의 isStudent를 1로 저장. 직장인 default인 0으로 설정.
    public boolean setStudent(Context context, String id, String name) {
        database = FirebaseDatabase.getInstance();
        SqliteManager sqm = new SqliteManager(context, "kang.db");
        if (sqm.checkId(id)) {
            database.getReference().child("idlist").child(id).child("isStudnet").setValue(1); //update
            database.getReference().child("idlist").child(id).child("name").setValue(name);
            return true;
        } else {
            return false;
        }
    }

    public boolean setWorker(Context context, String id, String name) {
        database = FirebaseDatabase.getInstance();
        SqliteManager sqm = new SqliteManager(context, "kang.db");
        if (sqm.checkId(id)) {
            database.getReference().child("idlist").child(id).child("name").setValue(name);
            return true;
        } else {
            return false;
        }
    }

    //사용자 삭제
    public boolean deleteUser(Context context, String id) {
        database = FirebaseDatabase.getInstance();
        SqliteManager sqm = new SqliteManager(context, "kang.db");
        if (sqm.checkId(id)) {
            database.getReference().child("idlist").child(id).setValue(null);
            return true;
        } else {
            return false;
        }

    }

    public boolean setNotice(Context context, String organ, String workingfo, int sal, String notice) {
        database = FirebaseDatabase.getInstance();
        SqliteManager sqm = new SqliteManager(context, "kang.db");
        ArrayList<Datadto> li = new ArrayList<Datadto>();
        li = sqm.getList(organ);
        for (int i = 0; i < li.size(); i++) {
            database.getReference().child("idlist").child(li.get(i).id).child("g_info").setValue(workingfo);
            database.getReference().child("idlist").child(li.get(i).id).child("salary").setValue(sal);
            database.getReference().child("idlist").child(li.get(i).id).child("groupNotice").setValue(notice);
        }
        return true;
    }

    //리더가 그룹원을 초대할 때, getcurrentUser로, 파라미터들을 가져온다.
    public boolean inviteGroupcomponent(Context context, String id, String iorgan, long iorgannum, String lname, String gdis, String g_info, String gnotice, long salary) {
        database = FirebaseDatabase.getInstance();
        SqliteManager sqm = new SqliteManager(context, "kang.db");
        if (sqm.checkId(id)) {
            database.getReference().child("idlist").child(id).child("organ").setValue(iorgan);
            database.getReference().child("idlist").child(id).child("organnum").setValue(iorgannum);
            database.getReference().child("idlist").child(id).child("groupLeadname").setValue(lname);
            database.getReference().child("idlist").child(id).child("discription").setValue(gdis);
            database.getReference().child("idlist").child(id).child("g_info").setValue(g_info);
            database.getReference().child("idlist").child(id).child("groupNotice").setValue(gnotice);
            database.getReference().child("idlist").child(id).child("salary").setValue(salary);
            return true;
        } else {
            return false;
        }
    }

    //그룹원들 초대하는 코드 예시
        /*Datadao doli=new Datadao();
        Datadto dt=new Datadto();
        dt=sqm.getCurrentUser("zhao");
        doli.inviteGroupcomponent(getApplicationContext(),"hello",dt.organ,dt.organnum,dt.groupLeadname,dt.discription,dt.g_info,dt.groupNotice,dt.salary);
        */
    public boolean disbandGroup(Context context, String organ) {
        database = FirebaseDatabase.getInstance();
        SqliteManager sqm = new SqliteManager(context, "kang.db");
        ArrayList<Datadto> li = new ArrayList<Datadto>();
        li = sqm.getList(organ);
        for (int i = 0; i < li.size(); i++) {
            database.getReference().child("idlist").child(li.get(i).id).child("g_info").setValue("");
            database.getReference().child("idlist").child(li.get(i).id).child("salary").setValue(0);
            database.getReference().child("idlist").child(li.get(i).id).child("groupNotice").setValue("");
            database.getReference().child("idlist").child(li.get(i).id).child("organ").setValue("");
            database.getReference().child("idlist").child(li.get(i).id).child("organnum").setValue(0);
            database.getReference().child("idlist").child(li.get(i).id).child("groupLeadname").setValue("");
            database.getReference().child("idlist").child(li.get(i).id).child("discription").setValue("");
        }
        return true;
    }

    public boolean insertSchedule(String group, String sschedule) {
        database = FirebaseDatabase.getInstance();
        database.getReference().child("Groupschedule").child(group).child("group_id").setValue(group);
        database.getReference().child("Groupschedule").child(group).child("schedule").setValue(sschedule);
        return true;
    }

}



