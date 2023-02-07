package ru.aao.spring2ws.template.db.repository;

import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
* @author Alyanov Artem 05.12.2022
*/

//Для использования раскомментировать
//@Repository
@RequiredArgsConstructor
public class TemplateRepositoryImpl implements TemplateRepository {

    private final DataSource dataSource;
    //private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        //this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
