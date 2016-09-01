package fr.unice.polytech.mundus;

/**
 * Created by TY on 2016/9/1.
 */
import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.dom4j.io.SAXReader;

/**
 * dom4j框架学习： 读取并解析xml
 *
 *
 */
public class TestXML2
{
    public static void main(String[] args) throws Exception
    {
        SAXReader saxReader = new SAXReader();

        Document document = saxReader.read(new File("helpMundus/outputs/user.xml"));

        // 获取根元素
        Element root = document.getRootElement();
        System.out.println("Root: " + root.getName());

        // 获取所有子元素
        List<Element> childList = root.elements();
        System.out.println("total child count: " + childList.size());

        // 获取特定名称的子元素
        List<Element> childList2 = root.elements("prof");
        System.out.println("prof child: " + childList2.size());

        // 获取名字为指定名称的第一个子元素
        Element firstStudentElement = root.element("student");
        // 输出其属性
        System.out.println("first student Attr: "
                + firstStudentElement.attribute(0).getName() + "="
                + firstStudentElement.attributeValue("id"));

        System.out.println("迭代输出-----------------------");
        // 迭代输出
        for (Iterator iter = root.elementIterator(); iter.hasNext();)
        {
            Element e = (Element) iter.next();
            System.out.println("id:"+e.attributeValue("id"));
            Element name = e.element("username");
            System.out.println("username:"+name.getText());

        }

        System.out.println("用DOMReader-----------------------");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        // 注意要用完整类名
        org.w3c.dom.Document document2 = db.parse(new File("helpMundus/outputs/user.xml"));

        DOMReader domReader = new DOMReader();

        // 将JAXP的Document转换为dom4j的Document
        Document document3 = domReader.read(document2);

        Element rootElement = document3.getRootElement();

        System.out.println("Root: " + rootElement.getName());

    }

}
