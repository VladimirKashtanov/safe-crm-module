package org.voldymar.safecrmmodule.feature.securityevent.dto;

/* Описывает тип события безопасности */
public enum SecurityEventType {

    /* Событие носит информационный характер */
    INFO,

    /* Событие носит предупредительный характер */
    WARN,

    /* Событие носит характер внутренней ошибки в модуле */
    ERROR
}
