package io.pivotal;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class CustomAppContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
          ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment();
        if(!Arrays.asList(environment.getActiveProfiles()).contains("local")) {
            try {
                /*System.out.println("spring.datasource.url :"+ EnvParser.getMySqlJdbcUrl());
                System.out.println("spring.datasource.password :"+ EnvParser.getMySqlPassword());
                System.out.println("spring.datasource.username :"+ EnvParser.getMySqlUsername());
                System.out.println("spring.gemfire.username :"+ EnvParser.getGemfireUsername());
                System.out.println("spring.gemfire.password :"+ EnvParser.getGemfirePasssword());
                System.out.println("spring.gemfire.locators :"+ EnvParser.getGemfireLocators());*/
                System.setProperty("spring.datasource.url", EnvParser.getMySqlJdbcUrl());
                System.setProperty("spring.datasource.password", EnvParser.getMySqlPassword());
                System.setProperty("spring.datasource.username", EnvParser.getMySqlUsername());
                System.setProperty("spring.gemfire.username", EnvParser.getGemfireUsername());
                System.setProperty("spring.gemfire.password", EnvParser.getGemfirePasssword());
                System.setProperty("spring.gemfire.locators", EnvParser.getGemfireLocators());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}