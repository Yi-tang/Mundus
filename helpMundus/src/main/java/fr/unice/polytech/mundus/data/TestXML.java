package fr.unice.polytech.mundus.data;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileOutputStream;
import java.io.FileWriter;

/**
 * Created by TY on 2016/9/1.
 */
public class TestXML
{
    //定义XML架构
    Document document = DocumentHelper.createDocument();
    Element root = document.addElement("userList");
    //添加子节点
    Element returnvalue = root.addElement("returnvalue");
    Element returninfo = root.addElement("returninfo");
    /*为子节点添加内容
    returnvalue.addText("false");
    returninfo.addText("get-session-fail");
    root.addElement("id").addText("12345");
    */

    public static void main(String[] args) throws Exception
    {
        // 第一种方式：创建文档，并创建根元素
        // 创建文档:使用了一个Helper类
        Document document = DocumentHelper.createDocument();

        // 创建根节点并添加进文档
        Element root = DocumentHelper.createElement("userList");
        document.setRootElement(root);


        Element root2 = root.addElement("prof");

        // 添加属性
        root2.addAttribute("id", "0");
        // 添加子节点:add之后就返回这个元素
        Element usernameElement = root2.addElement("username");
        Element passwordElement = root2.addElement("password");

        usernameElement.setText("Bond");
        passwordElement.setText("123");

 /*      // 输出
        // 输出到控制台
        XMLWriter xmlWriter = new XMLWriter();
        xmlWriter.write(document);
*/
        // 输出到文件
        // 格式
        OutputFormat format = new OutputFormat("    ", true);// 设置缩进为4个空格，并且另起一行为true
        XMLWriter xmlWriter2 = new XMLWriter(
                new FileOutputStream("helpMundus/outputs/student.xml"), format);
        xmlWriter2.write(document);
        //xmlWriter2.flush();
/*
        // 另一种输出方式，记得要调用flush()方法,否则输出的文件中显示空白
        XMLWriter xmlWriter3 = new XMLWriter(new FileWriter("helpMundus/outputs/student2.xml"),
                format);
        xmlWriter3.write(document);

        // close()方法也可以
*/
    }


}