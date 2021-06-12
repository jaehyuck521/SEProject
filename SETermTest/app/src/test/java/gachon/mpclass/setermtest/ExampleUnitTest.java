package gachon.mpclass.setermtest;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class ExampleUnitTest {

    public CreatedSchedule createdSchedule=new CreatedSchedule();
    public Datadto datadto = new Datadto();

    ArrayList<String> list= new ArrayList<String>();
    long b;


    @Test
    public void getRandomElement_Test(){


        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
       assertEquals(createdSchedule.getRandomElement(list).size(),3);
    }
    @Test
    public void getSalary_Test(){
        assertSame(datadto.getSalary(),b);
    }
    @Test
    public void getIsStudent_Test(){
        assertSame(datadto.getIsStudent(),b);
    }
    @Test
    public void getIsLeader_Test(){
        assertSame(datadto.getLeader(),b);
    }

    @Test
    public void getRest1_Test(){
        assertSame(datadto.getRest1(),b);
    }

    @Test
    public void getRest2_Test(){
        assertSame(datadto.getRest2(),b);
    }
    @Test
    public void getRest3_Test(){
        assertSame(datadto.getRest3(),b);
    }

    @Test
    public void getOrgannum_Test(){
        assertSame(datadto.getOrgannum(),b);
    }
    @Test
    public void prefer_Test(){
        assertSame(datadto.getPrefer(),b);
    }


}