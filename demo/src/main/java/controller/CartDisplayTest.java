package controller;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import model.dao.*;
import model.*;

public class CartDisplayTest {

    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        try {

            DBConnector connector = new DBConnector();

            Connection conn = connector.openConnection();

            OrderDAO orderDAO = new OrderDAO(conn);
            
            
            connector.closeConnection();

        } catch (ClassNotFoundException | SQLException ex) {

            Logger.getLogger(TestDB.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

}
