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
import lk.ijse.thogakade.db.DBConnection;
import lk.ijse.thogakade.model.Order;

/**
 *
 * @author niroth
 */
public class OrderController {

    public static String generateOrderId() throws ClassNotFoundException, SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT id FROM Orders ORDER BY id DESC LIMIT 1");

        if (rst.next()) {
            String orderID = rst.getString(1);
            orderID = orderID.split("[A-Z]")[1];
            orderID = (Integer.parseInt(orderID) + 1) + "";
            return "D" + orderID;
        } else {
            return "D001";
        }

    }

    public static boolean placeOrder(Order order) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement stm = connection.prepareStatement("Insert into Orders values(?,?,?)");
            stm.setObject(1, order.getId());
            stm.setObject(2, order.getDate());
            stm.setObject(3, order.getCustomerId());
            boolean isAddedOrder = stm.executeUpdate() > 0;
            if (isAddedOrder) {
                boolean isAddedItems = ItemDetailController.addItemDetail(order.getItemDetailList());
                if (isAddedItems) {
                    boolean isUpdate = ItemController.updateStockDec(order.getItemDetailList());
                    if (isUpdate) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

}
