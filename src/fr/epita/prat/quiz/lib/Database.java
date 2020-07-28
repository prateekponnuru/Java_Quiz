package fr.epita.prat.quiz.lib;

import org.h2.jdbc.JdbcLob;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class Database {
    private static Database instance;
    private String host;
    private String uId;
    private String pwd;
    private Connection connection;

    public String getHost() {
        return host;
    }

    public void setHost(String connectString) { this.host = connectString; }

    public String getuId() { return uId; }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public static Database getInstance() {
        if (instance == null){
            instance = new Database();
        }
        return instance;
    }

    public void setParams(String connectString, String uId, String pwd){
        this.host = connectString;
        this.uId = uId;
        this.pwd = pwd;
        }


    public Connection getConnection(){
        try {
            if (this.connection == null){
                this.connection = DriverManager.getConnection(this.host, this.uId, this.pwd);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("Exception occurred while connecting to the database.");
        }
        return this.connection;

    }
    public void createTable(String table, Map<String, String> columns){
        try {
            if (this.connection == null){
                this.connection = DriverManager.getConnection(this.host, this.uId, this.pwd);
            }
            Statement sql = this.connection.createStatement();
            String _sql = String.format("create table if not exists %s (%s);",
                                        table,
                                        columns.entrySet().stream()
                                                .map(e -> e.getKey()+" "+e.getValue())
                                                .reduce((e1, e2) -> e1+", "+e2).get());
//            sql.setString(1, _sql);
//            sql.setString(1, table);
//            sql.setString(2, String.valueOf(columns.entrySet().stream()
//                                                            .map(e -> e.getKey()+" "+e.getValue())
//                                                            .reduce((e1, e2) -> e1+", "+e2)));
            sql.executeUpdate(_sql);
//            sql.close();
//            this.connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Exception occurred while creating a table.");
            try{this.connection.close();}
            catch (SQLException e2){System.out.println("Couldn't close the connection.");}
        }
    }

    public void dropTables(List<String> tables){
        try {
            if (this.connection == null){
                this.connection = DriverManager.getConnection(this.host, this.uId, this.pwd);
            }
            for (String table: tables){
                Statement sql = this.connection.createStatement();
                sql.executeUpdate(String.format("drop table %s;", table));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Exception occurred while dropping a table.");
            try{this.connection.close();}
            catch (SQLException e2){System.out.println("Couldn't close the connection.");}
        }
    }

    public void selectAll(String table){
        try {
            Statement sql = this.connection.createStatement();
            ResultSet result = sql.executeQuery(String.format("select * from %s;", table));
            Integer num_cols = 0;
            if (result.next()) {
                try {
                    while (true) {
                        num_cols += 1;
                        System.out.print(result.getString(num_cols)+"\t");
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.print("\n");
                    num_cols -= 1;
                }
            }

            while (result.next()) {
                for (int col = 1; col < num_cols; col++){
                    System.out.print(result.getString(col)+"\t");
                }
                System.out.print(result.getString(num_cols)+"\n");
            }
        }
        catch (SQLException e){
            System.out.println("Exception occurred while fetching all records from table.");
            e.printStackTrace();
        }

    }



}
