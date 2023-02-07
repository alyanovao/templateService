package ru.aao.spring2ws.template.facade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"ru.aao.spring2ws.template.facade", "ru.aao.spring2ws.template.logging", "ru.aao.spring2ws.template.db"})
@SpringBootApplication
public class TemplateServiceFacadeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateServiceFacadeApplication.class, args);
    }

}
