package org.voldymar.safecrmmodule.feature.access.dto;

import org.voldymar.safecrmmodule.feature.shared.dto.AccessObject;
import org.voldymar.safecrmmodule.feature.shared.dto.AccessSubject;

import java.util.Map;


/* Описывает запрос доступа к объекту */
public record AccessRequest(

        /* Субъект, запрашивающий доступ */
        AccessSubject subject,

        /* Объект доступа */
        AccessObject object,

        /* Операция, разрешение для которой запрашивается субъектом */
        String operation,

        /* Контекст предоставления доступа */
        Map<String, String> context
) {
}
