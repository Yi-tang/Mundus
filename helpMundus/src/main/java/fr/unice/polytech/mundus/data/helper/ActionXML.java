package fr.unice.polytech.mundus.data.helper;

import fr.unice.polytech.mundus.data.Student;
import fr.unice.polytech.mundus.data.User;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author TANG Yi
 */

public class ActionXML {
    SAXReader saxReader = new SAXReader();
    //File userFile = new File("helpMundus/src/main/resources/user.xml");
    //File informationFile = new File("helpMundus/src/main/resources/information.xml");
    File file = null;
    Document document = null;
    Element root = null;

    /**
     *
     * @param file the xml file to be read
     * @throws DocumentException
     */
    public ActionXML(File file)throws DocumentException {
        this.file = file;
        document = saxReader.read(file);
        root = document.getRootElement();
    }

    /**
     * find in the user.xml with the name
     * @param name
     * @return the user if founded, or mull if not
     */
    public User getUser(String name) throws DocumentException{

        for (Iterator iter = root.elementIterator(); iter.hasNext();)
        {
            Element e = (Element) iter.next();
            if(e.elementText("username").equals(name)){
                User user = new User();
                user.setMail(e.elementText("mail"));
                user.setName(e.elementText("username"));
                user.setPassword(e.elementText("password"));
                return user;
            }
        }
        return null;
    }
    public static void main( String[] args ) throws DocumentException, IOException {
        Student student = new Student("peter","hehe","di@s", Student.Speciality.Bio);
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File("helpMundus/src/main/resources/user.xml"));
        Element root = document.getRootElement();
        String name = student.getName();

            Student.Speciality speciality = student.getSpeciality();

            Element root2 = root.addElement("student");
            int nb = root.elements().size()-1;
            root2.addAttribute("id", String.valueOf(nb));
            Element usernameElement = root2.addElement("username");
            Element passwordElement = root2.addElement("password");
            Element mailElement = root2.addElement("mail");
            Element specialityElement = root2.addElement("speciality");
            usernameElement.setText(name);
            passwordElement.setText(student.getPassword());
            mailElement.setText(student.getMail());
            specialityElement.setText(speciality.name());
        OutputFormat format = OutputFormat.createPrettyPrint();//th format of xml file
        XMLWriter xmlWriter =new XMLWriter( new FileOutputStream("helpMundus/src/main/resources/user.xml"), format);
        xmlWriter.write(document);//write the new xml into the file
        xmlWriter.flush();


    }


    /**
     * add a student into the user.xml
     * @param student
     * @return a flag to claim whether success or not
     */
    public boolean addStudent(Student student) throws DocumentException, IOException {
        String name = student.getName();
        if(getUser(name)==null){//hasn't got a user called the same name
            String password = student.getPassword();
            Student.Speciality speciality = student.getSpeciality();
            String mail = student.getMail();
            root = document.getRootElement();
            Element root2 = root.addElement("student");
            int nb = root.elements().size()-1;
            root2.addAttribute("id", String.valueOf(nb));
            Element usernameElement = root2.addElement("username");
            Element passwordElement = root2.addElement("password");
            Element mailElement = root2.addElement("mail");
            Element specialityElement = root2.addElement("speciality");
            usernameElement.setText(name);
            passwordElement.setText(password);
            mailElement.setText(mail);
            specialityElement.setText(speciality.name());
            writeXML();
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
    public void modifyInformation(String type,String title,String info) throws DocumentException, IOException {
        root = document.getRootElement();
        Element e = root.element(type);
        Element e2 = e.element(title);
        e2.setText(info);
        writeXML();
    }

    /**
     *
     * @param type  "practical","internship" or"speciality"
     * @param title : the exact title to be added
     * @param info the information to be added
     * @return false while the title already exists, true when success
     */
    public boolean addInformation(String type,String title,String info) throws DocumentException, IOException {
        root = document.getRootElement();
        Element e = root.element(type);
        if(e.element(title)==null){//doesn't exist such a title
            Element e2 = e.addElement(title);
            e2.setText(info);
            writeXML();
            return true;
        }
        else
            return false;
    }

    /**
     * change the xml file with the changement of Document
     * @throws IOException
     */
    public void writeXML() throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();//th format of xml file
        XMLWriter xmlWriter =new XMLWriter( new FileOutputStream(file), format);
        xmlWriter.write(document);//write the new xml into the file
        xmlWriter.flush();
    }

}
