/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * This class allows to get a connection from the database.
 * 
 */
public class DBConnector {
    public static Connection getConnection() throws SQLException{
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bussystem", "root", "");
        
        return con;
    }
}
