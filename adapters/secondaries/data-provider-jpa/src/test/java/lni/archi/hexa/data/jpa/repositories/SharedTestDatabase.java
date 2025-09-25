package lni.archi.hexa.data.jpa.repositories;

import lombok.Getter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SharedTestDatabase {

    private static EntityManagerFactory emf;

    @Getter
    private static EntityManager entityManager;

    @Getter
    private static Connection connection;

    private static boolean initialized = false;

    public static synchronized void init(String persistenceUnitName, String... sqlScripts) {
        if (!initialized) {
            try {
                emf = Persistence.createEntityManagerFactory(persistenceUnitName);
                entityManager = emf.createEntityManager();
                connection = DriverManager.getConnection(
                        "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");

                try (Statement stmt = connection.createStatement()) {
                    for (String script : sqlScripts) {
                        stmt.execute("RUNSCRIPT FROM '" + script + "'");
                    }
                }

                initialized = true;

            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize shared test database", e);
            }
        }
    }

    public static <T> void injectEntityManager(T repo) {
        try {
            Field field = repo.getClass().getDeclaredField("entityManager");
            field.setAccessible(true);
            field.set(repo, entityManager);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject EntityManager", e);
        }
    }

    public static void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    public static void rollbackTransaction() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    // Optionnel : fermeture propre à la fin de la JVM
    public static void close() {
        try {
            if (entityManager != null) entityManager.close();
            if (emf != null) emf.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
