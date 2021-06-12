package gachon.mpclass.setermtest;

//data dto object  for accessing server database
public class Datadto {
    //variables
    public String id;
    public String password;
    public String name;
    public long leader;
    public long isStudent;
    public String organ;
    public long organnum;
    public String phonenum;
    public String groupLeadname;
    public String discription;
    public String g_info;
    public long salary;
    public String groupNotice;
    public long rest1;
    public long rest2;
    public long rest3;
    public long prefer;
    //data dto constructor

    public Datadto(String id, String password,String name, long lead, long isStu,  String org, long orgnum, String phone,String gleader,String dis, String info, long salary, String notice,long rest1, long rest2, long rest3, long prefer)
    {
        this.id=id;
        this.password=password;
        this.name=name;
        this.leader=lead;
        this.isStudent=isStu;
        this.organ=org;
        this.organnum=orgnum;
        this.phonenum=phone;
        this.groupLeadname=gleader;
        this.g_info=info;
        this.salary=salary;
        this.groupNotice=notice;
        this.discription=dis;
        this.rest1=rest1;
        this.rest2=rest2;
        this.rest3=rest3;
        this.prefer=prefer;
    }
    public Datadto()
    {
    }
    //setter & getter method
    public String getId()
    {
        return id;
    }
    public String getPassword()
    {
        return password;
    }
    public String getName()
    {
        return name;
    }
    public String getOrgan()
    {
        return organ;
    }

    public long getRest3() {
        return rest3;
    }

    public long getRest2() {
        return rest2;
    }

    public long getRest1() {
        return rest1;
    }

    public long getPrefer() {
        return prefer;
    }

    public String getGroupNotice() {
        return groupNotice;
    }

    public String getGroupLeadname() {
        return groupLeadname;
    }

    public String getG_info() {
        return g_info;
    }

    public long getSalary() {
        return salary;
    }

    public long getOrgannum() {
        return organnum;
    }

    public String getDiscription() {
        return discription;
    }

    public String getPhonenum()
    {
        return phonenum;
    }

    public long getIsStudent()
    {
        return isStudent;
    }
    public long getLeader() { return leader; }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRest3(long rest3) {
        this.rest3 = rest3;
    }

    public void setRest2(long rest2) {
        this.rest2 = rest2;
    }

    public void setRest1(long rest1) {
        this.rest1 = rest1;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public void setOrgannum(long organnum) {
        this.organnum = organnum;
    }

    public void setGroupNotice(String groupNotice) {
        this.groupNotice = groupNotice;
    }

    public void setGroupLeadname(String groupLeadname) {
        this.groupLeadname = groupLeadname;
    }

    public void setG_info(String g_info) {
        this.g_info = g_info;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public void setPrefer(long prefer) {
        this.prefer = prefer;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIsStudent(long isStudent) {
        this.isStudent = isStudent;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public void setLeader(long leader) {
        this.leader = leader;
    }

}
