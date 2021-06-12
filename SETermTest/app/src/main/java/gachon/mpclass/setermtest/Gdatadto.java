package gachon.mpclass.setermtest;
//object class for access server grouplist database
public class Gdatadto {
    public String group_id;
    public String schedule;
    //constructor
    Gdatadto()
    {

    }
    //setter & getter method
    public String getGroup_id() {
        return group_id;
    }
    public String getSchedule()
    {
        return schedule;
    }
    public void setGroup(String group) {
        this.group_id = group;
    }
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

}