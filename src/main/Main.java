/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import jade.Boot;

/**
 *
 * @author 3443558
 */
public class Main {
    
    public static void main(String[] args) {
        
        String[] params = new String[2];
        params[0] = "-gui";
        params[1] = "Cliente:agentes.Cliente; Cozinheiro:agentes.Cozinheiro; Garcom:agentes.Garcom; Gerente:agentes.Gerente";
        
        Boot.main(params);
        
    }
    
}
