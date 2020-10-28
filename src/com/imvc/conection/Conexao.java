package com.imvc.conection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    String database = "gole";
    String user = "root";
    String password = "Ducilio199@";
    String hostname = "localhost:3306";
    String url = "jdbc:mysql://" + hostname + "/" +database;
    private Connection conexao = null;
    
    public Boolean AbreConexao() throws SQLException {
        try { 
         Class.forName("com.mysql.cj.jdbc.Driver");
         conexao = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e ){
            System.err.println("Classe não encontrada");
        }
        return true;
    }
    
    public Boolean FechaConexao(){
        try { 
            conexao.close();
            return true;
        }
        catch(SQLException e){
            System.err.println("Erro " + e.getMessage());
        }
        return false;
    }

    public Connection getConexao() {
        return conexao;
    }

    public void setConexao(Connection conexao) {
        this.conexao = conexao;
    }
    

}
