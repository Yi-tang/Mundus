package fr.unice.polytech.mundus.data.helper;

import fr.unice.polytech.mundus.data.User;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.dom4j.DocumentException;

import java.io.File;

/**
 * Created by TY on 2016/9/4.
 */
public class ActionXMLTest extends TestCase {
    private File userFile = new File("src/test/resources/user.xml");
    private File infoFile = new File("src/test/resources/Information.xml");

    public void testGetUser() throws DocumentException {
        ActionXML aUser = new ActionXML(userFile);
        User u1 = aUser.getUser("Alice");//name in the list
        Assert.assertEquals("Alice",u1.getName());
        Assert.assertEquals("456",u1.getPassword());
        Assert.assertEquals("Alice@etu.unice.fr",u1.getMail());
        User u2 = aUser.getUser("p");//not in the list
        Assert.assertEquals(null,u2);
    }

    public void testGetInformation() throws DocumentException {
        ActionXML aUser = new ActionXML(infoFile);
        Assert.assertEquals("fffsssfffff",aUser.getInformation("speciality","SI"));
    }


}
