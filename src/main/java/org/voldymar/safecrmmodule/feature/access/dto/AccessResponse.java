package org.voldymar.safecrmmodule.feature.access.dto;

import org.voldymar.safecrmmodule.feature.securityevent.dto.SecurityEvent;


/* Описывает DTO ответа контроллера запроса доступа на операцию */
public record AccessResponse(

        /* Состояние решения о предоставлении доступа */
        String decision,

        /* Событие безопасности */
        SecurityEvent securityEvent
) {
}
