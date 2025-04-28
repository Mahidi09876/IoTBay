package model.dao;
import java.sql.Connection;

public abstract class DB {
    protected String URL = "jdbc:derby://localhost:3306/";// replace this string with your jdbc:derby local host url
    protected String db = "usersdb";// name of the database
    protected String dbuser = "root";// db root user
    protected String dbpass = "admin"; // db root password
    protected String driver = "org.apache.derby.jdbc.ClientDriver"; // jdbc client driver - built in with NetBeans
    protected Connection conn; // connection null-instance to be initialized in sub-classes

    
}
