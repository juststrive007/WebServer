package cn.study;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Demo01 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1.注册驱动 运行提示ClassNotFound 把这个类
//        Class.forName("com.mysql.jdbc.Driver");

        //2.获取连接对象

            Connection conn= DriverManager.getConnection(
                    "jdbc:mysql://192.168.0.8:3306/newdb3",
                    "root","123456");

        System.out.println(conn);
        //3.创建SQL语句执行对象
        //4.执行SQL
        //5.关闭资源
    }
}
