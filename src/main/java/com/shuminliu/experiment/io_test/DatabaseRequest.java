package com.shuminliu.experiment.io_test;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseRequest implements Runnable {
    @Override
    public void run() {
        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM pg_tables")) {

            while (rs.next()) {
                consumeValue(rs.getString("tablename"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void consumeValue(String value) {
        if (value.length() >= 100) {
            // this will never happen
            System.out.println(value);
        }
    }

    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl("jdbc:postgresql://localhost:5432/postgres");
        ds.setUsername("postgres");
        ds.setPassword("postgres");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(0);
        ds.setCacheState(false);
    }
}
