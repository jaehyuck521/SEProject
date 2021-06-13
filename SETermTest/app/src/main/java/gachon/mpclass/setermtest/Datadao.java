package gachon.mpclass.setermtest;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
//class for access the firebase idlist database
public class Datadao {
    FirebaseDatabase database; //firebase variable

    public void DataDao() {
    }

    public boolean enroll(Context context, Datadto dto) //Enroll the user
    {
        SqliteManager sqm = new SqliteManager(context, "kang.db");
        boolean check = sqm.checkId(dto.getId()); //check the redundant id from sqlite
        if (check == false) {
            database = FirebaseDatabase.getInstance();
            database.getReference().child("idlist").child(dto.id).setValue(dto); //if it is not redundant
            //enroll the user database to server
            return true;
        } else {
            return false; //If id exists, enroll fail
        }
    }
    //login method
    public boolean login(Context context, String id, String password)
    {
        SqliteManager sqm = new SqliteManager(context, "kang.db");
        boolean idcheck = sqm.checkId(id); //check the id if it exists
        if (idcheck == true) {
            boolean pwcheck = sqm.checkPassword(id, password); //and password check
            if (pwcheck == true) {
                Log.i("db1", "login success"); //all the process check complete, go to login
                return true;
            } else {
                Log.i("db1", "password invalid"); //if the password invalid
                return false;
            }
        } else {
            Log.i("db1", " " + "id invalid"); //if the id invalid
            return false;
        }
    }
    // update prefertime, and rest day.
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
    //group leader update wage, change the database wage
    public boolean updateWage(Context context, String id, long wage) //change wage
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

    //invite group member press the id and organaziation name
    public boolean setGroup(Context context, String id, String org) {
        database = FirebaseDatabase.getInstance();
        SqliteManager sqm = new SqliteManager(context, "kang.db");
        if (sqm.checkId(id)) { //check the id if it exists
            database.getReference().child("idlist").child(id).child("organ").setValue(org); //update
            return true;
        } else {
            return false;
        }
    }

    //if groupleader makes the group, set the own variable to 1, default is 0
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
    //If the user is student, save the isStudent 1 to sever database or worker set default: 0
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
    //if the user is work set the isStudent variable to 0
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

    //if the user leaves the app, delete the user info
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

    // group leader can set the notice,salary,working information to group member
    public boolean setNotice(Context context, String organ, String workingfo, int sal, String notice) {
        database = FirebaseDatabase.getInstance();
        SqliteManager sqm = new SqliteManager(context, "kang.db");
        ArrayList<Datadto> li = new ArrayList<Datadto>(); //get the group member
        li = sqm.getList(organ);
        for (int i = 0; i < li.size(); i++) { //set  the all group memeber's notice and salary, info
            database.getReference().child("idlist").child(li.get(i).id).child("g_info").setValue(workingfo);
            database.getReference().child("idlist").child(li.get(i).id).child("salary").setValue(sal);
            database.getReference().child("idlist").child(li.get(i).id).child("groupNotice").setValue(notice);
        }
        return true;
    }

    //group leader invites the group member, and insert the group information using getCurrentUser method
    public boolean inviteGroupcomponent(Context context, String id, String iorgan, long iorgannum, String lname, String gdis, String g_info, String gnotice, long salary) {
        database = FirebaseDatabase.getInstance();
        SqliteManager sqm = new SqliteManager(context, "kang.db");
        if (sqm.checkId(id)) {
            //insert the group information
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
    // method for disband group. Group member can disband, set the group variable to default.
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

    //after the algorithm the group's schedule information sends to server database
    //for using group name and schedule. it saves the group member's schedule to server.
    public boolean insertSchedule(String group, String sschedule) {
        database = FirebaseDatabase.getInstance();
        database.getReference().child("Groupschedule").child(group).child("group_id").setValue(group);
        database.getReference().child("Groupschedule").child(group).child("schedule").setValue(sschedule);
        return true;
    }

}



