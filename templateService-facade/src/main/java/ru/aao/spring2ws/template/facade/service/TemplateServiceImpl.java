package ru.aao.spring2ws.template.facade.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;

/**
* @author Alyanov Artem 05.12.2022
*/

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final BuildProperties properties;

    //private final TemplateRepository repository;

    @Override
    public String echo(String param) {
        return param;
    }

    @Override
    public String getVersion() {
        return properties.getVersion();
    }
}
