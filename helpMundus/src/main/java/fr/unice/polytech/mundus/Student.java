package fr.unice.polytech.mundus;

import java.io.Serializable;

/**
 * Created by spong on 2016/8/18.
 */
public class Student implements Serializable {

    private String name=null;
    private String mail=null;
    private String speciality=null;

    public Student(String name,String mail,String speciality){
        this.name=name;
        this.mail=mail;
        this.speciality=speciality;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
