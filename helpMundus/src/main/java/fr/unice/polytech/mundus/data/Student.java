package fr.unice.polytech.mundus.data;

/**
 * @author TANG Yi
 */
public class Student extends User {
    private Speciality speciality=null;
    public Student(String name,String pass,String mail, Speciality speciality){
        this.name=name;
        this.mail=mail;
        this.speciality=speciality;
        password=pass;
    }
    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public enum Speciality{
        SI,MAM,eau,electronique,Bio;
    }
}
