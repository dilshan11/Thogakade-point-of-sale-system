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
import lk.ijse.thogakade.model.Item;
import lk.ijse.thogakade.model.ItemDetail;

/**
 *
 * @author niroth
 */
public class ItemController {
    
    public static boolean addItem(Item item) throws ClassNotFoundException, SQLException{
        
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO Item VALUES (?,?,?,?)");
        
        pstm.setObject(1, item.getCode());
        pstm.setObject(2, item.getDescription());
        pstm.setObject(3, item.getUnitPrice());
        pstm.setObject(4, item.getQtyOnHand());
        
        int affectedRows = pstm.executeUpdate();
        
        return affectedRows > 0;
        
    }
    
    public static ArrayList<Item> loadAllItems() throws ClassNotFoundException, SQLException{
        
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Item");
        
        ArrayList<Item> alItems = new ArrayList<>();
        
        while(rst.next()){
            
            Item item = new Item();
            item.setCode(rst.getString(1));
            item.setDescription(rst.getString(2));
            item.setUnitPrice(rst.getDouble(3));
            item.setQtyOnHand(rst.getInt(4));
            
            alItems.add(item);
        }
        
        return alItems;
        
    }
    
    public static Item searchItem(String itemCode) throws ClassNotFoundException, SQLException{
        
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Item WHERE code='" + itemCode + "'");
        
        if (rst.next()){
            Item item = new Item();
            item.setCode(rst.getString(1));
            item.setDescription(rst.getString(2));
            item.setUnitPrice(rst.getDouble(3));
            item.setQtyOnHand(rst.getInt(4));
            
            return item;
        }else{
            return null;
        }
        
    }

    public static boolean updateStockDec(ArrayList<ItemDetail> itemDetailList) throws ClassNotFoundException, SQLException {
        for (ItemDetail itemDetail : itemDetailList) {
            if(!updateStockDec(itemDetail)){
                return false;
            }
        }
        return true;
    }
    public static boolean updateStockDec(ItemDetail itemDetail) throws ClassNotFoundException, SQLException{
        Connection connection=DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        String SQL="Update Item set qtyOnHand=qtyOnhand-"+itemDetail.getQty()+" where code='"+itemDetail.getItemCode()+"'";
        return stm.executeUpdate(SQL)>0;
    }
}
