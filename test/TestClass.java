/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author niroth
 */
public class TestClass {
    
    public static void main(String[] args) {
        
        String orderID = "P999";
        String[] split = orderID.split("[A-Z]");
        System.out.println(split[1]);
        
        
    }
    
    
}
