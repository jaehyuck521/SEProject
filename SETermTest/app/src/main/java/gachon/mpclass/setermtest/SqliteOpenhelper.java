package gachon.mpclass.setermtest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public  class SqliteOpenhelper extends SQLiteOpenHelper{

    public SqliteOpenhelper (Context context , String name,SQLiteDatabase.CursorFactory factory , int version ) {
        super(context, name, factory, version);
    }
    public void onCreate( SQLiteDatabase db ) {
        String usql = "create table if not exists user(id varchar(32) PRIMARY KEY , password varchar(128) not null, name varchar(32) not null,leader Integer,isStudent Integer, organ varchar(32) ,orgnumber Integer , phone varchar(50), groupleader varchar(30), dis varchar(120), info varchar(30), salary Integer, notice varchar(20), rest1 Integer, rest2 Integer, rest3 Integer, prefer Integer );";
        db.execSQL(usql);
        String gsql="create table if not exists grouptable(group_id varchar(32) PRIMARY KEY, schedule varchar(2000));";
        db.execSQL(gsql);
    }
    public void onUpgrade (SQLiteDatabase db , int oldVersion , int newVersion ) {
        String sql ="drop table if exists user";
        db.execSQL (sql);
        onCreate(db);
    }
}