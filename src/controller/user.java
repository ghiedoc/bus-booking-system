/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Gillian
 */
public class user {
    public String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public user() {
        
    }
    
    private static final user INSTANCE = new user();
    
    public static user getINSTANCE() {
        return INSTANCE;
    }
    
    
}
