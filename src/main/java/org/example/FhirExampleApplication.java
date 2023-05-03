package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FhirExampleApplication {

    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(FhirExampleApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean<FhirRestfulServer> ServletRegistrationBean() {
        ServletRegistrationBean<FhirRestfulServer> registration = new ServletRegistrationBean<>(
                new FhirRestfulServer(context), "/*");
        registration.setName("FhirServlet");
        return registration;
    }
}