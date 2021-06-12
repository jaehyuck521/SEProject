package gachon.mpclass.setermtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
//for class saving the database from server database
public class SqliteManager {
    SQLiteDatabase database;
    SqliteOpenhelper helper;

    public SqliteManager(Context context, String name) { //set the helper class
        helper = new SqliteOpenhelper(context, name, null, 1);
    }

    public static SqliteManager open(Context context, String name) {
        return new SqliteManager(context, name);
    }

    // print the all user. we can get the data using different command
    //for check the database method
    public void select() {
        database = helper.getWritableDatabase();
        Cursor c = database.rawQuery("select * from user", null); //set the cursor
        while (c.moveToNext()) {
            String id = c.getString(0);
            String pw = c.getString(1);
            String name = c.getString(2);
            long leader = c.getInt(3);
            long istu = c.getInt(4);
            String organ = c.getString(5);
            Log.i("db1", "id: " + id + " " + pw + " " + name + " " + leader + istu + " " + organ);
        }
    }

    //Reading from server, insert the server database to sqlite internal database
    public boolean insert(Datadto sdto) {
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
            values.put("notice", sdto.getGroupNotice());
            values.put("info", sdto.getG_info());
            values.put("dis", sdto.getDiscription());
            values.put("groupleader", sdto.getGroupLeadname());
            values.put("orgnumber", sdto.getOrgannum());
            database.insert("user", null, values);
            Log.i("db1", "Success");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //the method for delete because of synchronization between seqlite and server
    public void delete() {
        database = helper.getWritableDatabase();
        database.execSQL("delete from user");

    }

    //Check the id exists, check redundant id
    public boolean checkId(String id) {
        boolean exists = false;
        database = helper.getWritableDatabase();
        Cursor c = database.rawQuery("select exists(select * from user where id=" + "'" + id + "'" + ")", null);
        c.moveToNext();
        int result = c.getInt(0);
        if (result == 0) {
            exists = false;
        }
        if (result == 1) {
            exists = true;
        }
        return exists;
    }

    //check the password method because valid password
    public boolean checkPassword(String id, String password) {
        database = helper.getWritableDatabase();
        Cursor c = database.rawQuery("select password from user where id=" + "'" + id + "'", null);
        c.moveToNext();
        String result = c.getString(0);
        Log.i("db1", " " + result);
        if (password.equals(result)) {
            return true;
        } else {
            return false;
        }

    }

    //for the making schedule, if you press the group name, you can get the all group member's information by list type
    public ArrayList<Datadto> getList(String organname) {
        ArrayList<Datadto> List = new ArrayList<Datadto>();
        database = helper.getReadableDatabase();
        Cursor c = database.rawQuery("select * from user where organ=" + "'" + organname + "'", null);
        //find the group member
        int count = c.getCount();
        while (c.moveToNext()) //get the each group member's information and insert to list
        {
            String id = c.getString(0);
            String pw = c.getString(1);
            String name = c.getString(2);
            long leader = c.getInt(3);
            long istu = c.getInt(4);
            String organ = c.getString(5);
            long orgnum = c.getInt(6);
            String phone = c.getString(7);
            String gleader = c.getString(8);
            String dis = c.getString(9);
            String info = c.getString(10);
            long salary = c.getInt(11);
            String notice = c.getString(12);
            long r1 = c.getInt(13);
            long r2 = c.getInt(14);
            long r3 = c.getInt(15);
            long prefer = c.getInt(16);
            Datadto ldto = new Datadto(id, pw, name, leader, istu, organ, orgnum, phone, gleader, dis, info, salary, notice, r1, r2, r3, prefer);
            List.add(ldto);
        }
        return List; //return the group memeber list
    }

    //Get the current login user's information method. using by sharedpreference
    public Datadto getCurrentUser(String id) {
        database = helper.getReadableDatabase();
        Cursor c = database.rawQuery("select * from user where id=" + "'" + id + "'", null);
        //get the id's information
        c.moveToNext();
        String cid = c.getString(0);
        String pw = c.getString(1);
        String name = c.getString(2);
        long leader = c.getInt(3);
        long istu = c.getInt(4);
        String organ = c.getString(5);
        long orgnum = c.getInt(6);
        String phone = c.getString(7);
        String gleader = c.getString(8);
        String dis = c.getString(9);
        String info = c.getString(10);
        long salary = c.getInt(11);
        String notice = c.getString(12);
        long r1 = c.getInt(13);
        long r2 = c.getInt(14);
        long r3 = c.getInt(15);
        long prefer = c.getInt(16);
        Datadto ldto = new Datadto(cid, pw, name, leader, istu, organ, orgnum, phone, gleader, dis, info, salary, notice, r1, r2, r3, prefer);
        return ldto; //return the current user's all information
    }

    // the method for insert the schedule information from server to sqlite database
    public boolean insertGroup(Gdatadto gdt) {
        try {
            database = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("group_id", gdt.group_id);
            values.put("schedule", gdt.schedule);
            database.insert("grouptable", null, values);
            Log.i("db1", "test " + gdt.schedule);
            Log.i("db1", "Success");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //the method for getting schedule information from sqlite, because of showing by timetable
    public String getSchedule(String group) {
        database = helper.getReadableDatabase();
        Cursor c = database.rawQuery("select schedule from grouptable where group_id=" + "'" + group + "'", null);
        c.moveToNext();
        String fschedule = c.getString(0); //get the schedule information
        return fschedule;
    }

    // continuously updating sqlite database,  delete and insert iterates for synchronization between server and sqlite
    public void deleteGroup() {
        database = helper.getWritableDatabase();
        database.execSQL("delete from grouptable");
    }

}