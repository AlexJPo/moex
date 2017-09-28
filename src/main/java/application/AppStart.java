package application;

import application.service.MoexService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "application.repository")
public class AppStart {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource( this.getDataSource() );
        factoryBean.setPackagesToScan( new String[ ] { "application" } );

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter(){
            {
                setGenerateDdl(false);
                setShowSql(true);
            }
        };

        factoryBean.setJpaVendorAdapter( vendorAdapter );
        factoryBean.setJpaProperties( this.additionalProperties() );

        return factoryBean;
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
        properties.setProperty("hibernate.show_sql", "false");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.naming.physical-strategy", "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");

        return properties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    @Bean
    public static DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://localhost;databaseName=Moex;");
        dataSource.setUsername("sa");
        dataSource.setPassword("123456");

        return dataSource;
    }

    public static void main(String[] args) {
        //SpringApplication.run(AppStart.class, args);

        ConfigurableApplicationContext context = new SpringApplicationBuilder(AppStart.class).headless(false).run(args);
        MoexService moexService = context.getBean(MoexService.class);
        moexService.run();

        //MoexService moexService = new MoexService();
        //moexService.run();
    }

}
