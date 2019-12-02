package nsa.group4.medical.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;


@Configuration
@ConfigurationProperties("spring.datasource")
@SuppressWarnings("unused")
public class DBConfiguration {

    private String driverClassName;
    private  String url;
    private String username;
    private String password;

    @Autowired
    private Environment env;

    @Profile("dev")
    @Bean
    public String devDatabaseConfiguration(){
        System.out.println("DB Connection for DEV - using H2");
        System.out.println("Driver: " + driverClassName);
        System.out.println("Url: "+ url);
        return "DB Connection for DEV - Using H2";
    }
    @Profile("prod")
    @Bean
    public String prodDatabaseConfiguration(){
        System.out.println("DB Connection for PRODUCTION - using MariaDB");
        System.out.println("Driver: " + driverClassName);
        System.out.println("Url: "+ url);
        return "DB Connection for PRODUCTION - Using MariaDB";
    }

}
