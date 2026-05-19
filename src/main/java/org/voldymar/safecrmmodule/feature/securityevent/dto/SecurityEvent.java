package org.voldymar.safecrmmodule.feature.securityevent.dto;

import lombok.Builder;
import org.voldymar.safecrmmodule.feature.shared.dto.AccessObject;
import org.voldymar.safecrmmodule.feature.shared.dto.AccessSubject;

import java.util.Map;


/**
 * Описывает DTO события безопасности.
 * @param type тип события (уровень важности).
 * @param timestamp время формирования события.
 * @param message сообщение события.
 * @param decision решение о предоставлении доступа.
 * @param reason причина предоставления/отказа доступа.
 * @param subject субъект, запрашивающий доступ.
 * @param object объект доступа.
 * @param operation тип операции.
 * @param context контекст доступа.
 */
@Builder
public record SecurityEvent(

        String type,
        String timestamp,
        String message,
        String decision,
        String reason,
        AccessSubject subject,
        AccessObject object,
        String operation,
        Map<String,String> context
) {
}
