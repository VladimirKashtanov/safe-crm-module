package org.voldymar.safecrmmodule.feature.access.dto;

import org.voldymar.safecrmmodule.feature.securityevent.dto.SecurityEvent;


/**
 * Описывает DTO ответа на запрос доступа к объекту.
 * @param decision решение о предоставлении доступа.
 * @param securityEvent событие безопасности.
 */
public record AccessResponse(

        String decision,
        SecurityEvent securityEvent
) {
}
