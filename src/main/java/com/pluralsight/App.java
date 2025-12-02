package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;

public class App {
    public static void main(String[] args) {}

    private static DataSource getDataSource(String user, String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }
}
