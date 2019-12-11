/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.thogakade.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import lk.ijse.thogakade.db.DBConnection;
import lk.ijse.thogakade.model.ItemDetail;

/**
 *
 * @author niroth
 */
public class ItemDetailController {
    public static boolean addItemDetail(ArrayList<ItemDetail> itemDetailList) throws ClassNotFoundException, SQLException{
        for (ItemDetail itemDetail : itemDetailList) {
            if(!addItemDetail(itemDetail)){
                return false;
            }
        }
        return true;
    }
    public static boolean addItemDetail(ItemDetail itemDetail) throws ClassNotFoundException, SQLException{
        Connection connection=DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("insert into ItemDetail values(?,?,?,?)");
        stm.setObject(1, itemDetail.getOrderId());
        stm.setObject(2, itemDetail.getItemCode());
        stm.setObject(3, itemDetail.getQty());
        stm.setObject(4, itemDetail.getUnitPrice());
        return stm.executeUpdate()>0;
    }
}
