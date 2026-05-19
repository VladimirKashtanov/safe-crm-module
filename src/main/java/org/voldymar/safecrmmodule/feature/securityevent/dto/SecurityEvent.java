package org.voldymar.safecrmmodule.feature.securityevent.dto;

import lombok.Builder;
import org.voldymar.safecrmmodule.feature.shared.dto.AccessObject;
import org.voldymar.safecrmmodule.feature.shared.dto.AccessSubject;


/* Описывает событие безопасности */
@Builder
public record SecurityEvent(

        /* Тип события */
        String type,

        /* Время формирования события */
        String timestamp,

        /* Сообщение события */
        String message,

        /* Решение о предоставлении доступа */
        String decision,

        /* Причина отказа в доступе (поле актуально при отказе) */
        String reason,

        /* Субъект, запрашивающий доступ */
        AccessSubject subject,

        /* Объект доступа */
        AccessObject object,

        /* Операция, разрешение для которой запрашивается субъектом */
        String operation
) {
}
