package fr.unice.polytech.mundus.protocol;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author TANG Yi
 */
public class ResponseTest extends TestCase {

    public void testIsFlag() {
        Response res = new Response();
        res.setType(Response.Type.MODIFICAITION);
        res.setFlag(true);
        res.setContent("sucess");
        Assert.assertEquals(true,res.isFlag());

        res.setFlag(false);
        Assert.assertEquals(false,res.isFlag());
    }
    public void testGetType(){
        Response res = new Response();
        res.setType(Response.Type.MODIFICAITION);
        res.setFlag(true);
        Assert.assertEquals(Response.Type.MODIFICAITION,res.getType());
    }
    public void testGetContent(){
        Response res = new Response();
        res.setType(Response.Type.MODIFICAITION);
        res.setFlag(true);
        res.setContent("sucess");
        Assert.assertEquals("sucess",res.getContent());
    }
}
