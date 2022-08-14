package demo.config;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.mariadb.jdbc.MariaDbDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import demo.utils.JwtHandler;
import lombok.AccessLevel;
import lombok.Getter;

@Configuration
@PropertySource(value = "classpath:application.properties")
@ComponentScan(basePackages = { "demo" })
@EnableTransactionManagement
public class AppConfig {

  @Getter(AccessLevel.PUBLIC)
  @Value("${undertow.port}")
  private Integer undertowPort;

  @Getter(AccessLevel.PUBLIC)
  @Value("${undertow.address}")
  private String undertowAddress;

  @Getter(AccessLevel.PUBLIC)
  @Value("${jdbc.user}")
  private String jdbcUser;

  @Getter(AccessLevel.PUBLIC)
  @Value("${jdbc.password}")
  private String jdbcPassword;

  @Getter(AccessLevel.PUBLIC)
  @Value("${jdbc.url}")
  private String jdbcUrl;

  @Getter(AccessLevel.PUBLIC)
  @Value("${jwt.secret}")
  private String jwtSecret;

  @Bean
  public JwtHandler jwtHandler() {
    JwtHandler jwtHandler = new JwtHandler(jwtSecret);
    var token = jwtHandler.createToken(
        Map.of(
            "name", "test user",
            "email", "testing@meow.com",
            "admin", "true"
            ), 
        LocalDateTime.now().plusMinutes(20)
        );

    System.out.println("JWT Generated -> " + token);
    System.out.println("--------- TOKEN VERIFICATION ---------");
    System.out.println(jwtHandler.verifyToken(token));

    return jwtHandler;
  }

  @Bean
  public DataSource dataSource() throws SQLException {
    MariaDbDataSource dataSource = new MariaDbDataSource();
    dataSource.setUser(jdbcUser);
    dataSource.setPassword(jdbcPassword);
    dataSource.setUrl(jdbcUrl);
    return dataSource;
  }

  @Bean
  public SessionFactory sessionFactory() throws SQLException {
    return new LocalSessionFactoryBuilder(dataSource())
      .scanPackages("demo.entities")
      .addProperties(hibernateProperties())
      .buildSessionFactory();
  }

  @Bean 
  public PlatformTransactionManager transactionManager() throws IOException, SQLException {
    return new HibernateTransactionManager(sessionFactory());
  }

  private Properties hibernateProperties() {
    Properties hibernateProp = new Properties();
    hibernateProp.put("hibernate.dialect", "org.hibernate.dialect.MariaDB103Dialect");
		hibernateProp.put("hibernate.hbm2ddl.auto", "update");
    hibernateProp.put("hibernate.format_sql", true);
    hibernateProp.put("hibernate.use_sql_comments", true);
    hibernateProp.put("hibernate.show_sql", true);
    hibernateProp.put("hibernate.max_fetch_depth", 3);
    hibernateProp.put("hibernate.jdbc.batch_size", 10);
    hibernateProp.put("hibernate.jdbc.fetch_size", 50);
    return hibernateProp;
  }
}
