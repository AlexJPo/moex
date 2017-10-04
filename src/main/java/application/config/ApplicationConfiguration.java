package application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "application")
@PropertySource("classpath:moex.properties")
@EnableJpaRepositories(basePackages = "application.repository")
public class ApplicationConfiguration {
    private static final String PROP_DATABASE_DRIVER = "db.driver";
    private static final String PROP_DATABASE_PASSWORD = "db.password";
    private static final String PROP_DATABASE_URL = "db.url";
    private static final String PROP_DATABASE_USERNAME = "db.username";
    private static final String PROP_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROP_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROP_ENTITY_MANAGER_PACKAGES_TO_SCAN = "db.entitymanager.packages.to.scan";
    private static final String PROP_HIBERNATE_NAMING_PHYSICAL_STRATEGY = "hibernate.naming.physical-strategy";

    @Resource
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(environment.getRequiredProperty(PROP_DATABASE_DRIVER));
        dataSource.setUrl(environment.getRequiredProperty(PROP_DATABASE_URL));
        dataSource.setUsername(environment.getRequiredProperty(PROP_DATABASE_USERNAME));
        dataSource.setPassword(environment.getRequiredProperty(PROP_DATABASE_PASSWORD));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter(){
            {
                setGenerateDdl(false);
                setShowSql(true);
            }
        };

        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(environment.getRequiredProperty(PROP_ENTITY_MANAGER_PACKAGES_TO_SCAN));
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setJpaProperties(getHibernateProperties());

        return factoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();

        properties.put(PROP_HIBERNATE_DIALECT, environment.getRequiredProperty(PROP_HIBERNATE_DIALECT));
        properties.put(PROP_HIBERNATE_SHOW_SQL, environment.getRequiredProperty(PROP_HIBERNATE_SHOW_SQL));
        properties.put(PROP_HIBERNATE_NAMING_PHYSICAL_STRATEGY, environment.getRequiredProperty(PROP_HIBERNATE_NAMING_PHYSICAL_STRATEGY));

        return properties;
    }
}
