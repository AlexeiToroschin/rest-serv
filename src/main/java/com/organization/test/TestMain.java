package com.organization.test;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.camel.component.jetty9.JettyHttpComponent9;
import org.apache.camel.component.log.LogComponent;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.component.sql.SqlComponent;
import org.apache.camel.impl.DefaultShutdownStrategy;
import org.apache.camel.main.Main;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.concurrent.ArrayBlockingQueue;

public class TestMain {
    protected static Logger log = LoggerFactory.getLogger(TestMain.class);

    private static Main main = new Main();

    public static void main(String[] args) throws Exception {
        log.info("***************************************************");
        log.info("");
        log.info(" TEST TASK:: DESCRIPTION ");
        log.info("");
        log.info("***************************************************");
        createBeans();
        try {
            main.run(args);
        } finally {
            HikariDataSource dataSource = main.lookup("dataSource", HikariDataSource.class);
            if (dataSource != null && !dataSource.isClosed()) {
                dataSource.close();
            }
        }

    }
    private static void createBeans() throws Exception {

        main.addMainListener(new EventsListener());

        PropertiesComponent properties = main.getOrCreateCamelContext().getComponent("properties", PropertiesComponent.class);
        properties.setLocation("file:src/main/resources/app.properties");
        properties.setIgnoreMissingLocation(false);
        main.bind("properties", properties);


        DefaultShutdownStrategy shutdownStrategy = new DefaultShutdownStrategy();
        shutdownStrategy.setTimeout(10);
        main.bind("shutdown", shutdownStrategy);

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(properties.parseUri("DB.DRIVER-CLASS-NAME"));
        hikariConfig.setJdbcUrl(properties.parseUri("DB.URL"));
        hikariConfig.setUsername(properties.parseUri("DB.USER"));
        hikariConfig.setPassword(properties.parseUri("DB.PASSWORD"));
        hikariConfig.setRegisterMbeans(true);
        hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.parseUri("DB.POOL-SIZE")));
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        main.bind("dataSource", dataSource);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        main.bind("jdbcTemplate", jdbcTemplate);

        SqlComponent sql = new SqlComponent();
        sql.setDataSource(dataSource);
        main.bind("sql", sql);

        Operation operation = new Operation();
        main.bind("operation", operation);

        main.bind("httpClientConfigurer", new TestHttpClientConfigurer(Integer.parseInt(properties.parseUri("{{retryingCount}}"))));

        JettyHttpComponent9 jetty = new JettyHttpComponent9();
        QueuedThreadPool threadPool = new QueuedThreadPool(200, 10, 0, new ArrayBlockingQueue<>(6000));
        threadPool.setDetailedDump(false);
        jetty.setThreadPool(threadPool);
        main.bind("http-jetty", jetty);

        LogComponent log = new LogComponent();
        main.bind("log",log);

        main.addRouteBuilder(new TestRouteBuilder());
    }

}
