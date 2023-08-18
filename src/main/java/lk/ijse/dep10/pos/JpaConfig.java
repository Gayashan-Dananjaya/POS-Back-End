package lk.ijse.dep10.pos;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class JpaConfig {

    private final Environment env;

    public JpaConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
        lcemfb.setDataSource(dataSource);
        lcemfb.setJpaVendorAdapter(jpaVendorAdapter);
        lcemfb.setPackagesToScan("lk.ijse.dep10.pos.entity");
        lcemfb.setJpaProperties(jpaProps());
        return lcemfb;
    }

    private Properties jpaProps() {
        Properties props = new Properties();
        props.put("hibernate.show_sql", env.getRequiredProperty("spring.jpa.properties.hibernate.show_sql", Boolean.class));
        props.put("hibernate.format_sql", env.getRequiredProperty("spring.jpa.properties.hibernate.format_sql", Boolean.class));
        props.put("hibernate.highlight_sql", env.getRequiredProperty("spring.jpa.properties.hibernate.highlight_sql", Boolean.class));
        props.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("spring.jpa.properties.hibernate.hbm2ddl.auto"));
        return props;
    }

    @Bean
    public DataSource dataSource(){
        BasicDataSource dbcp = new BasicDataSource();
        dbcp.setUrl(env.getRequiredProperty("spring.datasource.url"));
        dbcp.setUsername(env.getRequiredProperty("spring.datasource.username"));
        dbcp.setPassword(env.getRequiredProperty("spring.datasource.password"));
        dbcp.setDriverClassName(env.getRequiredProperty("spring.datasource.driver-class-name"));
        dbcp.setInitialSize(env.getRequiredProperty("spring.datasource.dbcp2.initial-size", Integer.class));
        dbcp.setMaxTotal(env.getRequiredProperty("spring.datasource.dbcp2.max-total", Integer.class));
        return dbcp;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setDatabasePlatform(env.getRequiredProperty("spring.jpa.properties.hibernate.dialect"));
//        adapter.setShowSql(env.getRequiredProperty("spring.jpa.properties.hibernate.show_sql", Boolean.class));
//        adapter.setGenerateDdl(true);
        return adapter;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        return new JpaTransactionManager(emf);
    }

}
