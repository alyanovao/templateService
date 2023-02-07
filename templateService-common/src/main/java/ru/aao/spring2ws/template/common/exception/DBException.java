package ru.aao.spring2ws.template.common.exception;

/**
* @author Alyanov Artem 06.12.2022
*/

public class DBException extends ApplicationException {
    public DBException(int errCode, String description, String system) {
        super(errCode + " :: " + description + " :: " + system);
    }

    public DBException(Throwable cause) {
        super(cause);
    }
}
