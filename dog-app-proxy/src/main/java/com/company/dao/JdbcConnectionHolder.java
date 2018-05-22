package com.company.dao;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcConnectionHolder {

    private ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<>();
    private DataSource dataSource;

    public JdbcConnectionHolder(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() {
        Connection connection = threadLocalConnection.get();
        if (connection == null) {
            try {
                connection = dataSource.getConnection();
                connection.setAutoCommit(false);
                threadLocalConnection.set(connection);
            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }
        return connection;
    }

    public void closeConnection() {
        Connection connection = threadLocalConnection.get();
        try {
            if (connection != null) {
                connection.close();
                threadLocalConnection.remove();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void commit(){
        Connection connection = getConnection();
        try{
            connection.commit();
        }catch (SQLException e){
            throw new RuntimeException("Error during commit");
        }
    }

    public void rollBack(){
        Connection connection = getConnection();
        try{
            connection.rollback();
        }catch (SQLException e){
            throw new RuntimeException("Error during rollback");
        }
    }
}
