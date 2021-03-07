package xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用DOM方式解析XML文档
 * @author wm
 */
public class ParseXmlDemo {
    public static void main(String[] args) {
        /**
         * 解析一个xml文档的大致流程
         * 1：创建SAXReader
         * 2：使用SAXReader读取要解析的XML文档
         *  并生成Document对象
         *  这个过程就是DOM解析耗时耗资源的地方
         *  因为会将XML文档数据全部读取完毕并且内建
         *  整棵树的结构。
         *  3.通过Document对象获取根元素
         *  4：按照XML表示的树结构从根元素开始逐级
         *  遍历子元素以达到获取XML文档数据的目的。
         */

        //将emplist.xml 文件中的员工信息解析出来
        List<Emp> empList =new ArrayList<>();

        try{
            //1
            SAXReader reader =new SAXReader();
            //2
            Document doc =reader.read(new File("./emplist.xml"));

            //3获取根元素list
            /**
             * Element的每一个实例用于表示XML文档中的一个元素（一对标签）
             * 通过Element我们可以得到其表示的元素的一切信息：
             *
             * String getName()
             * 获取当前元素的名字(标签名）
             *
             * String getText()
             * 获取当前元素中间的文本（开始与结束标签中间的文本信息）
             *
             * Element element(String name)
             * 获取当前元素下指定名字的子元素
             *
             * 获取子元素的内容
             * String elementText(String name)
             *
             * List elements()
             * 获取当前元素下的所有子元素
             *
             * List elements (String name)
             * 获取当前元素下所有同名子元素（指定的）
             */
            Element root=doc.getRootElement();

            System.out.println(root.getName());
            /**
             * 获取根标签下所有的<emp>标签
             */
            List<Element> list=root.elements("emp");
            System.out.println(list.size());

            /**
             * 遍历每一个员工信息
             */
            for(Element empEle:list){
                //获取员工姓名
                Element nameEle=empEle.element("name");
                String name=nameEle.getTextTrim();
                System.out.println("name:"+name);
                //获取员工age
                Element ageEle=empEle.element("age");
                int age=Integer.parseInt(ageEle.getTextTrim());
                System.out.println("age:"+age);
                //获取员工性别
//                Element genderEle= empEle.element("gender");
//                String gender=genderEle.getTextTrim();
//                System.out.println("gender:"+gender);
                String gender=empEle.elementText("gender");
                System.out.println("gender:"+gender);
                //获取工资
                Element salaryEle=empEle.element("salary");
                int salary=Integer.parseInt(salaryEle.getTextTrim());
                System.out.println("salary:"+salary);
                //获取ID
                int  id=Integer.parseInt(empEle.attributeValue("id"));
                System.out.println("ID:"+id);
                System.out.println("_______________");

                Emp emp=new Emp(id,name,age,gender,salary);
                empList.add(emp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
