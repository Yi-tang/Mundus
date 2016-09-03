package fr.unice.polytech.mundus.data.helper;

import fr.unice.polytech.mundus.data.Student;
import fr.unice.polytech.mundus.data.User;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

/**
 * @author TANG Yi
 */

public class ActionXML {
    SAXReader saxReader = new SAXReader();
    File userFile = new File("helpMundus/src/main/resources/user.xml");
    File informationFile = new File("helpMundus/src/main/resources/information.xml");
    Document document = null;
    Element root = null;

    public ActionXML() {  }

    /**
     * find in the user.xml with the name
     * @param name
     * @return the user if founded, or mull if not
     */
    public User getUser(String name) throws DocumentException{
        User user = null;
        document = saxReader.read(userFile);
        root = document.getRootElement();
        for (Iterator iter = root.elementIterator(); iter.hasNext();)
        {
            Element e = (Element) iter.next();
            Element username = e.element("username");
            if(username.getText()==name){
                user.setMail(e.elementText("mail"));
                user.setName(e.elementText("username"));
                user.setPassword(e.elementText("password"));
                return user;
            }
        }
        return user;
    }

    /**
     * add a student into the user.xml
     * @param student
     * @return a flag to claim whether success or not
     */
    public boolean addStudent(Student student) throws DocumentException {
        String name = student.getName();
        document = saxReader.read(userFile);
        if(getUser(name)==null){//hasn't got a user called the same name
            String password = student.getPassword();
            Student.Speciality speciality = student.getSpeciality();
            String mail = student.getMail();
            root = document.getRootElement();
            Element root2 = root.addElement("student");
            int nb = root.elements().size();
            root2.addAttribute("id", String.valueOf(nb));
            Element usernameElement = root2.addElement("username");
            Element passwordElement = root2.addElement("password");
            Element mailElement = root2.addElement("mail");
            Element specialityElement = root2.addElement("speciality");
            usernameElement.setText(name);
            passwordElement.setText(password);
            mailElement.setText(mail);
            specialityElement.setText(speciality.name());
            return true;
        }
        else{//the same name already in the list
            return false;
        }
    }

    /**
     * @param type: "practical","internship" or"speciality"
     * @param title : the exact title of the information
     * @return the information
     */
    public String getInformation(String type,String title) throws DocumentException{
        document = saxReader.read(informationFile);
        root = document.getRootElement();
        Element e = root.element(type);
        Element e2 = e.element(title);
        return e2.getText();
    }

    /**
     * @param type "practical","internship" or"speciality"
     * @param title : the exact title of the information
     * @param info the right information
     */
    public void modifyInformation(String type,String title,String info) throws DocumentException{
        document = saxReader.read(informationFile);
        root = document.getRootElement();
        Element e = root.element(type);
        Element e2 = e.element(title);
        e2.setText(info);
    }

    /**
     *
     * @param type  "practical","internship" or"speciality"
     * @param title : the exact title to be added
     * @param info the information to be added
     * @return false while the title already exists, true when success
     */
    public boolean addInformation(String type,String title,String info) throws DocumentException{
        document = saxReader.read(informationFile);
        root = document.getRootElement();
        Element e = root.element(type);
        if(e.element(title)==null){//doesn't exist such a title
            Element e2 = e.addElement(title);
            e2.setText(info);
            return true;
        }
        else
            return false;
    }

}
