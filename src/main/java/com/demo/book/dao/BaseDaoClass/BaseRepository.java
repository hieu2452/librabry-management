package com.demo.book.dao.BaseDaoClass;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import org.hibernate.jpa.HibernatePersistenceProvider;
import java.net.URL;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.*;

import static org.hibernate.cfg.AvailableSettings.*;

public abstract class BaseRepository<T,K >{
    protected EntityManagerFactory emf = new HibernatePersistenceProvider().createContainerEntityManagerFactory(archiverPersistenceUnitInfo(), config());

	/*
		If you want to use `persistence.xml`, just rename `src/main/resources/META-INF/persistence.xml.not_used` and replace EntityManagerFactory as below

		protected EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.codspire.db.mgmt");
	 */

    private static final String PERSISTENCE_UNIT_NAME = "com.demo.book.dao";

    public abstract Optional<T> save(T obj);

    public abstract Optional<T> findById(K key);

    public abstract void delete(T obj);

    public void close() {
        emf.close();
    }

    private Map<String, Object> config() {
        Map<String, Object> map = new HashMap<>();

        map.put(JPA_JDBC_DRIVER, "com.mysql.cj.jdbc.Driver");
        map.put(JPA_JDBC_URL, "jdbc:mysql://localhost:3306/library_management_db");
        map.put(JPA_JDBC_USER, "root");
        map.put(JPA_JDBC_PASSWORD, "12345");
        map.put(DIALECT, org.hibernate.dialect.MySQLDialect.class);
//		map.put(DIALECT, "org.hibernate.dialect.H2Dialect");
//        map.put(HBM2DDL_AUTO, "create");
        map.put(SHOW_SQL, "true");
        map.put(QUERY_STARTUP_CHECKING, "false");
        map.put(GENERATE_STATISTICS, "false");
//        map.put(USE_REFLECTION_OPTIMIZER, "false");
        map.put(USE_SECOND_LEVEL_CACHE, "false");
        map.put(USE_QUERY_CACHE, "false");
        map.put(USE_STRUCTURED_CACHE, "false");
        map.put(STATEMENT_BATCH_SIZE, "20");
        map.put(AUTOCOMMIT, "false");

        map.put("hibernate.hikari.minimumIdle", "5");
        map.put("hibernate.hikari.maximumPoolSize", "15");
        map.put("hibernate.hikari.idleTimeout", "30000");

        return map;
    }

    private static PersistenceUnitInfo archiverPersistenceUnitInfo() {
        return new PersistenceUnitInfo() {
            @Override
            public String getPersistenceUnitName() {
                return PERSISTENCE_UNIT_NAME;
            }

            @Override
            public String getPersistenceProviderClassName() {
                return "com.zaxxer.hikari.hibernate.HikariConnectionProvider";
            }

            @Override
            public PersistenceUnitTransactionType getTransactionType() {
                return PersistenceUnitTransactionType.RESOURCE_LOCAL;
            }

            @Override
            public DataSource getJtaDataSource() {
                return null;
            }

            @Override
            public DataSource getNonJtaDataSource() {
                return null;
            }

            @Override
            public List<String> getMappingFileNames() {
                return Collections.emptyList();
            }

            @Override
            public List<java.net.URL> getJarFileUrls() {
                try {
                    return Collections.list(this.getClass()
                            .getClassLoader()
                            .getResources(""));
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public URL getPersistenceUnitRootUrl() {
                return null;
            }

            @Override
            public List<String> getManagedClassNames() {
                return Collections.emptyList();
            }

            @Override
            public boolean excludeUnlistedClasses() {
                return false;
            }

            @Override
            public SharedCacheMode getSharedCacheMode() {
                return null;
            }

            @Override
            public ValidationMode getValidationMode() {
                return null;
            }

            @Override
            public Properties getProperties() {
                return new Properties();
            }

            @Override
            public String getPersistenceXMLSchemaVersion() {
                return null;
            }

            @Override
            public ClassLoader getClassLoader() {
                return null;
            }

            @Override
            public void addTransformer(ClassTransformer transformer) {

            }

            @Override
            public ClassLoader getNewTempClassLoader() {
                return null;
            }
        };
    }

}
