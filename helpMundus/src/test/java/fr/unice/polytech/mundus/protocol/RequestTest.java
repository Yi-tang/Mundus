package fr.unice.polytech.mundus.protocol;

import fr.unice.polytech.mundus.data.Student;
import junit.framework.TestCase;

/**
 * @author TANG Yi
 */
public class RequestTest extends TestCase {
    public void testGetCmd(){
        Request request = new Request();
        request.setCmd(Request.Command.signUp);
        request.setDetail(new Student("A","123","a@ss.com", Student.Speciality.Bio));
        assertEquals(Request.Command.signUp,request.getCmd());
    }
    public void testGetDetail(){
        Request request = new Request();
        request.setCmd(Request.Command.signUp);
        request.setDetail(new Student("A","123","a@ss.com", Student.Speciality.Bio));
        Student s1 = (Student) request.getDetail();
        assertEquals("A",s1.getName());
        assertEquals(Student.Speciality.Bio,s1.getSpeciality());
    }
}
