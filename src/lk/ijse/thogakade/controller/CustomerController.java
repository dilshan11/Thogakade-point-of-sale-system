/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.thogakade.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import lk.ijse.thogakade.db.DBConnection;
import lk.ijse.thogakade.model.Customer;

/**
 *
 * @author niroth
 */
public class CustomerController {
    
    public static boolean addCustomer(Customer customer) throws ClassNotFoundException, SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO Customer VALUES (?,?,?,?)");
        
        pstm.setObject(1, customer.getId());
        pstm.setObject(2, customer.getName());
        pstm.setObject(3, customer.getAddress());
        pstm.setObject(4, customer.getSalary());
        
        int affectedRows = pstm.executeUpdate();
        return affectedRows > 0;
    }
    
    public static boolean updateCustomer(Customer customer) throws ClassNotFoundException, SQLException{
        
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE Customer SET name=?,address=?, salary=? WHERE id=?");
        
        pstm.setObject(1, customer.getName());
        pstm.setObject(2, customer.getAddress());
        pstm.setObject(3, customer.getSalary());
        pstm.setObject(4, customer.getId());
        
        int affectedRows = pstm.executeUpdate();
        
        return affectedRows > 0;
    }
    
    public static Customer searchCustomer(String customerId) throws ClassNotFoundException, SQLException{
        
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Customer WHERE id='" + customerId + "'");
        
        if (rst.next()){
            
            Customer customer = new Customer();
            
            customer.setId(customerId);
            customer.setName(rst.getString(2));
            customer.setAddress(rst.getString(3));
            customer.setSalary(rst.getDouble(4));
            
            return customer;
        }else{
            return null;
        }
                
    }
    
    public static boolean deleteCustomer(String customerId) throws ClassNotFoundException, SQLException{
        
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM Customer WHERE id=?");
        pstm.setObject(1, customerId);
        int affectedRows = pstm.executeUpdate();
        
        return affectedRows > 0;
    }
    
    public static ArrayList<Customer> getAllCustomers() throws ClassNotFoundException, SQLException{
        
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Customer");
        
        ArrayList<Customer> alCustomers = new ArrayList<>();
        
        while (rst.next()){
            
            Customer customer = new Customer();
            
            customer.setId(rst.getString(1));
            customer.setName(rst.getString(2));
            customer.setAddress(rst.getString(3));
            customer.setSalary(rst.getDouble(4));
            
            alCustomers.add(customer);
        }
        
        return alCustomers;
        
    }
    
}
