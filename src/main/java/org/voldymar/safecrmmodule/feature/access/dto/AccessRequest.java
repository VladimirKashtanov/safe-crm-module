package org.voldymar.safecrmmodule.feature.access.dto;

import org.voldymar.safecrmmodule.feature.shared.dto.AccessObject;
import org.voldymar.safecrmmodule.feature.shared.dto.AccessSubject;

import java.util.Map;


/**
 * Описывает DTO запроса доступа к объекту.
 *
 * @param subject   субъект, запрашивающий доступ.
 * @param object    объект доступа.
 * @param operation тип операции.
 * @param context   контекст доступа.
 */
public record AccessRequest(

        AccessSubject subject,
        AccessObject object,
        String operation,
        Map<String, String> context
) {
}
