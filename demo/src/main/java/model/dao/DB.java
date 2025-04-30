package model.dao;
import java.sql.Connection;

public abstract class DB {
    protected String URL = "jdbc:mysql://localhost:3306/iotbaydb";
    protected String dbuser = "root";// db root user
    protected String dbpass = "1111"; // db root password
    protected String driver = "com.mysql.cj.jdbc.Driver"; // jdbc client driver - built in with NetBeans
    protected Connection conn; // connection null-instance to be initialized in sub-classes
}
