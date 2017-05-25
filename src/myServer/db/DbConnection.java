package myServer.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbConnection {

    private static  Connection con;

    public static  Connection getConnection(){
        try{
            Class.forName("org.sqlite.JDBC").newInstance();
            String url="jdbc:sqlite:" +System.getProperty("user.dir")+"\\assets\\Clients.db";
            if(con==null) con=DriverManager.getConnection(url);
            return  con;
        } catch (IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
