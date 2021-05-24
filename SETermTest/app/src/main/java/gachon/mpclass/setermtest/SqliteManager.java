package gachon.mpclass.setermtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SqliteManager {
    SQLiteDatabase database;
    SqliteOpenhelper helper;

    public SqliteManager(Context context, String name) {
        helper = new SqliteOpenhelper(context, name, null, 1);
    }

    public static SqliteManager open(Context context, String name) {
        return new SqliteManager(context, name);
    }
    // print the all user. we can get the data using different command
    //필요한 조건으로 충분히 가능.
    public void select() { //출력해서 확인하는 용도로 사용.
        database = helper.getWritableDatabase();
        Cursor c = database.rawQuery("select * from user", null);
        while (c.moveToNext()) {
            String id = c.getString(0);
            String pw = c.getString(1);
            String name = c.getString(2);
            long leader=c.getInt(3);
            long istu=c.getInt(4);
            String organ=c.getString(5);
            Log.i("db1", "id: " + id + " " + pw + " " + name + " " +leader +istu +" "+organ);
        }
    }
    //write agent info 이후에 가능
    public boolean insert(Datadto sdto) { //서버에서 읽어온 데이터, sqlite에 집어넣는 함수.
        try {
            database = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id", sdto.getId());
            values.put("password", sdto.getPassword());
            values.put("name", sdto.getName());
            values.put("prefer", sdto.getPrefer());
            values.put("salary", sdto.getSalary());
            values.put("phone", sdto.getPhonenum());
            values.put("leader", sdto.getLeader());
            values.put("isStudent", sdto.getIsStudent());
            values.put("rest1", sdto.getRest1());
            values.put("rest2", sdto.getRest2());
            values.put("rest3", sdto.getRest3());
            values.put("organ", sdto.getOrgan());
            values.put("notice",sdto.getGroupNotice());
            values.put("info",sdto.getG_info());
            values.put("dis",sdto.getDiscription());
            values.put("groupleader",sdto.getGroupLeadname());
            values.put("orgnumber",sdto.getOrgannum());
            database.insert("user", null, values);
            Log.i("db1", "Success");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public void delete() //sqlite의 데이터를 계속 업로드 해주기 위해, 데이터를 삭제해주는 함수
    {
        database=helper.getWritableDatabase();
        database.execSQL("delete from user");

    }
    public boolean checkId(String id) //ID가 있는 지 체크 해준다. ID중복 허용X
    {
        boolean exists=false;
        database=helper.getWritableDatabase();
        Cursor c=database.rawQuery("select exists(select * from user where id="+"'"+id+"'"+")",null);
        c.moveToNext();
        int result=c.getInt(0);
        if(result==0)
        {
            exists=false;
        }
        if(result==1)
        {
            exists=true;
        }
        return exists;
    }
    public boolean checkPassword(String id,String password) //해당 id의 비밀번호가 맞는 지 체크해주는 함수이다.
    {
        database=helper.getWritableDatabase();
        Cursor c=database.rawQuery("select password from user where id="+"'"+id+"'",null);
        c.moveToNext();
        String result=c.getString(0);
        Log.i("db1"," "+result);
        if(password.equals(result))
        {
            return true;
        }
        else
        {
            return false;
        }

    }
    //
    public ArrayList<Datadto> getList(String organname) //알고리즘을 구할 때, 조직원을 가져올 수 있는 함수, 리스트 형태로 반환
    //하기 때문에, 리스트에서 빼서 사용하면 된다.
    {
        ArrayList<Datadto> List=new ArrayList<Datadto>();
        database=helper.getReadableDatabase();
        Cursor c=database.rawQuery("select * from user where organ="+ "'"+organname+"'",null);
        int count=c.getCount();
        while(c.moveToNext())
        {
            String id = c.getString(0);
            String pw = c.getString(1);
            String name = c.getString(2);
            long leader=c.getInt(3);
            long istu=c.getInt(4);
            String organ=c.getString(5);
            long orgnum=c.getInt(6);
            String phone=c.getString(7);
            String gleader=c.getString(8);
            String dis=c.getString(9);
            String info=c.getString(10);
            long salary=c.getInt(11);
            String notice=c.getString(12);
            long r1=c.getInt(13);
            long r2=c.getInt(14);
            long r3=c.getInt(15);
            long prefer=c.getInt(16);
            Datadto ldto=new Datadto(id,pw,name,leader,istu,organ, orgnum, phone,gleader, dis, info, salary, notice, r1,r2,r3, prefer);
            List.add(ldto);
        }
        return List;
    }
    public Datadto getCurrentUser(String id) //현재 사용자 정보 가져오기용..?
    {
        database=helper.getReadableDatabase();
        Cursor c=database.rawQuery("select * from user where id="+"'"+id+"'",null);
        c.moveToNext();
        String cid = c.getString(0);
        String pw = c.getString(1);
        String name = c.getString(2);
        long leader=c.getInt(3);
        long istu=c.getInt(4);
        String organ=c.getString(5);
        long orgnum=c.getInt(6);
        String phone=c.getString(7);
        String gleader=c.getString(8);
        String dis=c.getString(9);
        String info=c.getString(10);
        long salary=c.getInt(11);
        String notice=c.getString(12);
        long r1=c.getInt(13);
        long r2=c.getInt(14);
        long r3=c.getInt(15);
        long prefer=c.getInt(16);
        Datadto ldto=new Datadto(cid,pw,name,leader,istu,organ, orgnum, phone,gleader, dis, info, salary, notice, r1,r2,r3, prefer);
        return ldto;
    }
    public boolean insertGroup(Gdatadto gdt)
    {
        try
        {
            database=helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("group_id",gdt.group_id);
            values.put("schedule",gdt.schedule);
            database.insert("grouptable", null, values);
            Log.i("db1","test "+gdt.schedule);
            Log.i("db1","Success");
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public String getSchedule(String group)
    {
        database = helper.getReadableDatabase();
        Cursor c = database.rawQuery("select schedule from grouptable where group_id="+ "'" + group + "'", null);
        c.moveToNext();
        String fschedule= c.getString(0);
        return fschedule;
    }
    public void deleteGroup() //sqlite의 데이터를 계속 업로드 해주기 위해, 데이터를 삭제해주는 함수
    {
        database=helper.getWritableDatabase();
        database.execSQL("delete from grouptable");

    }

    //그저 테스트용
    /*public String getTest(Context context, String organ)
    {
        database=helper.getReadableDatabase();
        Cursor c=database.rawQuery("select id from user where organ="+ "'"+organ+"'",null);
        int count=c.getCount();
        String log="";
        while(c.moveToNext())
        {
            String id=c.getString(0);

            log=log+id;
        }
        return log;


    }
     */

}