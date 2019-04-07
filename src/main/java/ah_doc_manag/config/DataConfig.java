package ah_doc_manag.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
//import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:app.properties") // defines that this class uses a property in the properties file
public class DataConfig {
    @Autowired
    private Environment env; // make an env variable to access the property

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        factory.setDataSource(dataSource());
        factory.setJpaVendorAdapter(vendorAdapter);

        factory.setPackagesToScan(env.getProperty("manage.doc_manage_package"));
        factory.setJpaProperties(getHibernateProperties());

        return factory;
    }


    @Bean
    @Profile("dev") // profile used when running locally
    public DataSource dataSource() {

        BasicDataSource ds = new BasicDataSource();
        // Driver class name
        ds.setDriverClassName(env.getProperty("manage.doc.db.driver"));

        // set URL
        ds.setUrl(env.getProperty("manage.doc.db.url"));

        // set username and password
        ds.setUsername(env.getProperty("manage.doc.db.username"));
        ds.setPassword(env.getProperty("manage.doc.db.password"));


        return ds;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("hibernate.implicit_naming_strategy",env.getProperty("hibernate.implicit_naming_strategy"));
        properties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
        properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("org.hibernate.envers.store_data_at_delete", env.getProperty("org.hibernate.envers.store_data_at_delete"));
        return properties;
    }

    @Bean(name = "dataSource")
    @Profile("prod") // profile used when creating WAR file
    public DataSource jndiDataSource() {
        return new JndiDataSourceLookup()
                .getDataSource(env.getProperty("docManage.jndi"));
    }


    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

}

