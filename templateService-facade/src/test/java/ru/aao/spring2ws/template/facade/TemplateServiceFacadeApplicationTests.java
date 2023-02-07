package ru.aao.spring2ws.template.facade;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"ru.aao.spring2ws.template.facade", "ru.aao.spring2ws.template.db", "ru.aao.spring2ws.template.logging"})
@SpringBootTest
class TemplateServiceFacadeApplicationTests {

    @Test
    void contextLoads() {
    }

}
