package com.shuminliu.experiment.io_test;

import com.shuminliu.experiment.io_test.hibernate.DummyEntity;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class HibernateRequest implements Runnable {
    private final Consumer<Object> consumer;
    private final SessionFactory sessionFactory;

    public HibernateRequest(Consumer<Object> consumer, SessionFactory sessionFactory) {
        this.consumer = consumer;
        this.sessionFactory = sessionFactory;
    }

    private static SessionFactory buildSessionFactory() {
        var configuration = new Configuration();
        configuration.setProperty("hibernate.connection.provider_class", "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");
        configuration.setProperty("hibernate.hikari.dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");
        configuration.setProperty("hibernate.hikari.dataSource.url", "jdbc:postgresql://localhost:5432/postgres");
        configuration.setProperty("hibernate.hikari.dataSource.user", "postgres");
        configuration.setProperty("hibernate.hikari.dataSource.password", "postgres");

        configuration.setProperty("hibernate.hbm2ddl.auto", "create");

        configuration.addAnnotatedClass(DummyEntity.class);

        return configuration.buildSessionFactory();
    }

    public static void migrate() {
        try (var factory = buildSessionFactory(); var session = factory.openSession()) {
            var transaction = session.beginTransaction();

            int deleted = session.createMutationQuery("DELETE FROM DummyEntity").executeUpdate();
            System.out.println("Deleted " + deleted + " entities");

            for (int i = 0; i < 10; i++) {
                DummyEntity dummyEntity = new DummyEntity();
                dummyEntity.setName("dummy" + i);
                session.persist(dummyEntity);
            }

            transaction.commit();
            System.out.println("Migrated");
        }
    }

    public static void runOnExecutor(ExecutorService threadPool, long count, Consumer<Object> consumer) throws InterruptedException {
        try (var factory = buildSessionFactory()) {
            for (int i = 0; i < count; i++) {
                threadPool.submit(new HibernateRequest(consumer, factory));
            }

            threadPool.shutdown();
            threadPool.awaitTermination(1, TimeUnit.DAYS);
        }
    }

    @Override
    public void run() {
        try (var session = sessionFactory.openSession()) {
            List<DummyEntity> entities = session.createSelectionQuery("FROM DummyEntity", DummyEntity.class).list();
            entities.forEach(consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
