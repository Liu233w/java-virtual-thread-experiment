package com.shuminliu.experiment.io_test;

import org.apache.commons.dbcp2.BasicDataSource;
import org.openjdk.jmh.infra.Blackhole;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public class DatabaseRequest implements Runnable {
    private final Consumer<Object> consumer;

    public DatabaseRequest(Consumer<Object> consumer) {
        this.consumer = consumer;
    }

    public static void runOnExecutor(ExecutorService threadPool, long count, Consumer<Object> consumer) throws InterruptedException {
        for (int i = 0; i < count; i++) {
            threadPool.submit(new DatabaseRequest(consumer));
        }

        threadPool.shutdown();
        threadPool.awaitTermination(1, java.util.concurrent.TimeUnit.DAYS);
    }

    @Override
    public void run() {
        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT 1")) {

            while (rs.next()) {
                consumer.accept(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
