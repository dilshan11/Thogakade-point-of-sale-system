/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.thogakade.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author niroth
 */
public class DBConnection {
    private Connection connection;
    private static DBConnection dBConnection;
    private DBConnection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/ThogaKade", "root", "ijse");
    }
    public static DBConnection getInstance() throws ClassNotFoundException, SQLException{
        if(dBConnection==null){
            dBConnection=new DBConnection();
        }
        return dBConnection;
    }
    public Connection getConnection(){
        return connection;
    }
}
